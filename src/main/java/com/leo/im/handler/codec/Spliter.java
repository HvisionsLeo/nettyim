package com.leo.im.handler.codec;

import com.leo.codec.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @Description:
 * @Author: Leo
 * @Date: 2018-12-03 下午 2:08
 */
public class Spliter extends LengthFieldBasedFrameDecoder {

    private static final int LENGTH_FIELD_OFFSET = 7;

    private static final int LENGTH_FILED_LENGTH = 4;

    public Spliter() {
        super(Integer.MAX_VALUE, LENGTH_FIELD_OFFSET, LENGTH_FILED_LENGTH);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        // 屏蔽非本协议客户端
        if (in.getInt(in.readerIndex()) != PacketCodec.MAGIC_NUMBER) {
            System.out.println("获取到非本协议数据，关闭。。。");
            ctx.channel().close();
            return null;
        }
        return super.decode(ctx, in);
    }
}
