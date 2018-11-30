package com.leo.im;

import com.leo.im.handler.ServerHandler;
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
                        ch.pipeline().addLast(new ServerHandler());
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
