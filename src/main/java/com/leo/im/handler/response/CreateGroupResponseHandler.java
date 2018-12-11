package com.leo.im.handler.response;

import com.leo.bean.response.CreateGroupResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Description: 创建群组响应
 * @Author: Leo
 * @Date: 2018-12-10 下午 4:59
 */
@ChannelHandler.Sharable
public class CreateGroupResponseHandler extends SimpleChannelInboundHandler<CreateGroupResponsePacket> {

    private volatile static CreateGroupResponseHandler handler;

    private CreateGroupResponseHandler() {
    }

    public synchronized static CreateGroupResponseHandler INSTANCE() {
        synchronized (CreateGroupResponseHandler.class) {
            if (handler == null) {
                handler = new CreateGroupResponseHandler();
            }
        }
        return handler;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupResponsePacket msg) throws Exception {
        System.out.println("群创建成功，id为[" + msg.getGroupId() + "]");
        System.out.println("群里面有：" + msg.getUserNameList());
    }
}
