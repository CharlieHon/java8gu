package com.charlie.leetcode.design.lru_;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: charlie
 * @CreateTime: Created in 2025/3/23 20:21
 * @Description: LRU（最近最少使用）缓存：基于 LinkedHashMap
 */
class LRUCacheByLinkedHashMap {
    private final Map<Integer, Integer> cache = new LinkedHashMap<>();
    private final int capacity;

    public LRUCacheByLinkedHashMap(int capacity) {
        this.capacity = capacity;
    }

    public int get(int key) {
        Integer value = cache.remove(key);
        if (value == null) {
            return -1;
        }
        cache.put(key, value);
        return value;
    }

    public void put(int key, int value) {
        if (cache.remove(key) != null) {
            cache.put(key, value);
            return;
        }
        if (cache.size() == capacity) {
            Integer oldestKey = cache.keySet().iterator().next();
            cache.remove(oldestKey);
        }
        cache.put(key, value);
    }
}
