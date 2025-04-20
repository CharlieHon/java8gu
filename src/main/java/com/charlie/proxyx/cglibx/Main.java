package com.charlie.proxyx.cglibx;

/**
 * @Author: charlie
 * @CreateTime: Created in 2025/3/5 9:59
 * @Description: CGLIB动态代理
 *
 * 1. CGLIB可以代理未实现任何接口的类
 * 2. CGLIB代理是通过生成一个被代理类的字类来拦截被代理类的方法调用，因此，不能代理声明为final类型的类和方法
 */
public class Main {

    public static void main(String[] args) {
        AliService proxy = (AliService) CglibProxyFactory.getProxy(AliService.class);
        proxy.send("hello");
    }
}
