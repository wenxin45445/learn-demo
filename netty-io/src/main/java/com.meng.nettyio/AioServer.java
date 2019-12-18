package com.meng.nettyio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class AioServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        new AioServer().startAioServer();
    }

    private void startAioServer() throws IOException, InterruptedException {
        final AsynchronousServerSocketChannel assc = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(9999));

        assc.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
            @Override
            public void completed(AsynchronousSocketChannel socketChannel, Object attachment) {
                try {
                    assc.accept(attachment, this);
                    System.out.println(socketChannel.getRemoteAddress());
                    ByteBuffer bb = ByteBuffer.allocate(1024);
                    socketChannel.read(bb, bb, new CompletionHandler<Integer, ByteBuffer>() {
                        @Override
                        public void completed(Integer result, ByteBuffer attachment) {
                            attachment.flip();
                            System.out.println(new String(bb.array(), 0, result));
                            socketChannel.write(ByteBuffer.wrap("hello client".getBytes()));
                        }

                        @Override
                        public void failed(Throwable exc, ByteBuffer attachment) {
                            exc.printStackTrace();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                exc.printStackTrace();
            }
        });
        Thread.sleep(Integer.MAX_VALUE);
    }
}
