package com.leo.practice.netty;

import com.leo.practice.netty.handler.FirstServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Description: netty服务端
 * @Author: Leo
 * @Date: 2018-11-28 下午 12:03
 */
public class NettyServer {

    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        // 对应IOServer中接受新连接线程，主要负责创建新的连接
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        // 对应IOServer中的负责读取数据的线程，主要用于读取数据以及业务逻辑处理
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        serverBootstrap
                // 给引导类配置两大线程组
                .group(bossGroup, workGroup)
                // 指定服务端的 IO 模型为NIO,若为BIO则配置OioServerSocketChannel.class
                .channel(NioServerSocketChannel.class)
                // 给每条连接设置一些TCP底层相关的属性
                .childOption(ChannelOption.SO_KEEPALIVE, true)  // 表示是否开启TCP底层心跳机制，true为开启
                .childOption(ChannelOption.TCP_NODELAY, true)   // 表示是否开启Nagle算法，true表示关闭，false表示开启，通俗地说，如果要求高实时性，有数据发送时就马上发送，就关闭，如果需要减少发送次数减少网络交互，就开启。
                // 给服务端设置一些属性
                .option(ChannelOption.SO_BACKLOG, 1024)
                // 用于指定在服务端启动过程中的一些逻辑,通常用不到
                .handler(new ChannelInitializer<NioServerSocketChannel>() {
                    @Override
                    protected void initChannel(NioServerSocketChannel ch) throws Exception {
                        System.out.println("服务端启动中。。。");
                    }
                })
                // 业务处理逻辑
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
//                        nioSocketChannel.pipeline().addLast(new StringDecoder());
                        nioSocketChannel.pipeline().addLast(new FirstServerHandler());
                    }
                });
        bind(serverBootstrap, 8081);
    }

    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(future -> {
            // 判断是否绑定成功
            if (future.isSuccess()) {
                System.out.println("端口号：[" + port + "]绑定成功！");
            } else {
                // 绑定失败，则将端口号+1继续绑定
                System.err.println("端口号：[" + port + "]绑定失败！");
                bind(serverBootstrap, port + 1);
            }
        });
    }
}
