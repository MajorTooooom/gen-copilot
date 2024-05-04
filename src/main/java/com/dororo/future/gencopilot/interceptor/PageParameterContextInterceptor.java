package com.dororo.future.gencopilot.interceptor;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.dororo.future.gencopilot.holder.PageContextHolder;
import com.dororo.future.gencopilot.util.UriWhitelistUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
public class PageParameterContextInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        if (UriWhitelistUtils.filter(requestURI)) {
            return true;
        }
        Integer pageNum = Optional.ofNullable(request).filter(Objects::nonNull).map(s -> s.getParameter("pageNum")).map(q -> Convert.toInt(q, -1)).filter(h -> h > 0).orElse(1);
        Integer pageSize = Optional.ofNullable(request).filter(Objects::nonNull).map(s -> s.getParameter("pageSize")).map(q -> Convert.toInt(q, -1)).filter(h -> h > 0).orElse(10);
        String orderBy = Optional.ofNullable(request).filter(Objects::nonNull).map(s -> s.getParameter("orderBy")).orElse(null);
        String ascend = Optional.ofNullable(request).filter(Objects::nonNull).map(s -> s.getParameter("ascend")).orElse(null);
        // orderBy防止sql注入,只允许英文大小写、数字、下划线、逗号
        if (StrUtil.isNotBlank(orderBy)) {
            Assert.isTrue(ReUtil.isMatch("^[a-zA-Z0-9_, ]*$", orderBy), "orderBy参数不合法");
            orderBy = StrUtil.trim(orderBy);
        }
        if (StrUtil.isNotBlank(ascend)) {
            Assert.isTrue(StrUtil.equalsIgnoreCase(ascend, "asc") || StrUtil.equalsIgnoreCase(ascend, "desc"), "ascend参数不合法");
            ascend = StrUtil.trim(ascend);
        }
        PageContextHolder.Page page = PageContextHolder.Page.builder()
                .pageNum(pageNum)
                .pageSize(pageSize)
                .orderBy(orderBy)
                .ascend(ascend)
                .build();
        PageContextHolder.setPage(page);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        PageContextHolder.clear(); // 清除 ThreadLocal 中的数据
    }
}
