package com.charlie.threadx;

/**
 * @Author: charlie
 * @CreateTime: Created in 2025/3/11 9:48
 * @Description: RejectPolicy
 */
public interface RejectPolicyH<T> {
    void reject(T task, BlockingQueueH<T> queue);
}
