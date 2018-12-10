package com.leo.bean.response;

import com.leo.bean.Command;
import com.leo.bean.Packet;
import lombok.Data;

import java.util.List;

/**
 * @Description: 创建群聊响应包
 * @Author: Leo
 * @Date: 2018-12-10 下午 4:05
 */
@Data
public class CreateGroupResponsePacket extends Packet {

    private boolean success;

    private String groupId;

    private List<String> userNameList;

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_RESPONSE;
    }
}
