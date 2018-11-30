package com.leo.util;


import com.leo.bean.Attributes;
import io.netty.channel.Channel;
import io.netty.util.Attribute;

/**
 * @Description: 登录工具
 * @Author: Leo
 * @Date: 2018-11-30 下午 4:10
 */
public class LoginUtil {

    public static void markAsLogin(Channel channel) {
        channel.attr(Attributes.LOGIN).set(true);
    }

    public static boolean hasLogin(Channel channel) {
        Attribute<Boolean> loginArr = channel.attr(Attributes.LOGIN);
        return loginArr.get() != null;
    }
}
