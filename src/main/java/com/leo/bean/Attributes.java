package com.leo.bean;

import io.netty.util.AttributeKey;

/**
 * @Description:
 * @Author: Leo
 * @Date: 2018-11-30 下午 4:11
 */
public interface Attributes {

    // 登录成功标志位
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
}
