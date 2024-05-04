package com.dororo.future.gencopilot.holder;

import com.dororo.future.gencopilot.domain.LoggedGenSysUser;

/**
 * @description: 登录用户上下文持有者
 */
public class LoginUserContextHolder {
    private static final ThreadLocal<LoggedGenSysUser> userThreadLocal = new ThreadLocal<>();

    public static LoggedGenSysUser getUser() {
        return userThreadLocal.get();
    }

    public static void setUser(LoggedGenSysUser user) {
        userThreadLocal.set(user);
    }

    public static void clear() {
        userThreadLocal.remove();
    }
}
