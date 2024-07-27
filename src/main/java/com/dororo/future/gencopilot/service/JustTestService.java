package com.dororo.future.gencopilot.service;

import cn.hutool.core.thread.ThreadUtil;
import com.dororo.future.gencopilot.constant.CacheConstants;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class JustTestService {

    @Cacheable(cacheNames = CacheConstants.TOKEN, key = "#root.methodName")
    public String test() {
        // 模拟长耗时
        ThreadUtil.sleep(10 * 1000L);
        return "test";
    }
}
