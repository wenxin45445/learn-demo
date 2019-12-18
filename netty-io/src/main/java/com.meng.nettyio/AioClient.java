package com.meng.nettyio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;

public class AioClient {
    public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
        new AioClient().startAioClient();
    }

    private void startAioClient() throws IOException, ExecutionException, InterruptedException {
        AsynchronousSocketChannel socketChannel = AsynchronousSocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 9999)).get();
        socketChannel.write(ByteBuffer.wrap("hello aio server".getBytes()));
        ByteBuffer bb = ByteBuffer.allocate(1024);
        Integer len = socketChannel.read(bb).get();
        if (len != -1) {
            System.out.println("收到服务端消息：" + new String(bb.array(), 0, len));
        }

    }
}
