package com.leo.practice.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Description: 传统IO服务端
 * @Author: Leo
 * @Date: 2018-11-28 上午 10:42
 */
public class IOServer {

    public static void main(String[] args) throws IOException {
        // IO 编程模型在客户端较少的情况下运行良好，但是对于客户端比较多的业务来说，
        // 单机服务端可能需要支撑成千上万的连接，IO 模型可能就不太合适了
        // 存在问题
        // 1.线程资源受限：线程是操作系统中非常宝贵的资源，同一时刻有大量的线程处于阻塞状态
        // 是非常严重的资源浪费，操作系统耗不起
        // 2.线程切换效率低下：单机 CPU 核数固定，线程爆炸之后操作系统频繁
        // 进行线程切换，应用性能急剧下降。
        // 3.除了以上两个问题，IO 编程中，我们看到数据读写是以字节流为单位。
        ServerSocket serverSocket = new ServerSocket(8888);
        System.out.println("服务端启动，监听端口：" + 8888);
        new Thread(() -> {
            while (true) {
                try {
                    // 以阻塞的方法获取新的连接
                    Socket socket = serverSocket.accept();

                    // 每一个新的连接创建一个新的线程，负责读取数据
                    new Thread(() -> {
                        try {
                            int len;
                            byte[] data = new byte[1024];
                            InputStream in = socket.getInputStream();
                            // 按照字节流的方式读取数据
                            while ((len = in.read(data)) != -1) {
                                System.out.println("接受到消息：" + new String(data, 0, len));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
