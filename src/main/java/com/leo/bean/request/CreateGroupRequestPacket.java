package com.leo.bean.request;

import com.leo.bean.Command;
import com.leo.bean.Packet;
import lombok.Data;

import java.util.List;

/**
 * @Description: 创建群聊请求包
 * @Author: Leo
 * @Date: 2018-12-10 下午 4:00
 */
@Data
public class CreateGroupRequestPacket extends Packet {

    private List<String> userIdList;

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_REQUEST;
    }
}
