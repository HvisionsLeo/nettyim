package com.leo.im.handler.response;

import com.leo.bean.response.ListGroupMembersResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Description: 获取群成员列表响应处理器
 * @Author: Leo
 * @Date: 2018-12-11 上午 11:10
 */
@ChannelHandler.Sharable
public class ListGroupMembersResponseHandler extends SimpleChannelInboundHandler<ListGroupMembersResponsePacket> {

    private volatile static ListGroupMembersResponseHandler handler;

    private ListGroupMembersResponseHandler() {
    }

    public synchronized static ListGroupMembersResponseHandler INSTANCE() {
        synchronized (ListGroupMembersResponseHandler.class) {
            if (handler == null) {
                handler = new ListGroupMembersResponseHandler();
            }
        }
        return handler;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersResponsePacket msg) throws Exception {
        System.out.println("群【" + msg.getGroupId() + "】包含群成员" + msg.getSessionList());
    }
}
