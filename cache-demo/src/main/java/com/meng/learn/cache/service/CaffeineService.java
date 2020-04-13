package com.meng.learn.cache.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CaffeineService {
    @Autowired
    CaffeineService2 caffeineService2;
    public String getCache(String key) {
        return caffeineService2.getCache(key);
    }
}
