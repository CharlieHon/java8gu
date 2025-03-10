package com.charlie.threadpoolx;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author: charlie
 * @CreateTime: Created in 2025/3/10 19:58
 * @Description: Test
 */
@Slf4j(topic = "c.Test")
public class Test {
    public static void main(String[] args) {
        ThreadPoolx pool = new ThreadPoolx(
                1,
                1000,
                TimeUnit.MILLISECONDS,
                1,
                (task, queue) -> {
                    // 1) 直接丢弃
                    // log.debug("丢弃任务{}", task);
                    // 2) 死等
                    // queue.put(task);

                    // 3) 超时等待
                    // queue.offer(task, 1500, TimeUnit.MILLISECONDS);

                    // 4. 放弃任务执行
                    // log.debug("放弃任务{}", task);

                    // 5. 抛出异常，主线程之后的代码都不会执行
                    // throw new RuntimeException("任务执行失败 " + task);

                    // 6. 让调用者自己执行任务
                    task.run();
                }
        );

        for (int i = 0; i < 4; i++) {
            int j = i;
            pool.execute(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.debug("{}", j);
            });
        }
    }

    public static void test1() {
        ExecutorService service = Executors.newFixedThreadPool(5);
    }
}
