package com.leo.bean.response;

import com.leo.bean.Command;
import com.leo.bean.Packet;
import lombok.Data;

/**
 * @Description: 群组消息接受数据包
 * @Author: Leo
 * @Date: 2018-12-11 下午 2:39
 */
@Data
public class SendToGroupResponsePacket extends Packet {

    private String groupId;

    private String userName;

    private String message;

    @Override
    public Byte getCommand() {
        return Command.SEND_TO_GROUP_RESPONSE;
    }
}
