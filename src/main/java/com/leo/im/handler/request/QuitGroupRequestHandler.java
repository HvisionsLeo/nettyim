package com.leo.im.handler.request;

import com.leo.bean.request.QuitGroupRequestPacket;
import com.leo.bean.response.QuitGroupResponsePacket;
import com.leo.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * @Description: 退出群聊请求处理器
 * @Author: Leo
 * @Date: 2018-12-11 上午 10:46
 */
public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupRequestPacket msg) throws Exception {
        String groupId = msg.getGroupId();
        ChannelGroup group = SessionUtil.getChannelGroup(groupId);
        group.remove(ctx.channel());

        QuitGroupResponsePacket packet = new QuitGroupResponsePacket();
        packet.setGroupId(groupId);
        packet.setSuccess(true);
        ctx.channel().writeAndFlush(packet);
    }
}
