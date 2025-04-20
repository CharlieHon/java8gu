package com.charlie.lambdax;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @Author: charlie
 * @CreateTime: Created in 2025/3/22 16:04
 * @Description: Main
 */
public class Main {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("hello");
        list.add("world");
        list.add("hi");
        list.add("a");
        list.sort(Comparator.comparing(String::length));
        list.forEach(System.out::println);

    }
}
