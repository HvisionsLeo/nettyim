package com.leo.bean.response;

import com.leo.bean.Command;
import com.leo.bean.Packet;

/**
 * @Description: 心跳响应数据包
 * @Author: Leo
 * @Date: 2018-12-12 下午 3:01
 */
public class HeartBeatResponsePacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.HEART_BEAT_RESPONSE;
    }
}
