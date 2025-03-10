package com.charlie.threadpoolx;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.concurrent.TimeUnit;

/**
 * @Author: charlie
 * @CreateTime: Created in 2025/3/10 20:59
 * @Description: 自定义线程池
 */
@Slf4j(topic = "c.ThreadPoolx")
public class ThreadPoolx {
    // 任务队列
    private BlockingQueuex<Runnable> taskQueue;

    // 线程集合
    private final HashSet<Worker> workers = new HashSet();

    // 核心线程数
    private int corePoolSize;

    // 线程获取任务的超时时间
    private long timeout;

    // 时间单位
    private TimeUnit unit;

    // 拒绝策略
    private RejectPolicy<Runnable> rejectPolicy;

    public ThreadPoolx(int corePoolSize, long keepAliveTime, TimeUnit unit, int queueCapacity, RejectPolicy<Runnable> rejectPolicy) {
        this.corePoolSize = corePoolSize;
        this.timeout = keepAliveTime;
        this.unit = unit;
        this.taskQueue = new BlockingQueuex<>(queueCapacity);
        this.rejectPolicy = rejectPolicy;
    }

    public void execute(Runnable task) {
        synchronized (this) {
            // 当任务数 < corePoolSize 时，直接交给 worker 对象执行
            if (workers.size() < corePoolSize) {
                // 创建新的 worker 对象
                Worker worker = new Worker(task);
                log.debug("新增 worker{}, {}", worker, task);
                workers.add(worker);
                worker.start();
            } else {
                // 任务数 >= corePoolSize 时，加入任务队列等待
                // 1. 死等
                // taskQueue.put(task);
                // 2. 带超时时间
                // 3. 放弃任务执行
                // 4. 抛出异常
                // 5. 让调用者自己执行任务

                // 策略模式
                taskQueue.tryPut(rejectPolicy, task);
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
            // 执行任务
            // 1) 当 task 不为空，执行任务
            // 2) 任务队列不为空，取出任务并执行
            // while (task != null || (task = taskQueue.take()) != null) {
            while (task != null || (task = taskQueue.poll(timeout, unit)) != null) {
                try {
                    log.debug("正在执行...{}", task);
                    task.run();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // 任务执行完，重置 task
                    task = null;
                }
            }

            synchronized (workers) {
                // 移除当前线程
                log.debug("worker 被移除{}", this);
                workers.remove(this);
            }
        }
    }
}
