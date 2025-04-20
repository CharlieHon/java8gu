package com.charlie.proxyx.jdkx;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Author: charlie
 * @CreateTime: Created in 2025/3/7 9:13
 * @Description: MyInvocationHandler
 */
public class MyInvocationHandler implements InvocationHandler {

    private final Object target;

    public MyInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before method: " + method.getName());
        Object result = method.invoke(target, args);
        System.out.println("after method: " + method.getName());
        return result;
    }
}
