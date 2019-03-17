package chapter08;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 
 * @author tengfei.fangtf
 * @version $Id: SemaphoreTest.java, v 0.1 2015-8-1 ����12:10:19 tengfei.fangtf Exp $
 *
 *
 *
 *
 * => 用来控制同步资源的可以同时访问的数量
 */
public class SemaphoreTest {

    private static final int       THREAD_COUNT = 30;

    private static ExecutorService threadPool   = Executors.newFixedThreadPool(THREAD_COUNT);

    private static Semaphore       s            = new Semaphore(10);

    public static void main(String[] args) {
        for (int i = 0; i < THREAD_COUNT; i++) {
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {

                        // 获取信号良
                        s.acquire();
                        System.out.println("save data");
                        // 释放信号量
                        s.release();
                    } catch (InterruptedException e) {
                    }
                }
            });
        }

        threadPool.shutdown();
    }
}
