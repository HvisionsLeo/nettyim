package com.leo.bean.response;

import com.leo.bean.Command;
import com.leo.bean.Packet;
import lombok.Data;

/**
 * @Description: 消息回复数据包
 * @Author: Leo
 * @Date: 2018-11-30 下午 3:31
 */
@Data
public class MessageResponsePacket extends Packet {

    private String fromUserId;
    private String fromUserName;
    private String message;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }
}
