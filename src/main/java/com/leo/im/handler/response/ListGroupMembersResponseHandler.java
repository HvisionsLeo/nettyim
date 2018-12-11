package com.leo.im.handler.response;

import com.leo.bean.response.ListGroupMembersResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Description:
 * @Author: Leo
 * @Date: 2018-12-11 上午 11:10
 */
public class ListGroupMembersResponseHandler extends SimpleChannelInboundHandler<ListGroupMembersResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersResponsePacket msg) throws Exception {
        System.out.println("群【" + msg.getGroupId() + "】包含群成员" + msg.getSessionList());
    }
}
