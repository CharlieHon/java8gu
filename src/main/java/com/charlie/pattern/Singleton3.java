package com.charlie.pattern;

/**
 * @Author: charlie
 * @CreateTime: Created in 2025/2/12 15:11
 * @Description: 单例模式：懒汉式
 */
public class Singleton3 {

    private static Singleton3 INSTANCE;

    private Singleton3() {
        System.out.println("private Singleton3()");
    }

    // Singleton3.class
    public static synchronized Singleton3 getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Singleton3();
        }
        return INSTANCE;
    }

    public static void otherMethod() {
        System.out.println("otherMethod");
    }
}
