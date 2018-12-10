package com.leo.bean.request;

import com.leo.bean.Command;
import com.leo.bean.Packet;
import lombok.Data;

/**
 * @Description: 登出请求
 * @Author: Leo
 * @Date: 2018-12-10 下午 4:25
 */
@Data
public class LogOutRequestPacket extends Packet {

    @Override
    public Byte getCommand() {
        return Command.LOGOUT_REQUEST;
    }
}
