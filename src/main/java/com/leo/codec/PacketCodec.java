package com.leo.codec;

import com.leo.bean.Packet;
import com.leo.serializer.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * @Description:
 * @Author: Leo
 * @Date: 2018-11-29 下午 5:46
 */
public class PacketCodec {

    private static final int MAGIC_NUMBER = 0x12345678;   // 魔数

    /**
     * 编码
     *
     * @param packet 数据包
     * @return byteBuf
     */
    public ByteBuf encode(Packet packet) {
        // 创建ByteBuf对象
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();
        // 序列化java对象
        byte[] bytes = Serializer.DEFAULT.serializer(packet);

        // 实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER); // 魔数
        byteBuf.writeByte(packet.getVersion()); // 版本
        byteBuf.writeByte(packet.getCommand()); // 序列化算法
        byteBuf.writeInt(bytes.length); // 内容长度
        byteBuf.writeBytes(bytes);  // 内容
        return byteBuf;
    }

    /**
     * 解码
     *
     * @param byteBuf byteBuf
     * @return 数据包
     */
    public Packet decode(ByteBuf byteBuf) {
        // 跳过魔数
        byteBuf.skipBytes(4);
        // 跳过版本号
        byteBuf.skipBytes(4);
        // 序列化算法标识
        byte serializerAlgorithm = byteBuf.readByte();

        // 指令
        byte command = byteBuf.readByte();

        // 数据长度
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);
//        Class<? extends Packet> requestType =
        return null;
    }
}
