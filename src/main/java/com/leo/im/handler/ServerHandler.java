package com.leo.im.handler;

import com.leo.bean.Packet;
import com.leo.bean.request.LoginRequestPacket;
import com.leo.bean.request.MessageRequestPacket;
import com.leo.bean.response.LoginResponsePacket;
import com.leo.bean.response.MessageResponsePacket;
import com.leo.codec.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Description: 服务端处理器
 * @Author: Leo
 * @Date: 2018-11-30 上午 10:09
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;

        // 解码
        Packet packet = PacketCodec.INSTANCE().decode(byteBuf);
        // 判断是否为登录请求包
        if (packet instanceof LoginRequestPacket) {
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;
            LoginResponsePacket rtnPacket = new LoginResponsePacket();
            if (valid(loginRequestPacket)) {
                // 登录成功
                rtnPacket.setSuccess(true);
            } else {
                // 登录失败
                rtnPacket.setSuccess(false);
                rtnPacket.setReason("密码不正确！");
            }
            ByteBuf rtnBuf = PacketCodec.INSTANCE().encode(ctx.alloc(), rtnPacket);
            ctx.channel().writeAndFlush(rtnBuf);
        } else if (packet instanceof MessageRequestPacket) {
            MessageRequestPacket requestPacket = (MessageRequestPacket) packet;
            MessageResponsePacket rtnPacket = new MessageResponsePacket();
            System.out.println("接收到客户端发送消息：" +  requestPacket.getMessage());
            rtnPacket.setMessage(requestPacket.getMessage());
            ByteBuf rtnBuf = PacketCodec.INSTANCE().encode(ctx.alloc(), rtnPacket);
            ctx.channel().writeAndFlush(rtnBuf);
        }
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        if ("123456".equals(loginRequestPacket.getPassword())) {
            return true;
        } else {
            return false;
        }
    }
}
