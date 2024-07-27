package com.dororo.future.gencopilot.holder;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

/**
 * @description: 分页参数上下文持有者
 */
public class PageContextHolder {
    private static final ThreadLocal<Page> pageThreadLocal = new ThreadLocal<>();

    public static Page getPage() {
        return pageThreadLocal.get();
    }

    public static void setPage(Page page) {
        pageThreadLocal.set(page);
    }

    public static void clear() {
        pageThreadLocal.remove();
    }

    public static void startPage() {
        Page page = getPage();
        if (StrUtil.isNotBlank(page.getOrderBy())) {
            PageHelper.startPage(page.getPageNum(), page.getPageSize(), page.getOrderBy() + " " + Optional.ofNullable(page.getAscend()).orElse("asc"));
        } else {
            PageHelper.startPage(page.getPageNum(), page.getPageSize());
        }
    }


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Page {
        private Integer pageNum;
        private Integer pageSize;
        private String orderBy;
        private String ascend;// asc,desc
    }
}
