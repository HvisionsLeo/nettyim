package com.leo.practice.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * @Description: 服务端handler
 * @Author: Leo
 * @Date: 2018-11-29 上午 11:41
 */
public class FirstServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String data = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + "客户端已连接！";
        ByteBuf b = getByteBuf(ctx, data);
        ctx.channel().writeAndFlush(b);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String data = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + "服务端收到消息：你好，Netty!";
        // 接收数据
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("接收到客户端数据->" + byteBuf.toString(Charset.forName("utf-8")));
        // 回复给客户端
        ByteBuf b = getByteBuf(ctx, data);
        ctx.channel().writeAndFlush(b);
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx, String data) {

        ByteBuf byteBuf = ctx.alloc().buffer();
        byte[] bytes = data.getBytes(Charset.forName("utf-8"));
        byteBuf.writeBytes(bytes);
        System.out.println("向客户端发送消息->" + data);
        return byteBuf;
    }
}
