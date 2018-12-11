package com.leo.command.impl;

import com.leo.command.ConsoleCommand;
import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @Description:
 * @Author: Leo
 * @Date: 2018-12-10 下午 3:44
 */
public class ConsoleCommandManager implements ConsoleCommand {

    private Map<String, ConsoleCommand> consoleCommandMap;

    public ConsoleCommandManager() {
        consoleCommandMap = new HashMap<>();
        consoleCommandMap.put("createGroup", new CreateGroupCommand());
        consoleCommandMap.put("joinGroup", new JoinGroupCommand());
        consoleCommandMap.put("quitGroup", new QuitGroupCommand());
        consoleCommandMap.put("sendToUser", new SendToUserConsoleCommand());
        consoleCommandMap.put("groupMer", new ListGroupMembersCommand());
        consoleCommandMap.put("logOut", new LogOutConsoleCommand());
    }

    @Override
    public void exec(Scanner scanner, Channel channel) {
        // 获取第一个指令
        String command = scanner.next();

        ConsoleCommand consoleCommand = consoleCommandMap.get(command);

        if (consoleCommand != null) {
            consoleCommand.exec(scanner, channel);
        } else {
            System.err.println("无法识别[" + command + "]指令，请重新输入！");
        }
    }
}
