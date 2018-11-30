package com.leo.bean.request;

import com.leo.bean.Command;
import com.leo.bean.Packet;
import lombok.Data;

/**
 * @Description: 登陆数据包
 * @Author: Leo
 * @Date: 2018-11-29 下午 5:32
 */
@Data
public class LoginRequestPacket extends Packet {

    private String userId;
    private String username;
    private String password;


    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
