package com.leo.im;

import com.leo.im.handler.AuthHandler;
import com.leo.im.handler.HeartBeatRequestHandler;
import com.leo.im.handler.IMHandler;
import com.leo.im.handler.codec.PacketMessageCodec;
import com.leo.im.handler.codec.Spliter;
import com.leo.im.handler.IMIdleStateHandler;
import com.leo.im.handler.request.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Description: IM服务端
 * @Author: Leo
 * @Date: 2018-11-30 上午 10:08
 */
public class IMServer {

    private static final int PORT = 8080;

    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup work = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();

        bootstrap.group(boss, work)
                .channel(NioServerSocketChannel.class)
//                .option(ChannelOption.SO_BACKLOG, 2048)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
//                        ch.pipeline().addLast(new ServerHandler());
//                        ch.pipeline().addLast(new PacketDecoder());
                        // 空闲检测
                        ch.pipeline().addLast(new IMIdleStateHandler());
                        // 拆包，粘包解决
                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(PacketMessageCodec.INSTANCE());
                        ch.pipeline().addLast(LoginRequestHandler.INSTANCE());
                        ch.pipeline().addLast(HeartBeatRequestHandler.INSTANCE());
                        // 用户认证handler
                        ch.pipeline().addLast(AuthHandler.INSTANCE());
                        // 聊天逻辑
                        ch.pipeline().addLast(IMHandler.INSTANCE());
//                        ch.pipeline().addLast(new PacketEncoder());
                    }
                });
        bind(bootstrap, PORT);
    }

    private static void bind(ServerBootstrap bootstrap, int port) {
        bootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("端口号[" + port + "]连接绑定成功！");
            } else {
                System.out.println("端口号[" + port + "]连接绑定失败，尝试绑定[" + (port + 1) + "]");
                bind(bootstrap, port + 1);
            }
        });
    }
}
