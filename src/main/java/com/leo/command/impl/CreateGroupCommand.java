package com.leo.command.impl;

import com.leo.bean.request.CreateGroupRequestPacket;
import com.leo.command.ConsoleCommand;
import io.netty.channel.Channel;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @Description: 创建群组命令
 * @Author: Leo
 * @Date: 2018-12-10 下午 3:57
 */
public class CreateGroupCommand implements ConsoleCommand {

    private static final String USER_ID_SPLITER = ",";

    @Override
    public void exec(Scanner scanner, Channel channel) {
        CreateGroupRequestPacket packet = new CreateGroupRequestPacket();
        System.out.println("【拉人群聊】");
        System.out.println("输入userId列表，userId之间英文逗号隔开：");
        String userIds = scanner.next();
        packet.setUserIdList(Arrays.asList(userIds.split(USER_ID_SPLITER)));
        channel.writeAndFlush(packet);
    }
}
