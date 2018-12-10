package com.leo.command.impl;

import com.leo.bean.request.LogOutRequestPacket;
import com.leo.command.ConsoleCommand;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @Description: 登出
 * @Author: Leo
 * @Date: 2018-12-10 下午 4:24
 */
public class LogOutConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        LogOutRequestPacket packet = new LogOutRequestPacket();
        channel.writeAndFlush(packet);
    }
}
