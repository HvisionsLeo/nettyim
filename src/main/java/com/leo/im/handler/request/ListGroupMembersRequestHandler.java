package com.leo.im.handler.request;

import com.leo.bean.request.ListGroupMembersRequestPacket;
import com.leo.bean.response.ListGroupMembersResponsePacket;
import com.leo.session.Session;
import com.leo.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: Leo
 * @Date: 2018-12-11 上午 11:06
 */
public class ListGroupMembersRequestHandler extends SimpleChannelInboundHandler<ListGroupMembersRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersRequestPacket msg) throws Exception {
        String groupId = msg.getGroupId();
        ChannelGroup group = SessionUtil.getChannelGroup(groupId);
        ListGroupMembersResponsePacket packet = new ListGroupMembersResponsePacket();
        packet.setGroupId(groupId);
        List<Session> sessionList = new ArrayList<>();
        for (Channel channel : group) {
            sessionList.add(SessionUtil.getSession(channel));
        }
        packet.setSessionList(sessionList);
        ctx.channel().writeAndFlush(packet);
    }
}
