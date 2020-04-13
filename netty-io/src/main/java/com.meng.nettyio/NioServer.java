package com.meng.nettyio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class NioServer {
    public static void main(String[] args) {
        try {
            new NioServer().startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startServer() throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.socket().bind(new InetSocketAddress(9999));

        Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        for (; ; ) {
            System.out.println("等待事件发生。。。。。");
            selector.select();
            System.out.println("有事件发生。。。。");
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey next = iterator.next();
                iterator.remove();
                handle(next);
            }
        }
    }

    private void handle(SelectionKey next) throws IOException {
        if (next.isAcceptable()) {
            System.out.println("有客户端连接。。。。。");
            ServerSocketChannel ssc = (ServerSocketChannel) next.channel();
            SocketChannel sc = ssc.accept();
            sc.configureBlocking(false);
            sc.register(next.selector(), SelectionKey.OP_READ);
        } else if (next.isReadable()) {
            System.out.println("有客户端可读事件发生。。。");
            SocketChannel sc = (SocketChannel) next.channel();
            ByteBuffer bb = ByteBuffer.allocate(1024);
            bb.clear();
            int len = sc.read(bb);
            if (len != -1) {
                System.out.println("读取到客户端发送信息：" + new String(bb.array(), 0, len));
            }
            ByteBuffer bbWrite = ByteBuffer.wrap("hello client".getBytes());
            sc.write(bbWrite);
            next.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
            sc.close();
        }
    }
}
