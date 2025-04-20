package com.charlie.proxyx.jdkx;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Author: charlie
 * @CreateTime: Created in 2025/3/5 9:44
 * @Description: JDK动态代理类
 */
public class DebugInvocationHandler implements InvocationHandler {

    /**
     * 代理类中的真实对象
     */
    private final Object target;

    public DebugInvocationHandler(Object target) {
        this.target = target;
    }

    // 当动态代理对象调用原生方法时，最终实际上调用的是 invoke() 方法
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 调用方法之前，可以添加自己的操作
        System.out.println("before method " + method.getName());
        Object result = method.invoke(target, args);
        // 调用方法之后，同样可以添加自己的操作
        System.out.println("after method " + method.getName());
        return result;
    }
}
