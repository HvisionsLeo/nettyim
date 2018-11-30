package com.leo.im.handler;

import com.leo.bean.request.LoginRequestPacket;
import com.leo.bean.Packet;
import com.leo.bean.response.LoginResponsePacket;
import com.leo.codec.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.UUID;

/**
 * @Description: 客户端处理器
 * @Author: Leo
 * @Date: 2018-11-30 上午 10:09
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端开始登录。。。");
        // 创建登录对象
        LoginRequestPacket packet = new LoginRequestPacket();
        packet.setUserId(UUID.randomUUID().toString());
        packet.setUsername("client-1");
        packet.setPassword("123456");
        // 编码
        ByteBuf byteBuf = PacketCodec.INSTANCE().encode(ctx.alloc(), packet);
        // 写入数据
        ctx.channel().writeAndFlush(byteBuf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;

        Packet packet = PacketCodec.INSTANCE().decode(byteBuf);

        if (packet instanceof LoginResponsePacket) {
            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;
            if (loginResponsePacket.isSuccess()) {
                System.out.println("登陆成功！");
            } else {
                System.out.println("登录失败，原因：" + loginResponsePacket.getReason());
            }
        }
    }
}
