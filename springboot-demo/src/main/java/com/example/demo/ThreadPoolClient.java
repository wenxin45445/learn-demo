package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ThreadPoolClient {

    @Autowired
    private ThreadPoolTest threadPoolTest;

    public void sayHelloFuture(long taskNum, long times) {

        for (int i = 0; i < taskNum; i++) {
            threadPoolTest.sayHelloFuture(times);
        }
    }

    public void sayHelloFuture2(long taskNum, long times) {
        for (int i = 0; i < taskNum; i++) {
            threadPoolTest.sayHelloFuture2(times);
        }
    }
}
