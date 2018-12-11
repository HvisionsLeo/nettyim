package com.leo.im.handler.request;

import com.leo.bean.request.JoinGroupRequestPacket;
import com.leo.bean.response.JoinGroupResponsePacket;
import com.leo.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * @Description: 加入群聊请求处理器
 * @Author: Leo
 * @Date: 2018-12-11 上午 10:33
 */
@ChannelHandler.Sharable
public class JoinGroupRequestHandler extends SimpleChannelInboundHandler<JoinGroupRequestPacket> {

    private volatile static JoinGroupRequestHandler handler;

    private JoinGroupRequestHandler() {
    }

    public synchronized static JoinGroupRequestHandler INSTANCE() {
        synchronized (JoinGroupRequestHandler.class) {
            if (handler == null) {
                handler = new JoinGroupRequestHandler();
            }
        }
        return handler;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupRequestPacket msg) throws Exception {
        // 获取群对应的channelGroup,将当前的用户加入
        String groupId = msg.getGroupId();
        ChannelGroup group = SessionUtil.getChannelGroup(groupId);
        group.add(ctx.channel());

        // 构造加群响应发送给客户端
        JoinGroupResponsePacket packet = new JoinGroupResponsePacket();
        packet.setGroupId(groupId);
        packet.setSuccess(true);
        ctx.channel().writeAndFlush(packet);
    }

}
