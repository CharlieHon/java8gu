package com.charlie.proxyx.jdkx;

import java.lang.reflect.Proxy;

/**
 * @Author: charlie
 * @CreateTime: Created in 2025/3/7 9:14
 * @Description: JdkProxyFactory
 */
public class JdkProxyFactory {
    public static Object getProxy(Object target) {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new MyInvocationHandler(target));
    }
}
