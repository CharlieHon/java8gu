package com.charlie.threadx;

import com.charlie.util.LoggerUtils;

import static com.charlie.util.LoggerUtils.get;
import static com.charlie.util.LoggerUtils.main;

public class WaitVsSleep {
    static final Object LOCK = new Object();

    public static void main(String[] args) throws InterruptedException {
        sleeping();
    }

    private static void illegalWait() throws InterruptedException {
        // 在没有获得对象锁之前直接调用，会抛出 java.lang.IllegalMonitorStateException 异常
        LOCK.wait();

        // synchronized (LOCK) {
        //     LOCK.wait();
        // }
    }

    private static void waiting() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            synchronized (LOCK) {
                try {
                    LoggerUtils.get("t").debug("waiting...");
                    LOCK.wait(5000L);
                } catch (InterruptedException e) {
                    LoggerUtils.get("t").debug("interrupted...");
                    e.printStackTrace();
                }
            }
        }, "t1");
        t1.start();

        Thread.sleep(100);
        synchronized (LOCK) {
            main.debug("other...");
        }

    }

    private static void sleeping() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            synchronized (LOCK) {
                try {
                    get("t").debug("sleeping...");
                    Thread.sleep(5000L);
                } catch (InterruptedException e) {
                    get("t").debug("interrupted...");
                    e.printStackTrace();
                }
            }
        }, "t1");
        t1.start();

        // Thread.sleep(timeout); 在线程获得锁后被打断，不会释放锁
        Thread.sleep(100);
        // t1.interrupt();
        synchronized (LOCK) {
            main.debug("other...");
        }
    }
}
