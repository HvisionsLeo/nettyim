package com.leo.im.handler.codec;

import com.leo.bean.Packet;
import com.leo.codec.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Description: 数据包编码
 *               MessageToByteEncoder实现自定义编码，不用关心byteBuf的创建，不用每次对java对象进行编码
 * @Author: Leo
 * @Date: 2018-12-03 上午 11:14
 */
public class PacketEncoder extends MessageToByteEncoder<Packet> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) throws Exception {
        PacketCodec.INSTANCE().encode(out, msg);
    }
}
