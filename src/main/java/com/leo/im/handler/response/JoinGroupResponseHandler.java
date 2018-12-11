package com.leo.im.handler.response;

import com.leo.bean.response.JoinGroupResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Description: 加入群聊响应处理器
 * @Author: Leo
 * @Date: 2018-12-11 上午 10:40
 */
@ChannelHandler.Sharable
public class JoinGroupResponseHandler extends SimpleChannelInboundHandler<JoinGroupResponsePacket> {

    private volatile static JoinGroupResponseHandler handler;

    private JoinGroupResponseHandler() {
    }

    public synchronized static JoinGroupResponseHandler INSTANCE() {
        synchronized (JoinGroupResponseHandler.class) {
            if (handler == null) {
                handler = new JoinGroupResponseHandler();
            }
        }
        return handler;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupResponsePacket msg) throws Exception {
        if (msg.isSuccess()) {
            System.out.println("加入群【" + msg.getGroupId() + "】成功！");
        } else {
            System.out.println("加入群【" + msg.getGroupId() + "】失败，原因：" + msg.getReason());
        }
    }
}
