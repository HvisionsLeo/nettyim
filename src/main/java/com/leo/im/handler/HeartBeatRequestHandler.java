package com.leo.im.handler;

import com.leo.bean.request.HeartBeatRequestPacket;
import com.leo.bean.response.HeartBeatResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Description: 心跳接受处理器
 * @Author: Leo
 * @Date: 2018-12-12 下午 3:05
 */
@ChannelHandler.Sharable
public class HeartBeatRequestHandler extends SimpleChannelInboundHandler<HeartBeatRequestPacket> {

    private volatile static HeartBeatRequestHandler handler;

    private HeartBeatRequestHandler() {
    }

    public synchronized static HeartBeatRequestHandler INSTANCE() {
        synchronized (HeartBeatRequestHandler.class) {
            if (handler == null) {
                handler = new HeartBeatRequestHandler();
            }
        }
        return handler;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartBeatRequestPacket msg) throws Exception {
        ctx.writeAndFlush(new HeartBeatResponsePacket());
    }
}
