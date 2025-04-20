package com.charlie.threadx;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.concurrent.TimeUnit;

/**
 * @Author: charlie
 * @CreateTime: Created in 2025/3/11 9:10
 * @Description: ThreadPoolH
 */
@Slf4j(topic = "c.ThreadPoolH")
public class ThreadPoolH {

    private BlockingQueueH<Runnable> taskQueue;

    private final HashSet<Thread> workers = new HashSet<>();

    private int corePoolSize;

    // 线程从阻塞队列中获取任务的超时时间
    private long timeout;

    private TimeUnit unit;

    private RejectPolicyH<Runnable> rejectPolicy;

    public ThreadPoolH(int corePoolSize, long timeout, TimeUnit unit, int capacity, RejectPolicyH<Runnable> rejectPolicy) {
        this.corePoolSize = corePoolSize;
        this.timeout = timeout;
        this.unit = unit;
        this.taskQueue = new BlockingQueueH<>(capacity);
        this.rejectPolicy = rejectPolicy;
    }

    public void execute(Runnable task) {
        synchronized (this) {
            // 线程数 < 核心线程数
            if (workers.size() < corePoolSize) {
                Worker worker = new Worker(task);
                log.debug("新增 worker{}, {}", worker, task);
                workers.add(worker);
                worker.start();
            } else {
                // 1. 一直等到队列有空位
                // taskQueue.put(task);
                // 2. 带超时等待
                // taskQueue.offer(task, timeout, unit);

                // 策略模式
                taskQueue.tryPut(task, rejectPolicy);
            }
        }
    }

    class Worker extends Thread {
        private Runnable task;

        public Worker(Runnable task) {
            this.task = task;
        }

        @Override
        public void run() {
            // while (task != null || (task = taskQueue.take()) != null) {
            while (task != null || (task = taskQueue.poll(timeout, unit)) != null) {
                try {
                    log.debug("正在执行...{}", task);
                    task.run();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    task = null;
                }
            }

            // 线程执行完毕后，从 workers 中移除
            synchronized (workers) {
                log.debug("worker 被移除{}", this);
                workers.remove(this);
            }
        }
    }
}
