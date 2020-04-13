package com.meng.learn.cache.service;

import org.springframework.stereotype.Service;

@Service
public class CaffeineService2 {
    public String getCache(String key) {
        try {
            System.out.println("getCache()方法执行");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return key;
    }
}
