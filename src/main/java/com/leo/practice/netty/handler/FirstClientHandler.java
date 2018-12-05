package com.leo.practice.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * @Description: 客户端handler
 * @Author: Leo
 * @Date: 2018-11-29 上午 11:27
 */
public class FirstClientHandler extends ChannelInboundHandlerAdapter {

    // 在客户端建立成功之后触发
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        for (int i = 0; i < 1000; i++) {
            // 获取数据
            ByteBuf buf = getByteBuf(ctx);
            // 写入数据
            ctx.channel().writeAndFlush(buf);
//        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("接收到服务端返回的消息->" + byteBuf.toString(Charset.forName("utf-8")));
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        String data = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + "你好，Netty!\t";
        // ctx.alloc()获取一个ByteBuf 内存管理器
        // 获取二进制抽象ByteBuf
        ByteBuf byteBuf = ctx.alloc().buffer();
        // 准备数据，指定字符串的字符集为utf-8
        byte[] bytes = data.getBytes(Charset.forName("utf-8"));
        // 填充数据到byteBuf
        byteBuf.writeBytes(bytes);
        System.out.println("客户端发送数据->" + data);
        return byteBuf;
    }
}
