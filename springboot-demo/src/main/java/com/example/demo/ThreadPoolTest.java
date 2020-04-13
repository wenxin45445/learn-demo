package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Component
public class ThreadPoolTest {

    Logger logger = LoggerFactory.getLogger(ThreadPoolTest.class);

    @Async("threadPoolConfig1")
    public ListenableFuture<String> sayHelloFuture(long times) {
        long st = System.currentTimeMillis();

        while (true) {
            long end = System.currentTimeMillis();
            if (end - st > times) {
                break;
            }
            synchronized (LockTest.obj) {
                try {
                    LockTest.obj.wait(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            long re = 0;
            while (System.currentTimeMillis() - end < times / 4) {
                for (int i = 0; i < times; i++) {
                    logger.info("i an doing , i am sayHelloFuture");
                    re += i * i;
                }
            }
/*

            try {
                Thread.sleep(times);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/

        }

        return new AsyncResult<>("sayHelloFuture");
    }

    @Async("threadPoolConfig2")
    public ListenableFuture<String> sayHelloFuture2(long times) {
        long st = System.currentTimeMillis();

        while (true) {
            long end = System.currentTimeMillis();
            if (end - st > times) {
                break;
            }

            long re = 0;
            while (System.currentTimeMillis() - end < times / 3) {
                for (int i = 0; i < times; i++) {
                    logger.info("i an doing , i am sayHelloFuture");
                    re += i * i * 2;
                }
            }

            synchronized (LockTest.obj) {
                try {
                    LockTest.obj.wait(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            /*
            try {
                Thread.sleep(times);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }

        return new AsyncResult<>("sayHelloFuture2");
    }
}
