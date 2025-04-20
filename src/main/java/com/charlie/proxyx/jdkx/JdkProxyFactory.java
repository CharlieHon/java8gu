package com.charlie.proxyx.jdkx;

import java.lang.reflect.Proxy;

/**
 * @Author: charlie
 * @CreateTime: Created in 2025/3/5 9:47
 * @Description: 获取代理对象的工厂类
 */
public class JdkProxyFactory {

    public static Object getProxy(Object target) {
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(), // 目标类的类加载器
                target.getClass().getInterfaces(),  // 代理需要实现的几口，可指定多个
                new DebugInvocationHandler(target)  // 代理对象对应的自定义 InvocationHandler
        );
    }

}
