package com.leo.im.handler.request;

import com.leo.bean.request.LoginRequestPacket;
import com.leo.bean.response.LoginResponsePacket;
import com.leo.session.Session;
import com.leo.util.LoginUtil;
import com.leo.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Description: 登录请求处理器
 * SimpleChannelInboundHandler可以实现每一种指令的处理，不需要强转，不再有冗余的if...else...逻辑，
 * 不需要手动传递对象
 * @Author: Leo
 * @Date: 2018-12-03 上午 11:10
 */
// 加上注解标识，表示该handler是可以被多个channel共享的
@ChannelHandler.Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    private volatile static LoginRequestHandler handler;

    private LoginRequestHandler() {
    }

    // 构造单例
    public synchronized static LoginRequestHandler INSTANCE() {
        synchronized (LoginRequestHandler.class) {
            if (handler == null) {
                handler = new LoginRequestHandler();
            }
        }
        return handler;
    }

    private static Integer USER_ID = 0;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) throws Exception {
        System.out.println("接收到登录请求-> username:" + msg.getUsername() + ", password:" + msg.getPassword());
        LoginResponsePacket packet = new LoginResponsePacket();
        packet.setUserName(msg.getUsername());
        if (valid(msg)) {
            USER_ID++;
            String userId = "U_" + USER_ID;
            packet.setUserId(userId);
            SessionUtil.bindSession(new Session(packet.getUserId(), packet.getUserName()), ctx.channel());
            packet.setSuccess(true);
        } else {
            packet.setSuccess(false);
            packet.setReason("密码不正确！");
        }
        // ctx.writeAndFlush 是从 pipeline 链中的当前节点开始往前找到第一个 outBound 类型的 handler
        // 把对象往前进行传播，如果这个对象确认不需要经过其他 outBound 类型的 handler 处理，就使用这个方法。
        // ctx.channel().writeAndFlush是从 pipeline 链中的最后一个 outBound 类型的 handler 开始，
        // 把对象往前进行传播，如果你确认当前创建的对象需要经过后面的 outBound 类型的 handler，那么就调用此方法
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
