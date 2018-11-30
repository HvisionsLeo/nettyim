package com.leo.serializer;

import com.leo.serializer.impl.JSONSerializer;

/**
 * @Description: 序列化接口
 * @Author: Leo
 * @Date: 2018-11-29 下午 5:33
 */
public interface Serializer {

    Serializer DEFAULT = new JSONSerializer();

    /**
     * 序列化算法
     *
     * @return 数据算法
     */
    byte getSerializerAlgorithm();

    /**
     * java对象转换为二进制数组
     *
     * @param object java对象
     * @return 二进制数组
     */
    byte[] serializer(Object object);

    /**
     * 二进制数组转换为java对象
     *
     * @param clazz java类
     * @param bytes 二进制数组
     * @param <T> 要转换的java类型
     * @return java对象
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);
}
