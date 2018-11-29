package com.leo.bean;

import lombok.Data;

/**
 * @Description:
 * @Author: Leo
 * @Date: 2018-11-29 下午 5:32
 */
@Data
public class LoginRequestPacket extends Packet {

    private Integer userId;
    private String username;
    private String password;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
