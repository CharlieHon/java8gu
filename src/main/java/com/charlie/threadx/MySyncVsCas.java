package com.charlie.threadx;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @Author: charlie
 * @CreateTime: Created in 2025/3/1 18:53
 * @Description: MySyncVsCas
 */
public class MySyncVsCas {

    public static void main(String[] args) throws NoSuchFieldException {
        Unsafe unsafe = UnsafeUtil.getUnsafe();
        SyncVsCas.Account account = new SyncVsCas.Account();
        Field field = account.getClass().getDeclaredField("balance");
        long BALANCE = unsafe.objectFieldOffset(field);
        int o = account.balance;
        int n = o + 5;
        boolean success = unsafe.compareAndSwapInt(account, BALANCE, o, n);
        System.out.println(success);
        System.out.println(account.balance);
    }

}
