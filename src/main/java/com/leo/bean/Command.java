package com.leo.bean;

/**
 * @Description: 指令包
 * @Author: Leo
 * @Date: 2018-11-29 下午 5:26
 */
public interface Command {

    Byte LOGIN_REQUEST = 1;

    Byte LOGIN_RESPONSE = 2;

    Byte MESSAGE_REQUEST = 3;

    Byte MESSAGE_RESPONSE = 4;

}
