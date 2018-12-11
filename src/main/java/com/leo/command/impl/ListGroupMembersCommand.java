package com.leo.command.impl;

import com.leo.bean.request.ListGroupMembersRequestPacket;
import com.leo.command.ConsoleCommand;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @Description: 群成员列表命令
 * @Author: Leo
 * @Date: 2018-12-11 上午 10:54
 */
public class ListGroupMembersCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("【查询群成员】");
        System.out.println("请输入查询群ID：");
        ListGroupMembersRequestPacket packet = new ListGroupMembersRequestPacket();
        packet.setGroupId(scanner.next());
        channel.writeAndFlush(packet);
    }
}
