package com.leo.im.handler.request;

import com.leo.bean.request.CreateGroupRequestPacket;
import com.leo.bean.response.CreateGroupResponsePacket;
import com.leo.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 创建群聊请求处理器
 * @Author: Leo
 * @Date: 2018-12-10 下午 4:40
 */
public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {
    private static Integer GROUP_ID = 0;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket msg) throws Exception {
        List<String> userIdList = msg.getUserIdList();
        List<String> userNameList = new ArrayList<>();

        // 创建一个channel分组
        ChannelGroup group = new DefaultChannelGroup(ctx.executor());

        // 筛选出待加入群聊的用户channel和userName
        for (String userId : userIdList) {
            Channel channel = SessionUtil.getChannel(userId);
            if (channel != null) {
                group.add(channel);
                userNameList.add(SessionUtil.getSession(channel).getUserName());
            }
        }
        GROUP_ID++;
        String groupId = "G_" + GROUP_ID;
        SessionUtil.bindChannelGroup(groupId, group);
        // 创建群聊响应结果
        CreateGroupResponsePacket packet = new CreateGroupResponsePacket();
        packet.setSuccess(true);
        packet.setGroupId(groupId);
        packet.setUserNameList(userNameList);

        group.writeAndFlush(packet);
        System.out.println("群创建成功，id为[" + packet.getGroupId() + "]");
        System.out.println("群里面有：" + packet.getUserNameList());
    }
}
