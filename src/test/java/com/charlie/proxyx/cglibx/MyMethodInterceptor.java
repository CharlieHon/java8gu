package com.charlie.proxyx.cglibx;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Author: charlie
 * @CreateTime: Created in 2025/3/7 9:21
 * @Description: MyMethodInterceptor
 */
public class MyMethodInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("before method:" + method.getName());
        Object result = methodProxy.invokeSuper(o, objects);
        System.out.println("after method:" + method.getName());
        return result;
    }
}
