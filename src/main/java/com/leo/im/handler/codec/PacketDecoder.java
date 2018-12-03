package com.leo.im.handler.codec;

import com.leo.codec.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @Description: 数据包解码处理器
 *              ByteToMessageDecoder可以实现自定义解码，不用关心强转和解码结果的传递，
 *              使用ByteToMessageDecoder，Netty 会自动进行内存的释放
 * @Author: Leo
 * @Date: 2018-12-03 上午 11:16
 */
public class PacketDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List out) throws Exception {
        out.add(PacketCodec.INSTANCE().decode(in));
    }
}
