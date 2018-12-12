package com.leo.im.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @Description: 空闲检测机制
 * @Author: Leo
 * @Date: 2018-12-12 下午 2:38
 */
public class IMIdleStateHandler extends IdleStateHandler {

    private final static Integer READ_IDLE_TIME = 15;

    public IMIdleStateHandler() {
        // 第一个参数表示读空闲时间
        // 第二个参数表示写空闲时间
        // 第三个参数表示读写空闲时间
        // 为0时，表示不关心这两类条件
        super(READ_IDLE_TIME, 0,0, TimeUnit.SECONDS);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        System.out.println(READ_IDLE_TIME+"秒内未读到数据，关闭连接！");
        ctx.channel().close();
    }
}
