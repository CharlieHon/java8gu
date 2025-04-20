package com.charlie.pattern;

import java.io.Serializable;

/**
 * @Author: charlie
 * @CreateTime: Created in 2025/2/12 15:46
 * @Description: 静态内部类
 */
public class Singleton5 implements Serializable {
    private Singleton5() {
        if (Holder.INSTANCE != null) {
            throw new RuntimeException("单例对象不能重复创建");
        }
        System.out.println("private Singleton5()");
    }

    private static class Holder {
        static Singleton5 INSTANCE = new Singleton5();
    }

    public static Singleton5 getInstance() {
        return Holder.INSTANCE;
    }

    public static void otherMethod() {
        System.out.println("otherMethod");
    }

    public Object readResolve() {
        return Holder.INSTANCE;
    }
}
