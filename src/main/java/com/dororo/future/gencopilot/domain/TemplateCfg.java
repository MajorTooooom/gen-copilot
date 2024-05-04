package com.dororo.future.gencopilot.domain;

import com.dororo.future.gencopilot.dto.TemplateEnvDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 取消数据库表,改为仅domain对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateCfg {
    // 虚拟ID
    private Integer id;
    // 传递来自Request信息:1: 渲染至项目目录 2: 下载
    private Integer mode;
    // 模板归属用户ID
    private Integer userId;
    // 模板文件在服务器的绝对路径
    private String absPath;
    // 模板文件名(包含扩展),例如:`controller.ftl`
    private String templateName;
    // 模板文件扩展名
    private String extName;
    // 模板渲染的目标目录(父目录)绝对路径
    private String destDirAbsPath;
    // 模板渲染的目标包名,eg:`com.dororo.future`,如果配置了,则最终计算目标文件绝对路径=目标目录(父目录)绝对路径+目标包名转路径+文件名
    private String destPackage;
    // 模板渲染的目标文件名,包含扩展名,例如`SkillController.java`
    private String expectName;
    // 模板文件最后更新时间
    private String updateTime;
    // 本次渲染是否使用该模板
    private Boolean use;
    // 渲染成功
    private Boolean renderSuccess;
    // 渲染失败原因
    private String renderException;
    // 模板内部使用的环境变量
    private TemplateEnvDTO env;
}