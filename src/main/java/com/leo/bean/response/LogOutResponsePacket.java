package com.leo.bean.response;

import com.leo.bean.Command;
import com.leo.bean.Packet;
import lombok.Data;

/**
 * @Description: 登出响应
 * @Author: Leo
 * @Date: 2018-12-10 下午 4:26
 */
@Data
public class LogOutResponsePacket extends Packet {

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return Command.LOGOUT_RESPONSE;
    }
}
