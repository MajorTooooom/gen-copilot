package com.dororo.future.gencopilot.util;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.HMac;

public class CryptoUtils {
    // 全局显式秘钥
    private static final String EXPLICIT_SECRET_KEY = "6661234567890666";

    /**
     * 摘要算法加密
     *
     * @param content 被加密内容
     * @return 摘要算法密文
     */
    public static String summaryEncrypt(String content) {
        // 非线程安全
        HMac hMac = SecureUtil.hmacSha1(EXPLICIT_SECRET_KEY);
        String digestHex = hMac.digestHex(content);
        return digestHex;
    }
}
