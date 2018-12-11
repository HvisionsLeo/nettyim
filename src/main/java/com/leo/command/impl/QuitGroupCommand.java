package com.leo.command.impl;

import com.leo.bean.request.QuitGroupRequestPacket;
import com.leo.command.ConsoleCommand;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @Description: 退出群聊
 * @Author: Leo
 * @Date: 2018-12-11 上午 10:05
 */
public class QuitGroupCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("【退出群聊】");
        System.out.println("请输入退出群ID：");
        QuitGroupRequestPacket packet = new QuitGroupRequestPacket();
        packet.setGroupId(scanner.next());
        channel.writeAndFlush(packet);
    }
}
