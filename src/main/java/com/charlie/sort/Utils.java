package com.charlie.sort;

import java.util.Random;

/**
 * @Author: charlie
 * @CreateTime: Created in 2025/2/11 15:47
 * @Description: Utils
 */
public class Utils {
    public static void swap(int[] array, int i, int j) {
        int t = array[i];
        array[i] = array[j];
        array[j] = t;
    }

    public static void shuffle(int[] array) {
        Random rnd = new Random();
        int size = array.length;
        for (int i = size; i > 1; i--) {
            swap(array, i - 1, rnd.nextInt(i));
        }
    }

    public static int[] randomArray(int n) {
        int lastVal = 1;
        Random r = new Random();
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            int v = lastVal + Math.max(r.nextInt(10), 1);
            array[i] = v;
            lastVal = v;
        }
        shuffle(array);
        return array;
    }

    // 偶数数组：array[i] = i * 2
    public static int[] evenArray(int n) {
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = i * 2;
        }
        return array;
    }

    // array[i] = i * 16
    public static int[] sixteenArray(int n) {
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = i * 16;
        }
        return array;
    }

    // 生成低位相同的元素
    public static int[] lowSameArray(int n) {
        int[] array = new int[n];
        Random r = new Random();
        for (int i = 0; i < n; i++) {
            //                      01111111 11111111 00000000 00000010
            array[i] = r.nextInt() & 0x7FFF0002;
        }
        return array;
    }
}
