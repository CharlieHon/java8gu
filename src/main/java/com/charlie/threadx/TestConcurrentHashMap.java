package com.charlie.threadx;

import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: charlie
 * @CreateTime: Created in 2025/3/1 19:27
 * @Description: TestConcurrentHashMap
 */
public class TestConcurrentHashMap {

    public static void main(String[] args) {
        Map<Integer, Integer> map = new ConcurrentHashMap<>();
        Hashtable<Integer, Integer> table = new Hashtable<>();
    }

}
