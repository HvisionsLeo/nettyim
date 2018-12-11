package com.leo.command.impl;

import com.leo.bean.request.JoinGroupRequestPacket;
import com.leo.command.ConsoleCommand;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @Description: 加入群聊
 * @Author: Leo
 * @Date: 2018-12-11 上午 10:09
 */
public class JoinGroupCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("【加入群聊】");
        System.out.println("请输入加入群聊ID：");
        JoinGroupRequestPacket packet = new JoinGroupRequestPacket();
        packet.setGroupId(scanner.next());
        channel.writeAndFlush(packet);
    }

}
