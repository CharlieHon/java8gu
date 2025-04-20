package com.charlie.leetcode.design.lru_;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: charlie
 * @CreateTime: Created in 2025/3/23 20:49
 * @Description: LRU缓存
 */
public class LRUCache {

    static class Node {
        int key, value;
        Node prev, next;

        Node(int k, int v) {
            this.key = k;
            this.value = v;
        }
    }

    // 虚拟头结点
    private final Node dummy = new Node(-1, -1);
    // 缓存容量
    private final int capacity;
    // key -> Node 的映射
    private final Map<Integer, Node> keyToNode = new HashMap<>();

    public LRUCache(int capacity) {
        this.capacity = capacity;
        // 🔺初始化时，虚拟头结点的前后指针都指向它自己
        dummy.next = dummy;
        dummy.prev = dummy;
    }

    public int get(int key) {
        Node node = getNode(key);
        return node == null ? -1 : node.value;
    }

    public void put(int key, int value) {
        Node node = getNode(key);
        if (node != null) {
            node.value = value; // 更新 value（在获取时已经将其放入头部）
            return;
        }
        // 插入
        node = new Node(key, value);
        // 🔺：添加到keyToNode中
        keyToNode.put(key, node);
        // 添加节点到缓存中
        putFirst(node);
        if (keyToNode.size() > capacity) {
            // 移除链表尾部元素
            Node lastNode = dummy.prev;
            // 🔺：从keyToNode中移除
            keyToNode.remove(lastNode.key);
            // 从缓存中移除
            remove(lastNode);
        }
    }

    // 获取 key 对应的节点，同时把该节点移动到链表头
    private Node getNode(int key) {
        Node node = keyToNode.get(key);
        // 没有该 key
        if (node == null) {
            return null;
        }
        // 删除该 node
        remove(node);
        // 放到链表头部
        putFirst(node);
        return node;
    }

    // 删除一个节点
    private void remove(Node x) {
        x.prev.next = x.next;
        x.next.prev = x.prev;
        x.prev = null;
        x.next = null;
    }

    // 在链表头结点添加一个元素
    private void putFirst(Node x) {
        x.prev = dummy;
        x.next = dummy.next;
        x.prev.next = x;
        x.next.prev = x;
    }
}
