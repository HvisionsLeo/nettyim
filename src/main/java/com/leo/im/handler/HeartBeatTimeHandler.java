package com.leo.im.handler;

import com.leo.bean.request.HeartBeatRequestPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: Leo
 * @Date: 2018-12-12 下午 2:50
 */
public class HeartBeatTimeHandler extends ChannelInboundHandlerAdapter {

    // 这个通常要比服务端空闲检测时间一半要短一些
    // 为了排除偶发的公网抖动，防止误判
    private final static Integer HEART_BEAT_TIME = 5;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        scheduleSendHeartBeat(ctx);
        super.channelActive(ctx);
    }

    private void scheduleSendHeartBeat(ChannelHandlerContext ctx) {
        ctx.executor().schedule(() -> {
            if (ctx.channel().isActive()){
                ctx.writeAndFlush(new HeartBeatRequestPacket());
                scheduleSendHeartBeat(ctx);
            }
        }, HEART_BEAT_TIME, TimeUnit.SECONDS);
    }
}
