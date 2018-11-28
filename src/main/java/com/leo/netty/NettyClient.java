package com.leo.netty;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @Description: netty客户端
 * @Author: Leo
 * @Date: 2018-11-28 下午 12:13
 */
public class NettyClient {
    private static int MAX_RETRY = 5;

    public static void main(String[] args) {

        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();

        bootstrap
                // 指定线程魔性
                .group(group)
                // 指定IO类型为NIO
                .channel(NioSocketChannel.class)
                // 设置TCP底层属性
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000) // 超时时间
                .option(ChannelOption.SO_KEEPALIVE, true)   // 心跳机制
                .option(ChannelOption.TCP_NODELAY, true)
                // IO 处理逻辑
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        channel.pipeline().addLast(new StringEncoder());
                    }
                });
        // 建立连接
        connect(bootstrap, "127.0.0.1", 8081, MAX_RETRY);
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功！");
            } else if (retry == 0) {
                System.err.println(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + "重试次数用完，放弃连接。。。");
            } else {
                // 第几次重连
                int order = (MAX_RETRY - retry) + 1;
                // 本次重连间隔
                int delay = 1 << order;
                System.err.println(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + "连接失败，第" + order + "次重连。。。");
                bootstrap.config().group().schedule(() ->
                        connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);

            }
        });
    }
}
