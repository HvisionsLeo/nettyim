package com.leo.command.impl;

import com.leo.bean.request.LoginRequestPacket;
import com.leo.command.ConsoleCommand;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @Description: 登录
 * @Author: Leo
 * @Date: 2018-12-10 下午 4:14
 */
public class LoginConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        LoginRequestPacket packet = new LoginRequestPacket();
        System.out.println("【登录】");
        System.out.println("输入用户名：");
        String userName = scanner.next();
        System.out.println("输入密码：");
        String passWord = scanner.next();
        packet.setUsername(userName);
        packet.setPassword(passWord);
        channel.writeAndFlush(packet);
        waitForLoginResponse();
    }

    private static void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
