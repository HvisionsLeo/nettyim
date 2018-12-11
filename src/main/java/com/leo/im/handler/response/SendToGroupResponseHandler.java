package com.leo.im.handler.response;

import com.leo.bean.response.SendToGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Description:
 * @Author: Leo
 * @Date: 2018-12-11 下午 3:06
 */
public class SendToGroupResponseHandler extends SimpleChannelInboundHandler<SendToGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SendToGroupResponsePacket msg) throws Exception {
        System.out.println("来自群【" + msg.getGroupId() + "】的【" + msg.getUserName() + "】发送消息->" + msg.getMessage());
    }
}
