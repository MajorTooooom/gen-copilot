package com.zhien.fac.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.zhien.common.core.domain.R;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class EasyExcelUtils {
    /**
     * 导出
     *
     * @param response 响应
     * @param list     数据
     * @param clazz    类
     * @param mainName 文件名`mainName`
     */
    public static void export(HttpServletResponse response, List list, Class clazz, String mainName) {
        Assert.notNull(response);
        list = CollectionUtil.isNotEmpty(list) ? list : new ArrayList();
        mainName = StrUtil.isNotBlank(mainName) ? mainName : "data";
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            String businessName = StrUtil.format("{}_{}", mainName, DateUtil.format(DateUtil.date(), "yyyyMMdd_HHmmss"));
            String fileName = URLEncoder.encode(businessName, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), clazz).autoCloseStream(Boolean.FALSE).sheet("data").doWrite(list);
        } catch (Exception e) {
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            R error = R.error("下载文件失败" + e.getMessage());
            try {
                response.getWriter().println(JSONUtil.toJsonStr(error));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
