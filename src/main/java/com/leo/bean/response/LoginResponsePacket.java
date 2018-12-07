package com.leo.bean.response;

import com.leo.bean.Command;
import com.leo.bean.Packet;
import lombok.Data;

/**
 * @Description: 登录响应
 * @Author: Leo
 * @Date: 2018-11-30 下午 2:30
 */
@Data
public class LoginResponsePacket extends Packet {

    private String userId;
    private String userName;
    private boolean success;
    private String reason;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
