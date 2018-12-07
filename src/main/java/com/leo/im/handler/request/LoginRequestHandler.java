package com.leo.im.handler.request;

import com.leo.bean.request.LoginRequestPacket;
import com.leo.bean.response.LoginResponsePacket;
import com.leo.session.Session;
import com.leo.util.LoginUtil;
import com.leo.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Description: 登录请求处理器
 * SimpleChannelInboundHandler可以实现每一种指令的处理，不需要强转，不再有冗余的if...else...逻辑，
 * 不需要手动传递对象
 * @Author: Leo
 * @Date: 2018-12-03 上午 11:10
 */
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) throws Exception {
        System.out.println("接收到登录请求-> username:" + msg.getUsername() + ", password:" + msg.getPassword());
        LoginResponsePacket packet = new LoginResponsePacket();
        packet.setUserId(msg.getUserId());
        packet.setUserName(msg.getUsername());
        if (valid(msg)) {
            SessionUtil.bindSession(new Session(msg.getUserId(), msg.getUsername()), ctx.channel());
            packet.setSuccess(true);
        } else {
            packet.setSuccess(false);
            packet.setReason("密码不正确！");
        }
        ctx.channel().writeAndFlush(packet);
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        if ("123456".equals(loginRequestPacket.getPassword())) {
            return true;
        } else {
            return false;
        }
    }

}
