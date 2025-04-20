package com.charlie.list;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Author: charlie
 * @CreateTime: Created in 2025/2/10 11:00
 * @Description: Iterator FailFastVsFailSafe
 * fail-fast 一旦发现遍历的同时其它人来修改，则立刻抛异常
 * fail-safe 发现遍历的同时其它人来修改，应当能有应对策略，例如牺牲一致性来让整个遍历运行完成
 */
public class FailFastVsFailSafe {

    // 当在遍历集合时修改，快速失败，抛出ConcurrentModificationException异常
    private static void failFast() {
        List<Student> list = new ArrayList<>();
        list.add(new Student("A"));
        list.add(new Student("B"));
        list.add(new Student("C"));
        list.add(new Student("D"));
        for (Student student : list) {
            System.out.println(student);
        }
        System.out.println(list);
    }

    // CopyOnWriteArrayList 是 fail-safe 的典型代表，遍历的同时可以修改，原理是**读写分离**
    private static void failSafe() {
        CopyOnWriteArrayList<Student> list = new CopyOnWriteArrayList<>();
        list.add(new Student("A"));
        list.add(new Student("B"));
        list.add(new Student("C"));
        list.add(new Student("D"));
        // CopyOnWriteArrayList创建迭代器时辅助此时集合中的数组，整个遍历过程中会是在遍历该数组
        // 所以集合可以进行修改，不影响遍历结果
        for (Student student : list) {
            System.out.println(student);
        }
        System.out.println(list);
    }

    // Vector 是 fail-fast 的
    private static void testVector() {
        Vector<Student> vector = new Vector<>();
        vector.add(new Student("A"));
        vector.add(new Student("B"));
        vector.add(new Student("C"));
        vector.add(new Student("D"));
        for (Student student : vector) {
            System.out.println(student);
        }
        System.out.println(vector);
    }

    public static void main(String[] args) {
        // failFast();
        // failSafe();
        testVector();
    }

    static class Student {
        private final String name;

        public Student(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }
}
