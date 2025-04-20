package com.charlie.proxyx.cglibx;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Author: charlie
 * @CreateTime: Created in 2025/3/5 9:55
 * @Description: 自定义 MethodInterceptor 方法拦截器
 */
public class DebugMethodInterceptor implements MethodInterceptor {

    /**
     * @param o           被代理的对象（需要增强的对象）
     * @param method      被拦截的方法（需要增强的方法）
     * @param objects     方法入参
     * @param methodProxy 用于调用原始方法
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        // 调用方法之前，添加自己的操作
        System.out.println("before method: " + method.getName());
        Object object = methodProxy.invokeSuper(o, objects);
        // Object object = method.invoke(o, objects);
        // 调用方法之后，添加自己的操作
        System.out.println("after method: " + method.getName());
        return object;
    }
}
