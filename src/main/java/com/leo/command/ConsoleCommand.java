package com.leo.command;

import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @Description: 控制台命令
 * @Author: Leo
 * @Date: 2018-12-10 下午 3:42
 */
public interface ConsoleCommand {
    void exec(Scanner scanner, Channel channel);
}
