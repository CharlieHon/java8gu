package com.charlie.pattern;

import sun.misc.Unsafe;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * @Author: charlie
 * @CreateTime: Created in 2025/2/12 14:19
 * @Description: 单例模式测试：Java虚拟机中一个类只有一个实例
 *
 * 1. 单例模式常见五种实现方式
 * 2. JDK中的单例模式
 *
 *
 * 饿汉式（包括枚举类）单例对象，是在代码块中赋值的，jvm会保证其线程安全
 */
public class TestSingleton {
    public static void main(String[] args) throws Exception {
        Singleton5.otherMethod();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println(Singleton5.getInstance());
        System.out.println(Singleton5.getInstance());
        
        // 反射破坏单例
        // reflection(Singleton5.class);

        // 反序列化破坏单例
        deserializable(Singleton5.getInstance());

        // Unsafe 破坏单例
        // unsafe(Singleton3.class);
    }

    private static void unsafe(Class<?> clazz) throws Exception {
        Field field = Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        Unsafe unsafe = (Unsafe) field.get(null);

        // allocateInstance 绕过对象的构造函数，直接分配内存并返回一个对象实例
        Object o = unsafe.allocateInstance(clazz);
        System.out.println("Unsafe创建实例：" + o);
    }

    // 反序列化破坏单例
    private static void deserializable(Object instance) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(instance);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
        System.out.println("反序列化创建实例：" + ois.readObject());
    }

    // 通过反射破坏单例
    private static void reflection(Class<?> clazz) throws Exception {
        Constructor<?> constructor = clazz.getDeclaredConstructor();

        // 枚举类没有无参构造方法，其构造方法的参数是 String name, int ordinal
        // Cannot reflectively create enum objects
        // Constructor<?> constructor = clazz.getDeclaredConstructor(String.class, int.class);

        constructor.setAccessible(true);
        System.out.println(constructor.newInstance());

        // System.out.println(constructor.newInstance("OTHER", 1));
    }


}
