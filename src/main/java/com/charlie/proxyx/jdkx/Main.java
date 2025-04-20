package com.charlie.proxyx.jdkx;

/**
 * @Author: charlie
 * @CreateTime: Created in 2025/3/5 9:48
 * @Description: JDK动态代理
 *
 * JDK动态代理只能代理实现了接口的类
 */
public class Main {
    public static void main(String[] args) {
        SmsService proxy = (SmsService) JdkProxyFactory.getProxy(new SmsServiceImpl());
        proxy.send("hello");
    }
}
