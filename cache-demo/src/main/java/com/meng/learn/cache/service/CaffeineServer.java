package com.meng.learn.cache.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CaffeineServer {
    @Autowired
    private CaffeineService service;

    @Cacheable(value = "IZUUL", key = "#key")
    public String cacheIZUUL(String key){
        System.out.println("cacheIZUUL");
        return service.getCache(key);
    }
}
