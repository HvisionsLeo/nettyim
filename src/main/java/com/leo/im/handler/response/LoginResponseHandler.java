package com.leo.im.handler.response;

import com.leo.bean.response.LoginResponsePacket;
import com.leo.session.Session;
import com.leo.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Description: 登录回复处理器
 * @Author: Leo
 * @Date: 2018-12-03 上午 11:12
 */
@ChannelHandler.Sharable
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    private volatile static LoginResponseHandler handler;

    private LoginResponseHandler() {
    }

    public synchronized static LoginResponseHandler INSTANCE() {
        synchronized (LoginResponseHandler.class) {
            if (handler == null) {
                handler = new LoginResponseHandler();
            }
        }
        return handler;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端开始登录。。。");
        // 创建登录对象
//        LoginRequestPacket packet = new LoginRequestPacket();
//        packet.setUserId(UUID.randomUUID().toString());
//        packet.setUsername("client#1");
//        packet.setPassword("123456");
//        // 写入数据
//        ctx.channel().writeAndFlush(packet);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) throws Exception {
        if (msg.isSuccess()) {
            SessionUtil.bindSession(new Session(msg.getUserId(), msg.getUserName()), ctx.channel());
            System.out.println("登陆成功！");
        } else {
            System.out.println("登录失败，原因：" + msg.getReason());
        }
    }

}
