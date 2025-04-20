package com.charlie.threadx;

import static com.charlie.util.LoggerUtils.logger1;
import static com.charlie.util.LoggerUtils.main;

/**
 * @Author: charlie
 * @CreateTime: Created in 2025/3/1 13:07
 * @Description: Java线程分成六种状态
 * 1. 新建（New）：新创建Thread对象时的状态，Thread t1 = new Thead();
 * 2. 运行（Runnable）：t.start(); 可以运行/获得时间片正在运行状态
 * 3. 阻塞（Blocked）：竞争锁失败后的状态
 * 4. 等待（Waiting）：获取锁成功后，调用对象的 obj.wait() 方法，释放对象锁并进入 waiting 状态
 * 5. 超时等待 (Timed Waiting)：获得锁调用对象的 obj.wait(timeout)方法；或者 Thread.sleep(timeout) 方法
 * 6. 运行结束（Terminated）：线程run方法执行结束
 */
public class TestThreadState {

    static final Object LOCK = new Object();
    public static void main(String[] args) throws InterruptedException {
        // testWaiting();
        testBlocked();
        // testNewRunnableTerminated();
        // testTimedWaiting();
    }

    private static void testWaiting() {
        Thread t2 = new Thread(() -> {
            synchronized (LOCK) {
                logger1.debug("before waiting"); // 1       runnable
                try {
                    LOCK.wait(); // 3                       waiting
                    logger1.debug("after waiting");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t2");

        t2.start();
        main.debug("state: {}", t2.getState()); // 2        runnable
        synchronized (LOCK) {
            main.debug("state: {}", t2.getState()); // 4    waiting
            LOCK.notify(); // 5                             waiting -> blocked
            main.debug("state: {}", t2.getState()); // 6    blocked
        }
        main.debug("state: {}", t2.getState()); // 7        runnable
    }

    private static void testTimedWaiting() {
        Thread t2 = new Thread(() -> {
            synchronized (LOCK) {
                logger1.debug("before waiting"); // 1       runnable
                try {
                    LOCK.wait(30000); // 3                       waiting
                    logger1.debug("after waiting");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t2");

        t2.start();
        main.debug("state: {}", t2.getState()); // 2        runnable
        synchronized (LOCK) {
            main.debug("state: {}", t2.getState()); // 4    waiting
            LOCK.notify(); // 5                             waiting -> blocked
            main.debug("state: {}", t2.getState()); // 6    blocked
        }
        main.debug("state: {}", t2.getState()); // 7        runnable
    }

    private static void testBlocked() {
        Thread t2 = new Thread(() -> {
            logger1.debug("before sync"); // 3
            synchronized (LOCK) {
                logger1.debug("in sync"); // 4
            }
        },"t2");

        t2.start();
        main.debug("state: {}", t2.getState()); // 1
        synchronized (LOCK) {
            main.debug("state: {}", t2.getState()); // 2
        }
        main.debug("state: {}", t2.getState()); // 5
    }

    private static void testNewRunnableTerminated() {
        Thread t1 = new Thread(() -> {
            logger1.debug("running..."); // 3
        },"t1");

        main.debug("state: {}", t1.getState()); // 1
        t1.start();
        main.debug("state: {}", t1.getState()); // 2

        main.debug("state: {}", t1.getState()); // 4
    }

}
