package com.leo.bean.response;

import com.leo.bean.Command;
import com.leo.bean.Packet;
import com.leo.session.Session;
import lombok.Data;

import java.util.List;

/**
 * @Description: 组成员获取响应数据包
 * @Author: Leo
 * @Date: 2018-12-11 上午 10:59
 */
@Data
public class ListGroupMembersResponsePacket extends Packet {

    private String groupId;

    private List<Session> sessionList;

    @Override
    public Byte getCommand() {
        return Command.LIST_GROUP_MEMBERS_RESPONSE;
    }
}
