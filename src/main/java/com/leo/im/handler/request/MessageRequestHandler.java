package com.leo.im.handler.request;

import com.leo.bean.request.MessageRequestPacket;
import com.leo.bean.response.MessageResponsePacket;
import com.leo.session.Session;
import com.leo.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Description: 消息请求处理器
 * @Author: Leo
 * @Date: 2018-12-03 上午 11:11
 */
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket msg) throws Exception {
        System.out.println("服务端接收到消息->" + msg.getMessage());
        if ("exit".equals(msg.getMessage())) {
            // 接收到退出命令，执行关闭channel操作
            ctx.channel().close();
        } else {
            // 获取发送方信息
            Session session = SessionUtil.getSession(ctx.channel());

            // 构造发送消息包
            MessageResponsePacket packet = new MessageResponsePacket();
            packet.setFromUserId(session.getUserId());
            packet.setFromUserName(session.getUserName());

            // 获取接收方channel
            Channel toChannel = SessionUtil.getChannel(msg.getToUserId());
            // 发送消息给接收方
            if (toChannel != null && SessionUtil.hasLogin(toChannel)) {
                packet.setMessage(msg.getMessage());
                toChannel.writeAndFlush(packet);
            } else {
                packet.setMessage("[" + msg.getToUserId() + "]:[" + msg.getMessage() + "]发送失败，对方不在线！");
                ctx.channel().writeAndFlush(packet);
            }
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Session session = SessionUtil.getSession(ctx.channel());
        System.out.println(session.getUserId() + ":" + session.getUserName() + "已经登出！");
        SessionUtil.unBindSession(ctx.channel());
    }
}
