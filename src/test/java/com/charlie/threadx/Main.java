package com.charlie.threadx;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author: charlie
 * @CreateTime: Created in 2025/3/11 9:19
 * @Description: Main
 */
@Slf4j(topic = "c.Main")
public class Main {
    public static void main(String[] args) {
        ThreadPoolH pool = new ThreadPoolH(1,
                1000,
                TimeUnit.MILLISECONDS,
                1,
                (task, queue) -> {
                    // 1) Discard
                    // log.debug("Rejected");
                    // 2) CallerRunsPolicy
                    task.run();
                });

        for (int i = 0; i < 5; i++) {
            int j = i;
            pool.execute(() -> {
                try {
                    Thread.sleep(20000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.debug("{}", j);
            });
        }

        ExecutorService service = Executors.newFixedThreadPool(2);
    }
}
