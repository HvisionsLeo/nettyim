package com.leo.im.handler.response;

import com.leo.bean.response.SendToGroupResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Description: 发送群消息响应处理器
 * @Author: Leo
 * @Date: 2018-12-11 下午 3:06
 */
@ChannelHandler.Sharable
public class SendToGroupResponseHandler extends SimpleChannelInboundHandler<SendToGroupResponsePacket> {

    private volatile static SendToGroupResponseHandler handler;

    private SendToGroupResponseHandler() {
    }

    public synchronized static SendToGroupResponseHandler INSTANCE() {
        synchronized (SendToGroupResponseHandler.class) {
            if (handler == null) {
                handler = new SendToGroupResponseHandler();
            }
        }
        return handler;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SendToGroupResponsePacket msg) throws Exception {
        System.out.println("来自群【" + msg.getGroupId() + "】的【" + msg.getUserName() + "】发送消息->" + msg.getMessage());
    }
}
