package com.leo.serializer;

import com.alibaba.fastjson.JSON;

/**
 * @Description: json序列化
 * @Author: Leo
 * @Date: 2018-11-29 下午 5:39
 */
public class JSONSerializer implements Serializer {

    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serializer(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}
