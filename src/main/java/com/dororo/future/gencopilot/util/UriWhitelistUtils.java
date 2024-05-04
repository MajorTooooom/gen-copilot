package com.dororo.future.gencopilot.util;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;

import java.util.List;

public class UriWhitelistUtils {
    // 静态资源后缀名
    private static final List<String> staticResourceExtNames = StrUtil.split("css,ico,svg,ttf,js,woff2,html,eot,map,woff", StrUtil.COMMA);

    // 完全匹配时不需要校验
    private static final List<String> equalsUri = ListUtil.toList("/", "/login/submit", "/login/signUp", "/login/check");

    // 以这些开头的uri不需要校验
    private static final List<String> startWithUri = ListUtil.toList("/executiveCenter/download");

    /**
     * 如果是"/"或者静态资源
     */
    public static boolean filter(String requestURI) {
        // 静态资源不需要校验
        for (String extName : staticResourceExtNames) {
            if (StrUtil.endWithIgnoreCase(requestURI, StrUtil.format(".{}", extName))) {
                return true;
            }
        }
        // 首页、登录和注册接口也不需要拦截或者过滤
        for (String uri : equalsUri) {
            if (StrUtil.equals(requestURI, uri)) {
                return true;
            }
        }

        // 以这些开头的uri不需要校验
        for (String uri : startWithUri) {
            if (StrUtil.startWith(requestURI, uri)) {
                return true;
            }
        }

        // 其他情况需要拦截
        return false;
    }
}