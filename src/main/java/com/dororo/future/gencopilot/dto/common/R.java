package com.dororo.future.gencopilot.dto.common;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 控制层基础响应体
 *
 * @author Dororo
 * @date 2023-6-26 23:04:39  更名为`R`
 * @date 2023-6-26 23:04:39 和`RuoYi-Vue`框架中`AjaxResult`类保持一致,区别是以Bean的形式存在,目的是内置一些常用方法;
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class R implements Serializable {
    /**
     * 默认成功状态码,与`AjaxResult`逻辑保持一致
     */
    public final static Integer DEFAULT_SUCCESS_CODE = Convert.toInt(HttpStatus.HTTP_OK);
    /**
     * 默认失败状态码,与`AjaxResult`逻辑保持一致
     */
    public final static Integer DEFAULT_ERROR_CODE = Convert.toInt(HttpStatus.HTTP_INTERNAL_ERROR);

    @Builder.Default
    private Integer code = DEFAULT_SUCCESS_CODE;
    @Builder.Default
    private String msg = "success";
    private Object data;

    public static R success() {
        return R.builder().build();
    }

    public static R success(Integer code) {
        return R.builder().code(code).build();
    }

    public static R success(String message) {
        return R.builder().msg(message).build();
    }

    public static R success(Integer code, String message) {
        return R.builder().code(code).msg(message).build();
    }

    public static R successData(Object data) {
        return R.builder().data(data).build();
    }

    public static R error(String message) {
        return R.builder().code(DEFAULT_ERROR_CODE).msg(message).build();
    }

    public static R error(Integer code, String message) {
        return R.builder().code(code).msg(message).build();
    }

    public static R errorData(Object data) {
        return R.builder().code(DEFAULT_ERROR_CODE).data(data).build();
    }

    public static R page(List<?> list) {
        PageInfo<?> pageInfo = new PageInfo<>(list);
        JSONObject data = JSONUtil.createObj()
                .putOpt("list", list)
                .putOpt("pageNum", pageInfo.getPageNum())
                .putOpt("pageSize", pageInfo.getPageSize())
                .putOpt("total", pageInfo.getTotal())
                .putOpt("totalPage", pageInfo.getPages());
        return R.successData(data);
    }


    /**
     * 判断请求是否成功
     */
    public boolean isSuccess() {
        Integer code = this.getCode();
        if (code != null && NumberUtil.equals(Convert.toLong(code), Convert.toLong(DEFAULT_SUCCESS_CODE))) {
            return true;
        }
        return false;
    }
}
