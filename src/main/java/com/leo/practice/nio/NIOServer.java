package com.leo.practice.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * @Description: NIO服务端
 * @Author: Leo
 * @Date: 2018-11-28 上午 11:13
 */
public class NIOServer {

    public static void main(String[] args) throws IOException {
        Selector serverSelector = Selector.open();
        Selector clientSelector = Selector.open();

        new Thread(() -> {
            try {
                // 对应IO编程中服务端启动
                ServerSocketChannel channel = ServerSocketChannel.open();
                channel.socket().bind(new InetSocketAddress(8888));
                channel.configureBlocking(false);
                channel.register(serverSelector, SelectionKey.OP_ACCEPT);
                while (true) {
                    // 监测是否有新的连接，这里的1为阻塞时间为1ms
                    if (serverSelector.select(1) > 0) {
                        Set<SelectionKey> set = serverSelector.selectedKeys();
                        Iterator<SelectionKey> it = set.iterator();
                        while (it.hasNext()) {
                            SelectionKey key = it.next();
                            if (key.isAcceptable()) {
                                try {
                                    // 每来一个新连接，不需要创建一个线程，而是直接注册到clientSelector上
                                    SocketChannel clientChannel = ((ServerSocketChannel) key.channel()).accept();
                                    clientChannel.configureBlocking(false);
                                    clientChannel.register(clientSelector, SelectionKey.OP_READ);
                                } finally {
                                    it.remove();
                                }
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();

        new Thread(() -> {
            try {
                while (true) {
                    // 批量轮询是否有那些连接数据可读，这里的1指的是阻塞时间1ms
                    if (clientSelector.select(1) > 0) {
                        Set<SelectionKey> set = clientSelector.selectedKeys();
                        Iterator<SelectionKey> it = set.iterator();
                        while (it.hasNext()) {
                            SelectionKey key = it.next();
                            if (key.isReadable()) {
                                try {

                                    SocketChannel clientChannel = (SocketChannel) key.channel();
                                    // 读取 面向buffer
                                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                                    clientChannel.read(byteBuffer);
                                    byteBuffer.flip();
                                    System.out.println(Charset.defaultCharset().newDecoder().decode(byteBuffer).toString());
                                } finally {
                                    it.remove();
                                    key.interestOps(SelectionKey.OP_READ);
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {

            }
        }).start();
    }
}
