package com.charlie.list;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author: charlie
 * @CreateTime: Created in 2025/2/10 11:32
 * @Description: ArrayList&LinkedList
 * ArrayList
 *  1. 基于数组，需要连续内存
 *  2. 随机访问快（指根据下标访问）
 *  3. 尾部插入、删除性能可以，其它部分插入、删除都会移动数据，因此性能会低
 *  4. 可以利用 cpu 缓存，局部性原理
 * LinkedList
 *  1. 基于双向链表，无需连续内存
 *  2. 随机访问慢（要沿着链表遍历）
 *  3. 头尾插入删除性能高
 *  4. 占用内存多
 *
 *  局部性原理：读取一个数据时，其相邻位置的数据很大概率也被要访问。
 *  数组访问时能够很好的利用CPU局部性原理，将数组中相邻位置的元素放在CPU的缓存中，从而提高访问速度。
 */
public class ArrayListVsLinkedList {

    public static void main(String[] args) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();
    }
}
