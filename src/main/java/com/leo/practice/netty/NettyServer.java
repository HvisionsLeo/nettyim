package com.leo.practice.netty;

import com.leo.practice.netty.handler.FirstServerHandler;
import com.leo.practice.netty.handler.LifeCycleTestHandler;
import com.leo.practice.netty.handler.inbound.InBoundHandlerA;
import com.leo.practice.netty.handler.inbound.InBoundHandlerB;
import com.leo.practice.netty.handler.inbound.InBoundHandlerC;
import com.leo.practice.netty.handler.outbound.OutBoundHandlerA;
import com.leo.practice.netty.handler.outbound.OutBoundHandlerB;
import com.leo.practice.netty.handler.outbound.OutBoundHandlerC;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;

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
                        // 固定长度拆包器FixedLengthFrameDecoder
//                        nioSocketChannel.pipeline().addLast(new FixedLengthFrameDecoder(34));
                        // 换行符拆包器LineBasedFrameDecoder \n
//                        nioSocketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
                        // 自定义分隔符拆包器
//                        ByteBuf byteBuf = Unpooled.copiedBuffer("\t".getBytes());
//                        nioSocketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, byteBuf));
                        // 基于长度拆包器 （最大长度，长度域的偏移量，长度域的长度）
//                        nioSocketChannel.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 7, 4));
                        nioSocketChannel.pipeline().addLast(new LifeCycleTestHandler());
                        nioSocketChannel.pipeline().addLast(new FirstServerHandler());
                        // inBoundHandler 处理读取数据的逻辑链 A->B->C
//                        nioSocketChannel.pipeline().addLast(new InBoundHandlerA());
//                        nioSocketChannel.pipeline().addLast(new InBoundHandlerB());
//                        nioSocketChannel.pipeline().addLast(new InBoundHandlerC());

                        // outBoundHandler 处理写数据的逻辑链 outBound执行顺序和添加顺序相反 C->B->A
//                        nioSocketChannel.pipeline().addLast(new OutBoundHandlerA());
//                        nioSocketChannel.pipeline().addLast(new OutBoundHandlerB());
//                        nioSocketChannel.pipeline().addLast(new OutBoundHandlerC());
                    }
                });
        bind(serverBootstrap, 8081);
    }

    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(future -> {
            // 判断是否绑定成功
            if (future.isSuccess()) {
                System.out.println("端口号：[" + port + "]绑定成功！");
                getCount();
            } else {
                // 绑定失败，则将端口号+1继续绑定
                System.err.println("端口号：[" + port + "]绑定失败！");
                bind(serverBootstrap, port + 1);
            }
        });
    }

    private static void getCount() {
        new Thread(() -> {
            while (true) {
                System.out.println("当前在线人数：" + LifeCycleTestHandler.COUNT);
                System.out.println("当前接收到字节：" + LifeCycleTestHandler.ACCEPT_BYTE + "bit");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
