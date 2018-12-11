package com.leo.im.handler.request;

import com.leo.bean.request.LogOutRequestPacket;
import com.leo.bean.response.LogOutResponsePacket;
import com.leo.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Description:
 * @Author: Leo
 * @Date: 2018-12-10 下午 5:21
 */
@ChannelHandler.Sharable
public class LogOutRequestHandler extends SimpleChannelInboundHandler<LogOutRequestPacket> {

    private volatile static LogOutRequestHandler handler;

    private LogOutRequestHandler() {
    }

    public synchronized static LogOutRequestHandler INSTANCE() {
        synchronized (LogOutRequestHandler.class) {
            if(handler == null){
                handler = new LogOutRequestHandler();
            }
        }
        return handler;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogOutRequestPacket msg) throws Exception {
        System.out.println(SessionUtil.getSession(ctx.channel()).getUserName() + "已经登出！");
        SessionUtil.unBindSession(ctx.channel());
        LogOutResponsePacket packet = new LogOutResponsePacket();
        packet.setSuccess(true);
        packet.setReason("已经登出！");
        ctx.channel().writeAndFlush(packet);
    }
}
