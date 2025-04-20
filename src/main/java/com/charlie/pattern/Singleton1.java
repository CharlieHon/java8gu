package com.charlie.pattern;

import java.io.Serializable;

/**
 * @Author: charlie
 * @CreateTime: Created in 2025/2/12 14:17
 * @Description: 单例模式：饿汉式
 */
public class Singleton1 implements Serializable {
    // 静态成员变量
    private static final Singleton1 INSTANCE = new Singleton1();

    // 构造私有化
    private Singleton1() {
        // 防止反射破坏单例
        if (INSTANCE != null) {
            throw new RuntimeException("单例对象不能重复创建");
        }

        System.out.println("private Singleton1");
    }

    // 提供获取单例的方法
    public static Singleton1 getInstance() {
        return INSTANCE;
    }

    public static void otherMethod() {
        System.out.println("otherMethod");
    }

    // 防止反序列化破坏单例
    public Object readResolve() {
        return INSTANCE;
    }
}
