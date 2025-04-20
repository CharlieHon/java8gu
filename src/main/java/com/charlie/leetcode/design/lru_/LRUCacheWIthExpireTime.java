package com.charlie.leetcode.design.lru_;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: charlie
 * @CreateTime: Created in 2025/3/23 21:28
 * @Description: LRUCacheWIthExpireTime
 */
class LRUCacheWIthExpireTime {

    static class Node {
        int key, value;
        Node prev, next;
        long expireTime;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }

        Node(int key, int value, long expireTime) {
            this.key = key;
            this.value = value;
            this.expireTime = expireTime;
        }
    }

    // LRU容量
    private final int capacity;
    // key -> Node 映射
    private final Map<Integer, Node> keyToNode = new HashMap<>();
    // 虚拟头结点
    private final Node dummy = new Node(0, 0);

    public LRUCacheWIthExpireTime(int capacity) {
        this.capacity = capacity;
        // 虚拟头结点的前后指针初始化都指向它自己
        dummy.prev = dummy;
        dummy.next = dummy;
    }

    public int get(int key) {
        Node node = getNode(key);
        if (node == null || isExpired(node)) {
            return -1;
        }
        return node.value;
    }

    public void put(int key, int value, long timeout, TimeUnit unit) {
        Node node = getNode(key);
        if (node != null) {
            node.value = value;
            node.expireTime = System.currentTimeMillis() + unit.toMillis(timeout);
            return;
        }
        node = new Node(key, value, System.currentTimeMillis() + unit.toMillis(timeout));
        keyToNode.put(key, node);
        putFirst(node);
        if (keyToNode.size() > capacity) {
            Node lastNode = dummy.prev;
            remove(lastNode);
            keyToNode.remove(lastNode.key);
        }
    }

    private Node getNode(int key) {
        Node node = keyToNode.get(key);
        // 节点不存在
        if (node == null) {
            return null;
        }
        // 节点已经过期
        if (isExpired(node)) {
            remove(node);
            keyToNode.remove(node.key);
            return null;
        }
        // 节点存在，移到链表头部
        remove(node);
        putFirst(node);
        return node;
    }

    // 判断节点 x 是否过期
    private boolean isExpired(Node x) {
        return System.currentTimeMillis() > x.expireTime;
    }

    // 删除节点
    private void remove(Node x) {
        x.prev.next = x.next;
        x.next.prev = x.prev;
    }

    // 将节点放入链表头部
    private void putFirst(Node x) {
        x.prev = dummy;
        x.next = dummy.next;
        x.prev.next = x;
        x.next.prev = x;
    }
}
