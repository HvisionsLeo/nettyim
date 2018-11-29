package com.leo.bean;

import lombok.Data;

/**
 * @Description: 数据包
 * @Author: Leo
 * @Date: 2018-11-29 下午 5:24
 */
@Data
public abstract class Packet {

    /**
     * 版本协议
     */
    private Byte version = 1;

    /**
     * 指令
     * @return
     */
    public abstract Byte getCommand();
}
