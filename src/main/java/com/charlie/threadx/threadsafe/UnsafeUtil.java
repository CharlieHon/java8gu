package com.charlie.threadx.threadsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @Author: charlie
 * @CreateTime: Created in 2025/3/8 15:38
 * @Description: UnsafeUtil
 */
public class UnsafeUtil {

    public static Unsafe getUnsafe() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return ((Unsafe) field.get(null));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
