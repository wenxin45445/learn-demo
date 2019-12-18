package com.meng.nettyio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NioClient {
    private Selector selector;

    public static void main(String[] args) throws IOException {
        NioClient nioClient = new NioClient();
        nioClient.startClient();
        nioClient.waitForChannel();
    }

    private void waitForChannel() throws IOException {
        for (;;){
            this.selector.select();
            Iterator<SelectionKey> iterator = this.selector.selectedKeys().iterator();
            while (iterator.hasNext()){
                SelectionKey next = iterator.next();
                iterator.remove();
                if (next.isConnectable()){
                    SocketChannel sc = (SocketChannel) next.channel();
                    if (sc.isConnectionPending()){
                        sc.finishConnect();
                    }
                    sc.configureBlocking(false);
                    ByteBuffer bb = ByteBuffer.wrap("hello server".getBytes());
                    sc.write(bb);
                    sc.register(this.selector, SelectionKey.OP_READ);
                } else if (next.isReadable()){
                    read(next);
                }
            }
        }
    }

    private void read(SelectionKey next) throws IOException {
        SocketChannel sc = (SocketChannel) next.channel();
        ByteBuffer bb = ByteBuffer.allocate(1024);
        int len = sc.read(bb);
        if (len != -1) {
            System.out.println("客户端收到服务端消息："  + new String(bb.array(), 0, len));
        }
    }

    private void startClient() throws IOException {
        SocketChannel ss = SocketChannel.open();
        ss.configureBlocking(false);
        this.selector = Selector.open();

        ss.connect(new InetSocketAddress("127.0.0.1", 9999));
        ss.register(this.selector, SelectionKey.OP_CONNECT);

    }

}
