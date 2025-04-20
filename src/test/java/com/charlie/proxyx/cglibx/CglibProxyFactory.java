package com.charlie.proxyx.cglibx;

import net.sf.cglib.proxy.Enhancer;

/**
 * @Author: charlie
 * @CreateTime: Created in 2025/3/7 9:22
 * @Description: CglibProxyFactory
 */
public class CglibProxyFactory {
    public static Object getProxy(Class<?> clazz) {
        Enhancer enhancer = new Enhancer();
        // 设置父类
        enhancer.setSuperclass(clazz);
        // 设置类加器
        enhancer.setClassLoader(clazz.getClassLoader());
        // 设置回调函数
        enhancer.setCallback(new MyMethodInterceptor());
        return enhancer.create();
    }
}
