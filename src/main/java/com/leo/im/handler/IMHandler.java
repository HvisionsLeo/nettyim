package com.leo.im.handler;

import com.leo.bean.Packet;
import com.leo.im.handler.request.*;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.HashMap;
import java.util.Map;

import static com.leo.bean.Command.*;

/**
 * @Description: IM聊天处理器
 * @Author: Leo
 * @Date: 2018-12-11 下午 4:09
 */
@ChannelHandler.Sharable
public class IMHandler extends SimpleChannelInboundHandler<Packet> {

    private volatile static IMHandler handler;
    private Map<Byte, SimpleChannelInboundHandler<? extends Packet>> handlerMap;

    private IMHandler() {
        handlerMap = new HashMap<>();

        handlerMap.put(MESSAGE_REQUEST, MessageRequestHandler.INSTANCE());
        handlerMap.put(LOGOUT_REQUEST, LogOutRequestHandler.INSTANCE());
        handlerMap.put(CREATE_GROUP_REQUEST, CreateGroupRequestHandler.INSTANCE());
        handlerMap.put(LIST_GROUP_MEMBERS_REQUEST, ListGroupMembersRequestHandler.INSTANCE());
        handlerMap.put(JOIN_GROUP_REQUEST, JoinGroupRequestHandler.INSTANCE());
        handlerMap.put(QUIT_GROUP_REQUEST, QuitGroupRequestHandler.INSTANCE());
        handlerMap.put(SEND_TO_GROUP_REQUEST, SendToGroupRequestHandler.INSTANCE());
    }

    public synchronized static IMHandler INSTANCE() {
        synchronized (IMHandler.class) {
            if (handler == null) {
                handler = new IMHandler();
            }
        }
        return handler;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        // 通过具体命令找到相应的handler
        handlerMap.get(msg.getCommand()).channelRead(ctx, msg);
    }
}
