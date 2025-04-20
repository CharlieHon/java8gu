package com.charlie.proxyx.jdkx;

/**
 * @Author: charlie
 * @CreateTime: Created in 2025/3/7 9:16
 * @Description: Main
 */
public class TestMain {
    public static void main(String[] args) {
        SmsServiceImpl target = new SmsServiceImpl();
        SmsService proxy = (SmsService) JdkProxyFactory.getProxy(target);
        proxy.send("hello");
    }
}
