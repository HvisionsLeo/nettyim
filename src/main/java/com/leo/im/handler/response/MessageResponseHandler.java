package com.leo.im.handler.response;

import com.leo.bean.response.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Description: 消息回复处理器
 * @Author: Leo
 * @Date: 2018-12-03 上午 11:13
 */
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket msg) throws Exception {
        System.out.println("接收到服务端的回复->" + msg.getMessage());
        if ("exit".equals(msg.getMessage())) {
            ctx.channel().close();
        }
    }
}
