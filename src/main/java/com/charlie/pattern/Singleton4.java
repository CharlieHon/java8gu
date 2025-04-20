package com.charlie.pattern;

/**
 * @Author: charlie
 * @CreateTime: Created in 2025/2/12 15:22
 * @Description: 懒汉式：双重检验锁（DCL）
 *
 * 反编译二进制字节码 javap -c -v -p Singleton4.class
 *
 * DCL版，INSTANCE变量必须使用 volatile 关键字修饰，否则可能由于置零重排导致，在多线程的情况下
 * 拿到没有经过完整构造的半成品对象
 */
public class Singleton4 {

    // volatile解决共享变量的 可见性、有序性
    private static volatile Singleton4 INSTANCE;

    private Singleton4() {
        System.out.println("private  Singleton4()");
    }

    public static Singleton4 getInstance() {
        if (INSTANCE == null) {
            synchronized (Singleton4.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Singleton4();
                }
            }
        }
        return INSTANCE;
    }

    public static void otherMethod() {
        System.out.println("otherMethod");
    }
}
