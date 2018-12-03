package com.leo.im.handler.codec;

import com.leo.bean.Packet;
import com.leo.codec.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

import java.util.List;

/**
 * @Description: 编解码器，整合PacketEncoder,PacketDecoder
 * @Author: Leo
 * @Date: 2018-12-03 下午 2:02
 */
public class PacketMessageCodec extends ByteToMessageCodec<Packet> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) throws Exception {
        PacketCodec.INSTANCE().encode(out, msg);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        out.add(PacketCodec.INSTANCE().decode(in));
    }
}
