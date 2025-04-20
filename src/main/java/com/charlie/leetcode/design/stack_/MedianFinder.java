package com.charlie.leetcode.design.stack_;

import java.util.PriorityQueue;

/**
 * @Author: charlie
 * @CreateTime: Created in 2025/3/23 22:29
 * @Description: MedianFinder
 */
public class MedianFinder {

    // 4, 3, 2, 1
    private final PriorityQueue<Integer> left = new PriorityQueue<>((a, b) -> b - a);
    private final PriorityQueue<Integer> right = new PriorityQueue<>();

    public MedianFinder() {
    }

    public void addNum(int num) {
        if (left.size() == right.size()) {
            right.offer(num);
            left.offer(right.poll());
        } else {
            left.offer(num);
            right.offer(left.poll());
        }
    }

    public double findMedian() {
        if (left.size() > right.size()) {
            return left.peek();
        }
        return (left.peek() + right.peek()) / 2.0;
    }
}
