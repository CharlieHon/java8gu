package com.charlie.list;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: charlie
 * @CreateTime: Created in 2025/2/10 10:29
 * @Description: ArrayList扩容机制
 */
public class TestArrayList {

    public static void main(String[] args) {
        // [0, 10, 15, 22, 33, 49, 73, 109, 163, 244, 366]
        System.out.println(arrayListGrowRule(10));

        // 1. 使用无参构造器，数组长度为0
        ArrayList<Integer> list = new ArrayList<>();
        System.out.println(length(list));    // 0
        // 2. 第一次添加元素时，**容量**扩容为10
        list.add(2);
        System.out.println(length(list));    // 大小为1，容量为10
        // addAll扩容
        list.addAll(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        // 使用addAll扩容时，容量为Math.max(下一次扩容大小，添加元素后数组实际大小)
        System.out.println(length(list));   // 15

        // 3. ArrayList(int initialCapacity) 会使用指定容量的数组
        List<Integer> list2 = new ArrayList<>(11);
        System.out.println(length(list2));  // 11

        // 4. public ArrayList(Collection<? extends E> c) 会使用 c 的大小作为数组容量
        List<Integer> list3 = new ArrayList<>(list);
        System.out.println(length(list3));  // 11， list实际大小为11，数组容量为15
    }

    // ArrayList扩容n次时elementData的容量大小
    private static List<Integer> arrayListGrowRule(int n) {
        List<Integer> list = new ArrayList<>();
        int init = 0;
        // 使用无参构造器，初始化数组大小为0
        list.add(init);
        if (n >= 1) {
            // 添加第一个元素时，数组扩容为10
            init = 10;
            list.add(init);
        }
        for (int i = 1; i < n; i++) {
            // 以后继续添加元素，当元素个数超过容量时，数组大小扩容为原来的1.5倍
            init += init >> 1;
            list.add(init);
        }
        return list;
    }

    // 通过反射获取ArrayList中elementData数组的大小
    private static int length(List<Integer> list) {
        try {
            Field field = ArrayList.class.getDeclaredField("elementData");
            field.setAccessible(true);
            return ((Object[]) field.get(list)).length;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return 0;
        }
    }

}
