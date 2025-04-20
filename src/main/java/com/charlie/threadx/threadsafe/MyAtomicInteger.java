package com.charlie.threadx.threadsafe;

import sun.misc.Unsafe;

/**
 * @Author: charlie
 * @CreateTime: Created in 2025/3/8 13:31
 * @Description: 原子整形
 */
public class MyAtomicInteger {

    // 提供底层的CAS操作
    private static final Unsafe U;

    // 获取value字段的偏移量
    private static final long valueOffset;

    static {
        U = UnsafeUtil.getUnsafe();
        try {
            valueOffset = U.objectFieldOffset(MyAtomicInteger.class.getDeclaredField("value"));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    // CAS需要与volatile配合使用，保证变量的可见性
    private volatile int value;

    public MyAtomicInteger() {

    }

    public MyAtomicInteger(int value) {
        this.value = value;
    }

    public int incrementAndGet() {
        // int o, n;
        // for (; ; ) {
        //     o = value;
        //     n = o + 1;
        //     if (U.compareAndSwapInt(this, valueOffset, o, n)) {
        //         break;
        //     }
        // }
        // return n;
        return U.getAndAddInt(this, valueOffset, 1) + 1;
    }

    public int getAndIncrement() {
        // int o, n;
        // for (; ; ) {
        //     o = value;
        //     n = o + 1;
        //     if (U.compareAndSwapInt(this, valueOffset, o, n)) {
        //         break;
        //     }
        // }
        // return o;
        return U.getAndAddInt(this, valueOffset, 1);
    }

    public int intValue() {
        return value;
    }

    public static void main(String[] args) throws InterruptedException {
        MyAtomicInteger i = new MyAtomicInteger();
        Thread t1 = new Thread(() -> {
            for (int j = 0; j < 10000; j++) {
                i.incrementAndGet();
            }
        });
        t1.start();
        t1.join();
        System.out.println(i.intValue());
    }
}
