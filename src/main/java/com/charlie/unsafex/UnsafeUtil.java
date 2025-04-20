package com.charlie.unsafex;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @Author: charlie
 * @CreateTime: Created in 2025/3/7 19:03
 * @Description: UnsafeUtil
 */
public class UnsafeUtil {
    public static Unsafe getUnsafe() {
        Field field;
        try {
            field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return ((Unsafe) field.get(null));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
