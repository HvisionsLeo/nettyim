package com.leo.command.impl;

import com.leo.bean.request.SendToGroupRequestPacket;
import com.leo.command.ConsoleCommand;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @Description: 发送消息命令
 * @Author: Leo
 * @Date: 2018-12-11 下午 2:35
 */
public class SendToGroupCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        SendToGroupRequestPacket packet = new SendToGroupRequestPacket();
        System.out.println("【发送给组】");
        System.out.println("请输入组ID：");
        packet.setGroupId(scanner.next());
        System.out.println("请输入要发送的消息：");
        packet.setMessage(scanner.next());
        channel.writeAndFlush(packet);

    }

}
