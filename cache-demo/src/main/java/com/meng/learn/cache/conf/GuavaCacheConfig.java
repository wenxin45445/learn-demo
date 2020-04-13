package com.meng.learn.cache.conf;


import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.meng.learn.cache.cache.GuavaCacheLoader;
import com.meng.learn.cache.cache.GuavaCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@EnableConfigurationProperties(GuavaProperties.class)
@Configuration
@EnableCaching
public class GuavaCacheConfig {

    @Autowired
    private GuavaProperties guavaProperties;

    @Bean
    public CacheBuilder<Object,Object> cacheBuilder(){
        long maximumSize = guavaProperties.getMaximumSize();
        long expireAfterWrite = guavaProperties.getExpireAfterWriteDuration();
        long expireAfterAccess = guavaProperties.getExpireAfterAccessDuration();
        long refreshDuration = guavaProperties.getRefreshDuration();

        if(maximumSize <= 0){
            maximumSize = 1024;
        }
        if(expireAfterAccess <= 0){
            expireAfterAccess = 3600;
        }
        if(expireAfterWrite <= 0){
            expireAfterWrite = 3600;
        }

        if(refreshDuration <= 0){
            refreshDuration = 1800;
        }

        return CacheBuilder
                .newBuilder()
                .maximumSize(maximumSize)
                .expireAfterAccess(expireAfterAccess, TimeUnit.SECONDS)
                .expireAfterWrite(expireAfterWrite,TimeUnit.SECONDS)
                .refreshAfterWrite(refreshDuration, TimeUnit.SECONDS);
    }

    @Bean(name = "guavaCacheLoader")
    public CacheLoader cacheLoader(){
        return new GuavaCacheLoader();
    }

    @Bean
    public GuavaCacheManager cacheManager(@Qualifier("cacheBuilder")CacheBuilder cacheBuilder,
                                     @Qualifier("guavaCacheLoader")CacheLoader cacheLoader){
        GuavaCacheManager cacheManager = new GuavaCacheManager();
        return cacheManager;
    }



}
