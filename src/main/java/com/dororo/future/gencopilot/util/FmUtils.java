package com.dororo.future.gencopilot.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileWriter;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * FreeMarker工具类
 */
public class FmUtils {

    /**
     * 获取通用FreeMarker配置
     */
    private static Configuration getCommonFmCfg() throws Exception {
        // 版本号需要根据导入的Maven依赖决定,当前SpringBoot-2.7.18对应的版本是2.3.32
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
        // 通过`setSharedVariable`方法将DateUtil和StrUtil注册到FreeMarker的全局变量中,以便在模板中使用
        cfg.setSharedVariable("DateUtil", new DateUtil());
        cfg.setSharedVariable("StrUtil", new StrUtil());
        return cfg;
    }


    /**
     * 模板来源于字符串,渲染为字符串
     */
    @SneakyThrows
    public static String stringToString(String templateContent, Object dataModel) {
        Configuration cfg = getCommonFmCfg();
        Template template = new Template(null, new StringReader(templateContent), cfg);
        try (StringWriter writer = new StringWriter()) {
            template.process(dataModel, writer);
            return writer.toString();
        }
    }

    /**
     * 模板来源于文件,渲染为文件
     *
     * @param from 模板文件
     * @param to   目标文件
     */
    @SneakyThrows
    public static File fileToFile(File from, File to, Object dataModel) {
        Configuration cfg = getCommonFmCfg();
        // 设置模板所在目录
        cfg.setDirectoryForTemplateLoading(FileUtil.getParent(from, 1));
        Template template = cfg.getTemplate(FileUtil.getName(from));
        // 创建目标文件的父目录
        FileUtil.mkdir(FileUtil.getParent(to, 1));
        // 渲染模板
        try (FileWriter writer = new FileWriter(to)) {
            template.process(dataModel, writer);
            return to;
        }
    }

    /**
     * @param from 模板文件路径
     * @param to   目标文件路径
     */
    public static File fileToFile(String from, String to, Object dataModel) {
        return fileToFile(FileUtil.file(from), FileUtil.file(to), dataModel);
    }


}
