package com.leo.practice.netty.handler.inbound;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

/**
 * @Description: InBoundHandlerB
 * @Author: Leo
 * @Date: 2018-12-03 上午 9:45
 */
public class InBoundHandlerB extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("InBoundHandlerB:" + ((ByteBuf) msg).toString(Charset.forName("utf-8")));
        super.channelRead(ctx, msg);
    }
}
