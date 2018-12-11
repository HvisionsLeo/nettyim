package com.leo.im.handler.codec;

import com.leo.bean.Packet;
import com.leo.codec.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

/**
 * @Description: 编解码器，整合PacketEncoder,PacketDecoder
 *              既是InBound又是OutBound
 * @Author: Leo
 * @Date: 2018-12-03 下午 2:02
 */
@ChannelHandler.Sharable
public class PacketMessageCodec extends MessageToMessageCodec<ByteBuf, Packet> {

    private volatile static PacketMessageCodec codec;

    private PacketMessageCodec() {
    }

    public synchronized static PacketMessageCodec INSTANCE() {
        synchronized (PacketMessageCodec.class) {
            if (codec == null) {
                codec = new PacketMessageCodec();
            }
        }
        return codec;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, List<Object> out) throws Exception {
        ByteBuf byteBuf = ctx.channel().alloc().ioBuffer();
        PacketCodec.INSTANCE().encode(byteBuf, msg);
        out.add(byteBuf);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        out.add(PacketCodec.INSTANCE().decode(msg));
    }
}
