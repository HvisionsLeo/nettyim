package com.leo.im.handler;

import com.leo.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Description: 登陆校验
 * @Author: Leo
 * @Date: 2018-12-06 下午 3:33
 */
@ChannelHandler.Sharable
public class AuthHandler extends ChannelInboundHandlerAdapter {

    private volatile static AuthHandler handler;

    private AuthHandler() {
    }

    public synchronized static AuthHandler INSTANCE() {
        synchronized (AuthHandler.class) {
            if (handler == null) {
                handler = new AuthHandler();
            }
        }
        return handler;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!SessionUtil.hasLogin(ctx.channel())) {
            ctx.channel().close();
        } else {
            // 一行代码实现逻辑删除
            ctx.channel().pipeline().remove(this);
            super.channelRead(ctx, msg);
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        if (SessionUtil.hasLogin(ctx.channel())) {
            System.out.println("当前连接登录验证完毕，无需再次验证，AuthHandler被移除！");
        } else {
            System.out.println("无登录验证，强制关闭！");
        }
    }
}
