package com.dororo.future.gencopilot.service;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.IdUtil;
import com.dororo.future.gencopilot.constant.CacheConstants;
import com.dororo.future.gencopilot.domain.GenSysUser;
import com.dororo.future.gencopilot.domain.LoggedGenSysUser;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TokenService {
    @Resource
    private TokenService cacheThisVo;

    /**
     * 创建token并处理缓存等
     */
    public LoggedGenSysUser loginAction(GenSysUser genSysUser) {
        String token = IdUtil.simpleUUID();
        // 将domain置换为登录用户Bean
        LoggedGenSysUser loggedGenSysUser = Convert.convert(LoggedGenSysUser.class, genSysUser);
        loggedGenSysUser.setToken(token);
        loggedGenSysUser.setIsLoggedIn(Boolean.TRUE);
        // 缓存token 
        cacheThisVo.cachePutToken(token, loggedGenSysUser);

        return loggedGenSysUser;
    }

    @Cacheable(cacheNames = CacheConstants.TOKEN, key = "#token", unless = "#result == null")
    public LoggedGenSysUser cacheableToken(String token, LoggedGenSysUser user) {
        return user;
    }

    @CachePut(cacheNames = CacheConstants.TOKEN, key = "#token", unless = "#result == null")
    public LoggedGenSysUser cachePutToken(String token, LoggedGenSysUser user) {
        return user;
    }

    @CacheEvict(cacheNames = CacheConstants.TOKEN, key = "#token")
    public LoggedGenSysUser cacheEvictToken(String token, LoggedGenSysUser user) {
        return user;
    }
}
