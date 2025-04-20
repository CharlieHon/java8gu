package com.charlie.pattern;

/**
 * @Author: charlie
 * @CreateTime: Created in 2025/2/12 14:59
 * @Description: 单例模式：枚举类-饿汉实现
 */
public enum Singleton2 {
    INSTANCE;

    // 枚举类的构造参数默认是 private
    private Singleton2() {
        System.out.println("private Singleton2()");
    }

    public static Singleton2 getInstance() {
        return INSTANCE;
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode());
    }

    public static void otherMethod() {
        System.out.println("otherMethod");
    }

}
