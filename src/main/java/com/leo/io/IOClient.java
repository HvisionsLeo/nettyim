package com.leo.io;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

/**
 * @Description: 传统IO客户端
 * @Author: Leo
 * @Date: 2018-11-28 上午 10:44
 */
public class IOClient {

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                Socket socket = new Socket("127.0.0.1", 8888);
                while (true) {
                    socket.getOutputStream().write((DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + ":Hello World!").getBytes());
                    Thread.sleep(2000);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
