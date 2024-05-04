package com.dororo.future.gencopilot.config;


import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();

        // 创建 Caffeine 实例为 "token"
        cacheManager.registerCustomCache("token", Caffeine.newBuilder()
                .maximumSize(100)// 最多缓存N个条目
                .expireAfterWrite(5 * 60, TimeUnit.SECONDS)// 写入后N秒过期
                .expireAfterAccess(5 * 60, TimeUnit.SECONDS)// 最后一次访问后N秒过期
                .build());

        // 创建 Caffeine 实例为 "tablePoolOptions"
        cacheManager.registerCustomCache("tablePoolOptions", Caffeine.newBuilder()
                .maximumSize(100)// 最多缓存N个条目
                .expireAfterWrite(5 * 60, TimeUnit.SECONDS)// 写入后N秒过期
                .expireAfterAccess(5 * 60, TimeUnit.SECONDS)// 最后一次访问后N秒过期
                .build());

        return cacheManager;
    }
}
