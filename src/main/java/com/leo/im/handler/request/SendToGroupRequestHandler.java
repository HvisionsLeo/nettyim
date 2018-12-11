package com.leo.im.handler.request;

import com.leo.bean.request.SendToGroupRequestPacket;
import com.leo.bean.response.SendToGroupResponsePacket;
import com.leo.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * @Description: 群组发送消息接受处理器
 * @Author: Leo
 * @Date: 2018-12-11 下午 3:02
 */
@ChannelHandler.Sharable
public class SendToGroupRequestHandler extends SimpleChannelInboundHandler<SendToGroupRequestPacket> {

    private volatile static SendToGroupRequestHandler handler;

    private SendToGroupRequestHandler() {
    }

    public synchronized static SendToGroupRequestHandler INSTANCE() {
        synchronized (SendToGroupRequestHandler.class) {
            if (handler == null) {
                handler = new SendToGroupRequestHandler();
            }
        }
        return handler;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SendToGroupRequestPacket msg) throws Exception {
        String groupId = msg.getGroupId();
        ChannelGroup group = SessionUtil.getChannelGroup(groupId);
        SendToGroupResponsePacket packet = new SendToGroupResponsePacket();
        packet.setGroupId(groupId);
        packet.setUserName(SessionUtil.getSession(ctx.channel()).getUserName());
        packet.setMessage(msg.getMessage());
        group.writeAndFlush(packet);
    }
}
