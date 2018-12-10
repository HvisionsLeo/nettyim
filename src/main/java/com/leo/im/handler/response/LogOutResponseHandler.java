package com.leo.im.handler.response;

import com.leo.bean.response.LogOutResponsePacket;
import com.leo.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Description:
 * @Author: Leo
 * @Date: 2018-12-10 下午 5:24
 */
public class LogOutResponseHandler extends SimpleChannelInboundHandler<LogOutResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogOutResponsePacket msg) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
        ctx.channel().close();
    }
}
