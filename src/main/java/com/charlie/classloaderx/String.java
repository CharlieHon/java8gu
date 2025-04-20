package com.charlie.classloaderx;

/**
 * @Author: charlie
 * @CreateTime: Created in 2025/3/14 10:29
 * @Description: 类加载
 */
public class String {

    private int x = 10;

    public static void main(java.lang.String[] args) {
        System.out.println("hello world");
        // jdk.internal.loader.ClassLoaders$AppClassLoader@63947c6b
        System.out.println(String.class.getClassLoader());
        String s = new String();
        System.out.println(s.x);

        java.lang.String s2 = "hello";
        System.out.println(s2);
    }
}
