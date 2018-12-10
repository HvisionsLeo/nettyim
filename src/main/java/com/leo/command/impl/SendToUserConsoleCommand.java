package com.leo.command.impl;

import com.leo.bean.request.MessageRequestPacket;
import com.leo.command.ConsoleCommand;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @Description:
 * @Author: Leo
 * @Date: 2018-12-10 下午 4:19
 */
public class SendToUserConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        MessageRequestPacket packet = new MessageRequestPacket();
        System.out.println("【发送消息】");
        System.out.println("输入要发送的用户id：");
        String userId = scanner.next();
        packet.setToUserId(userId);
        System.out.println("输入要发送的消息：");
        String message = scanner.next();
        packet.setMessage(message);
        channel.writeAndFlush(packet);
    }
}
