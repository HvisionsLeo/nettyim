package com.leo.im.handler.response;

import com.leo.bean.response.LogOutResponsePacket;
import com.leo.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Description: 登出响应处理器
 * @Author: Leo
 * @Date: 2018-12-10 下午 5:24
 */
@ChannelHandler.Sharable
public class LogOutResponseHandler extends SimpleChannelInboundHandler<LogOutResponsePacket> {

    private volatile static LogOutResponseHandler handler;

    private LogOutResponseHandler() {
    }

    public synchronized static LogOutResponseHandler INSTANCE() {
        synchronized (LogOutResponseHandler.class) {
            if (handler == null) {
                handler = new LogOutResponseHandler();
            }
        }
        return handler;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogOutResponsePacket msg) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
        ctx.channel().close();
    }
}
