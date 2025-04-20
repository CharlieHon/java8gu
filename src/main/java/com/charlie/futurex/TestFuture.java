package com.charlie.futurex;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.concurrent.*;

/**
 * @Author: charlie
 * @CreateTime: Created in 2025/3/13 21:30
 * @Description: TestFuture
 */
@Slf4j(topic = "c.TestFuture")
public class TestFuture {
    public static void main(String[] args) {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(1,
                1,
                0,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );

        pool.submit(() -> {
            System.out.println("123");
        });

        CompletableFuture<Void> futureT1 = CompletableFuture.runAsync(() -> {
            log.debug("T1 is executing. Current time: {}", LocalDateTime.now());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        CompletableFuture<Void> futureT2 = CompletableFuture.runAsync(() -> {
            log.debug("T2 is executing. Current time: {}", LocalDateTime.now());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        CompletableFuture<Void> bothCompleted = CompletableFuture.allOf(futureT1, futureT2);
        bothCompleted.thenRunAsync(() -> {
            log.debug("T3 is executing. Current time: {}", LocalDateTime.now());
        });

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
