package com.leo.practice.netty.handler.outbound;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

import java.nio.charset.Charset;

/**
 * @Description: OutBoundHandlerB
 * @Author: Leo
 * @Date: 2018-12-03 上午 9:51
 */
public class OutBoundHandlerB extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println("OutBoundHandlerB:" + ((ByteBuf) msg).toString(Charset.forName("utf-8")));
        super.write(ctx, msg, promise);
    }
}
