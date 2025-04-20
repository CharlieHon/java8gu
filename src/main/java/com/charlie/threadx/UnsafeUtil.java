package com.charlie.threadx;


import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @Author: charlie
 * @CreateTime: Created in 2025/3/1 18:47
 * @Description: UnsafeUtil
 */
public class UnsafeUtil {

    private UnsafeUtil() {

    }

    public static Unsafe getUnsafe() {
        Unsafe unsafe = null;
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return unsafe;
    }

}
