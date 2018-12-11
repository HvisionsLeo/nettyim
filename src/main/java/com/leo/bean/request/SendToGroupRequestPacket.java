package com.leo.bean.request;

import com.leo.bean.Command;
import com.leo.bean.Packet;
import lombok.Data;

/**
 * @Description: 发送消息给群聊数据包
 * @Author: Leo
 * @Date: 2018-12-11 下午 2:37
 */
@Data
public class SendToGroupRequestPacket extends Packet {

    private String groupId;

    private String message;

    @Override
    public Byte getCommand() {
        return Command.SEND_TO_GROUP_REQUEST;
    }
}
