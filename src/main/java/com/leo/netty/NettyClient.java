package com.leo.netty;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

/**
 * @Description: netty客户端
 * @Author: Leo
 * @Date: 2018-11-28 下午 12:13
 */
public class NettyClient {

    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        channel.pipeline().addLast(new StringEncoder());
                    }
                });
        Channel channel = bootstrap.connect("127.0.0.1", 8888).channel();
        while (true) {
            String s = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + ":Hello World!";
            channel.writeAndFlush(s);
            System.out.println("写入：" + s);
            Thread.sleep(2000);
        }

    }
}
