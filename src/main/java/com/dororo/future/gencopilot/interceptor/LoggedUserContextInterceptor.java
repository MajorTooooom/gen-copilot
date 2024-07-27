package com.dororo.future.gencopilot.interceptor;

import cn.hutool.core.util.StrUtil;
import com.dororo.future.gencopilot.constant.CommonConstant;
import com.dororo.future.gencopilot.domain.LoggedGenSysUser;
import com.dororo.future.gencopilot.holder.LoginUserContextHolder;
import com.dororo.future.gencopilot.service.TokenService;
import com.dororo.future.gencopilot.util.UriWhitelistUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 处理用户上下文信息
 *
 * @author Dororo
 * @date 2024-04-30 11:39:35
 **/
@Slf4j
@Component
public class LoggedUserContextInterceptor implements HandlerInterceptor {
    @Autowired
    private TokenService tokenService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        if (UriWhitelistUtils.filter(requestURI)) {
            return true;
        }
        // token换取用户信息
        String token = request.getHeader(CommonConstant.TOKEN_KEY);
        if (StrUtil.isNotBlank(token)) {
            LoggedGenSysUser loggedGenSysUser = tokenService.cacheableToken(token, null);
            if (loggedGenSysUser != null) {
                // 将用户信息放入ThreadLocal
                LoginUserContextHolder.setUser(loggedGenSysUser);
                // 刷新token过期时间
                tokenService.cachePutToken(token, loggedGenSysUser);
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LoginUserContextHolder.clear(); // 清除 ThreadLocal 中的数据
    }
}
