package com.leo.im.handler.response;

import com.leo.bean.response.MessageResponsePacket;
import com.leo.session.Session;
import com.leo.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Description: 消息回复处理器
 * @Author: Leo
 * @Date: 2018-12-03 上午 11:13
 */
@ChannelHandler.Sharable
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {

    private volatile static MessageResponseHandler handler;

    private MessageResponseHandler() {
    }

    public synchronized static MessageResponseHandler INSTANCE() {
        synchronized (MessageResponseHandler.class) {
            if (handler == null) {
                handler = new MessageResponseHandler();
            }
        }
        return handler;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket msg) throws Exception {
        // 打印接收到消息
        System.out.println(msg.getFromUserId() + ":" + msg.getFromUserName() + "->" + msg.getMessage());
    }
}
