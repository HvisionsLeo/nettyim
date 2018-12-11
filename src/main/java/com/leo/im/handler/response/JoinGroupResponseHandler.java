package com.leo.im.handler.response;

import com.leo.bean.response.JoinGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Description:
 * @Author: Leo
 * @Date: 2018-12-11 上午 10:40
 */
public class JoinGroupResponseHandler extends SimpleChannelInboundHandler<JoinGroupResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupResponsePacket msg) throws Exception {
        if (msg.isSuccess()) {
            System.out.println("加入群【" + msg.getGroupId() + "】成功！");
        } else {
            System.out.println("加入群【" + msg.getGroupId() + "】失败，原因：" + msg.getReason());
        }
    }
}
