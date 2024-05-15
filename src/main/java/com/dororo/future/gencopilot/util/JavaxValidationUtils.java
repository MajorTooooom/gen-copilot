package com.dororo.future.gencopilot.util;

import cn.hutool.extra.spring.SpringUtil;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

public class JavaxValidationUtils {
    /**
     * 验证对象
     *
     * @param obj     验证对象
     * @param isThrow 是否抛出异常, true:抛出异常, false:返回错误信息
     * @return
     */
    public static String verify(Object obj, Boolean isThrow) {
        if (obj == null) {
            return null;
        }
        // 默认抛出异常
        isThrow = isThrow == null ? true : isThrow;
        Validator validator = SpringUtil.getBean(Validator.class);
        Set<ConstraintViolation<Object>> violations = validator.validate(obj);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<Object> violation : violations) {
                sb.append(violation.getMessage()).append(";");
            }
            if (isThrow) {
                throw new RuntimeException(sb.toString());
            } else {
                return sb.toString();
            }
        }
        return null;
    }

    /**
     * 默认抛异常验证对象
     */
    public static void verify(Object obj) {
        verify(obj, true);
    }
}