package com.charlie.threadx;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: charlie
 * @CreateTime: Created in 2025/3/11 9:03
 * @Description: BlockingQueueH
 */
@Slf4j(topic = "c.BlockingQueueH")
public class BlockingQueueH<T> {

    private Deque<T> queue = new ArrayDeque<>();

    private ReentrantLock lock = new ReentrantLock();

    private Condition emptyWaitSet = lock.newCondition();

    private Condition fullWaitSet = lock.newCondition();

    private int capacity;

    public BlockingQueueH(int capacity) {
        this.capacity = capacity;
    }

    public T take() {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                try {
                    emptyWaitSet.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            T task = queue.removeFirst();
            log.debug("从队列获取任务 {}", task);
            fullWaitSet.signalAll();
            return task;
        } finally {
            lock.unlock();
        }
    }

    // 带超时等待的获取
    public T poll(long timeout, TimeUnit unit) {
        lock.lock();
        try {
            long nanos = unit.toNanos(timeout);
            while (queue.isEmpty()) {
                // 超时
                if (nanos <= 0) {
                    return null;
                }
                try {
                    nanos = emptyWaitSet.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            T t = queue.removeFirst();
            fullWaitSet.signalAll();
            return t;
        } finally {
            lock.unlock();
        }
    }

    public void put(T task) {
        lock.lock();
        try {
            while (queue.size() == capacity) {
                try {
                    log.debug("等待加入任务队列 {}", task);
                    fullWaitSet.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            log.debug("加入任务队列 {}", task);
            queue.addLast(task);
            emptyWaitSet.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public boolean offer(T task, long timeout, TimeUnit unit) {
        lock.lock();
        try {
            long nanos = unit.toNanos(timeout);
            while (queue.size() == capacity) {
                if (nanos <= 0) {
                    log.debug("任务丢弃 {}", task);
                    return false;
                }
                try {
                    log.debug("等待加入任务队列 {}", task);
                    nanos = fullWaitSet.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            log.debug("加入任务队列{}", task);
            queue.addLast(task);
            emptyWaitSet.signalAll();
            return true;
        } finally {
            lock.unlock();
        }
    }

    public void tryPut(T task, RejectPolicyH<T> policy) {
        lock.lock();
        try {
            // 队列已经满了
            if (queue.size() == capacity) {
                log.debug("执行拒绝策略 {}", task);
                policy.reject(task, this);
            } else {
                log.debug("加入任务队列 {}", task);
                queue.addLast(task);
                emptyWaitSet.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }

    public int size() {
        lock.lock();
        try {
            return queue.size();
        } finally {
            lock.unlock();
        }
    }
}
