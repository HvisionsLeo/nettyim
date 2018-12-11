package com.leo.util;

import com.leo.bean.Attributes;
import com.leo.session.Session;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description:
 * @Author: Leo
 * @Date: 2018-12-07 下午 12:02
 */
public class SessionUtil {

    private static Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();

    private static Map<String, ChannelGroup> channelGroupMap = new ConcurrentHashMap<>();

    public static void bindSession(Session session, Channel channel) {
        userIdChannelMap.put(session.getUserId(), channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    public static void unBindSession(Channel channel) {
        if (hasLogin(channel)) {
            userIdChannelMap.remove(getSession(channel).getUserId());
            channel.attr(Attributes.SESSION).set(null);
        }
    }

    public static boolean hasLogin(Channel channel) {
        return channel.hasAttr(Attributes.SESSION);
    }

    public static Session getSession(Channel channel) {
        return channel.attr(Attributes.SESSION).get();
    }

    public static Channel getChannel(String userId) {
        return userIdChannelMap.get(userId);
    }

    public static void bindChannelGroup(String groupId, ChannelGroup channels) {
        channelGroupMap.put(groupId, channels);
    }

    public static ChannelGroup getChannelGroup(String groupId) {
        return channelGroupMap.get(groupId);
    }
}
