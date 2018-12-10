package com.leo.im.handler.response;

import com.leo.bean.response.CreateGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Description: 创建群组响应
 * @Author: Leo
 * @Date: 2018-12-10 下午 4:59
 */
public class CreateGroupResponseHandler extends SimpleChannelInboundHandler<CreateGroupResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupResponsePacket msg) throws Exception {
        System.out.println("群创建成功，id为[" + msg.getGroupId() + "]");
        System.out.println("群里面有：" + msg.getUserNameList());
    }
}
