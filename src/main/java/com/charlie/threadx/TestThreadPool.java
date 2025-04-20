package com.charlie.threadx;

import java.util.concurrent.*;

/**
 * @Author: charlie
 * @CreateTime: Created in 2025/3/9 22:05
 * @Description: TestThreadPool
 */
public class TestThreadPool {

    public static void test1() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5,
                6,
                5,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(10)
        );

        ExecutorService service = Executors.newSingleThreadExecutor();
    }

}
