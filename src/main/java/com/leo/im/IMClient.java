package com.leo.im;

import com.leo.command.impl.ConsoleCommandManager;
import com.leo.command.impl.LoginConsoleCommand;
import com.leo.im.handler.codec.PacketMessageCodec;
import com.leo.im.handler.codec.Spliter;
import com.leo.im.handler.response.*;
import com.leo.util.SessionUtil;
import io.netty.bootstrap.Bootstrap;
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

    public static void start() {
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
//                        ch.pipeline().addLast(new ClientHandler());
//                        ch.pipeline().addLast(new PacketDecoder());
                        // 拆包，粘包解决
                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(PacketMessageCodec.INSTANCE());
                        ch.pipeline().addLast(LoginResponseHandler.INSTANCE());
                        ch.pipeline().addLast(CreateGroupResponseHandler.INSTANCE());
                        ch.pipeline().addLast(JoinGroupResponseHandler.INSTANCE());
                        ch.pipeline().addLast(QuitGroupResponseHandler.INSTANCE());
                        ch.pipeline().addLast(ListGroupMembersResponseHandler.INSTANCE());
                        ch.pipeline().addLast(SendToGroupResponseHandler.INSTANCE());
                        ch.pipeline().addLast(MessageResponseHandler.INSTANCE());
                        ch.pipeline().addLast(LogOutResponseHandler.INSTANCE());
//                        ch.pipeline().addLast(new PacketEncoder());
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
        ConsoleCommandManager consoleCommand = new ConsoleCommandManager();
        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();
        Scanner scanner = new Scanner(System.in);
        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (SessionUtil.hasLogin(channel)) {
                    consoleCommand.exec(scanner, channel);
                } else {
                    loginConsoleCommand.exec(scanner, channel);
                }
            }
        }).start();
    }
}
