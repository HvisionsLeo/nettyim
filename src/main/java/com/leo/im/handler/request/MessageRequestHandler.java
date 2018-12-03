package com.leo.im.handler.request;

import com.leo.bean.request.MessageRequestPacket;
import com.leo.bean.response.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Description: 消息请求处理器
 * @Author: Leo
 * @Date: 2018-12-03 上午 11:11
 */
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket msg) throws Exception {
        System.out.println("服务端接收到消息->" + msg.getMessage());
        MessageResponsePacket packet = new MessageResponsePacket();
        packet.setMessage("回复：" + msg.getMessage());
        ctx.channel().writeAndFlush(packet);
    }
}
