package com.charlie.threadpoolx;

/**
 * @Author: charlie
 * @CreateTime: Created in 2025/3/10 21:43
 * @Description: 任务拒绝策略
 */
@FunctionalInterface
public interface RejectPolicy<T> {

    void reject(T task, BlockingQueuex<T> queue);
}
