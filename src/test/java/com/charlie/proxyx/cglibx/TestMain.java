package com.charlie.proxyx.cglibx;

/**
 * @Author: charlie
 * @CreateTime: Created in 2025/3/7 9:21
 * @Description: TestMain
 */
public class TestMain {
    public static void main(String[] args) {
        AliService proxy = (AliService) CglibProxyFactory.getProxy(AliService.class);
        proxy.send("hi");
    }
}
