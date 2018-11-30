package com.leo.im;

import com.leo.bean.request.MessageRequestPacket;
import com.leo.codec.PacketCodec;
import com.leo.im.handler.ClientHandler;
import com.leo.util.LoginUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;


/**
 * @Description: IM客户端
 * @Author: Leo
 * @Date: 2018-11-30 上午 10:08
 */
public class IMClient {

    private static final String HOST = "127.0.0.1";

    private static final int PORT = 8080;

    private static final int MAX_RETRY = 5;

    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ch.pipeline().addLast(new ClientHandler());
                    }
                });
        connect(bootstrap, HOST, PORT, MAX_RETRY);
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("[" + host + "]:[" + port + "]连接成功");
                Channel channel = ((ChannelFuture) future).channel();
                // 启动控制台线程
                startConsoleThread(channel);
            } else if (retry == 0) {
                System.err.println("重连次数用完，停止重连。。。");
            } else {
                int order = (MAX_RETRY - retry) + 1;
                int delay = 1 << order;
                System.err.println("[" + host + "]:[" + port + "]连接失败，尝试第" + order + "次重连。。。");
                bootstrap.config().group().schedule(() ->
                        connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);
            }
        });
    }

    /**
     * 启动控制台线程
     *
     * @param channel
     */
    private static void startConsoleThread(Channel channel) {
        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (LoginUtil.hasLogin(channel)) {
                    System.out.println("输入要发送给服务端的数据：");
                    Scanner scanner = new Scanner(System.in);
                    MessageRequestPacket packet = new MessageRequestPacket();
                    packet.setMessage(scanner.nextLine());
                    ByteBuf byteBuf = PacketCodec.INSTANCE().encode(channel.alloc(), packet);
                    channel.writeAndFlush(byteBuf);
                }
            }
        }).start();
    }
}
