package com.leo.practice.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * @Description: byteBuf练习
 * @Author: Leo
 * @Date: 2018-11-29 下午 2:36
 */
public class ByteBufTest {

    public static void main(String[] args) {
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer(9, 100);
        print("allocator", byteBuf);

        // write方法改变写指针，写完之后写指针未到capacity的时候，buffer仍然可写
        byteBuf.writeBytes(new byte[]{1, 2, 3, 4});
        print("writeBytes(new byte[]{1, 2, 3, 4})", byteBuf);

        // write方法改变写指针，写完之后写指针未到capacity的时候，buffer仍然可写，写完int类型之后，写指针增加4
        byteBuf.writeInt(12);
        print("writeInt(12)", byteBuf);

        // write 方法改变写指针，写完之后写指针等于 capacity 的时候，buffer 不可写
        byteBuf.writeBytes(new byte[]{5});
        print("writeByte(5)", byteBuf);

        // write 方法改变写指针，写的时候发现buffer不可写开始扩容，扩容之后capacity随即改变
        byteBuf.writeBytes(new byte[]{6});
        print("writeByte(6)", byteBuf);
        // get方法不改变读写指针
        System.out.println("getByte(3)->" + byteBuf.getByte(3));
        System.out.println("getShort(3)->" + byteBuf.getShort(3));
        System.out.println("getInt(3)->" + byteBuf.getInt(3));
        print("getByte()", byteBuf);

        // set方法不会改变读写指针
        byteBuf.setByte(byteBuf.readableBytes() + 1, 0);
        print("setByte()", byteBuf);

        // 都维持着与原始 ByteBuf 相同的内存引用计数和不同的读写指针
        // 与原始的 ByteBuf 的读写指针无关，相互之间不受影响
        // 原始的ByteBuf调用release的时候byteBuf1和byteBuf2也会被释放，因为使用了相同的内存引用计数
        // slice() 方法从原始 ByteBuf 中截取一段，这段数据是从 readerIndex 到 writeIndex
        ByteBuf byteBuf1 = byteBuf.slice();
        print("byteBuf-slice()->", byteBuf);
        print("byteBuf1-slice()->", byteBuf1);
        byteBuf1.readBytes(new byte[byteBuf1.readableBytes()]);
        print("byteBuf-slice()-readBytes->", byteBuf);
        print("byteBuf1-slice()-readBytes->", byteBuf1);

        // duplicate() 方法把整个 ByteBuf 都截取出来，包括所有的数据，指针信息
        ByteBuf byteBuf2 = byteBuf.duplicate();
        print("byteBuf-duplicate()->", byteBuf);
        print("byteBuf2-duplicate()->", byteBuf2);
        byteBuf2.readBytes(new byte[byteBuf2.readableBytes()]);
        print("byteBuf-duplicate()-readBytes->", byteBuf);
        print("byteBuf2-duplicate()-readBytes->", byteBuf2);
        byteBuf2.writeBytes(new byte[]{6});
        print("byteBuf-duplicate()-writeBytes->", byteBuf);
        print("byteBuf2-duplicate()-writeBytes->", byteBuf2);
        // copy() 会直接从原始的 ByteBuf 中拷贝所有的信息，包括读写指针以及底层对应的数据，
        // 因此，往 copy() 返回的 ByteBuf 中写数据不会影响到原始的 ByteBuf
        ByteBuf byteBuf3 = byteBuf.copy();
        print("byteBuf-copy()->", byteBuf);
        print("byteBuf3-copy()->", byteBuf3);
        byteBuf3.readBytes(byteBuf3.readableBytes());
        print("byteBuf-copy()-readBytes->", byteBuf);
        print("byteBuf3-copy()-readBytes->", byteBuf3);
        byteBuf3.writeBytes(new byte[]{6});
        print("byteBuf-copy()-writeBytes->", byteBuf);
        print("byteBuf3-copy()-writeBytes->", byteBuf3);
        // read方法会改变读写指针
        byte[] dts = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(dts);
        print("readBytes", byteBuf);
        byteBuf.release();
//        byteBuf1.release(); 再次释放会报错
//        byteBuf2.release(); 再次释放会报错
        byteBuf3.release(); // 不会报错

    }

    private static void print(String action, ByteBuf byteBuf) {
        System.out.println("after ==========" + action + "==========");
        System.out.println("capacity()->" + byteBuf.capacity());
        System.out.println("maxCapacity()->" + byteBuf.maxCapacity());
        System.out.println("readerIndex()->" + byteBuf.readerIndex());
        System.out.println("readableBytes()->" + byteBuf.readableBytes());
        System.out.println("isReadable()->" + byteBuf.isReadable());
        System.out.println("writerIndex()->" + byteBuf.writerIndex());
        System.out.println("writableBytes()->" + byteBuf.writableBytes());
        System.out.println("isWritable()->" + byteBuf.isWritable());
        System.out.println("maxWritableBytes()->" + byteBuf.maxWritableBytes());
        System.out.println();
    }
}
