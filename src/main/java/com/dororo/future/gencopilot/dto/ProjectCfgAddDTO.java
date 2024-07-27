package com.dororo.future.gencopilot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectCfgAddDTO {
    // private Integer id;
    // private Integer userId;
    @NotBlank(message = "配置名不能为空")
    private String projectCfgName;
    @NotBlank(message = "源代码目录绝对路径不能为空")
    private String sourceCodeAbsPath;
    @NotBlank(message = "实体类包引用不能为空")
    private String domainPackage;
    @NotBlank(message = "DTO包引用不能为空")
    private String dtoPackage;
    @NotBlank(message = "VO包引用不能为空")
    private String voPackage;
    @NotBlank(message = "mapper包引用不能为空")
    private String mapperPackage;
    @NotBlank(message = "service包引用不能为空")
    private String servicePackage;
    @NotBlank(message = "Controller包引用不能为空")
    private String controllerPackage;
    @NotBlank(message = "easyExcelListener包引用不能为空")
    private String easyExcelListenerPackage;
    @NotBlank(message = "资源文件根目录不能为空")
    private String resourceAbsPath;
    @NotBlank(message = "mapperXml包引用不能为空")
    private String mapperXmlPackage;
    private String isExtendTkMapper;
    private String tkMapperPackage;
    private String isGenSwagger;
    private String isGenComment;
    private String isGenEasyExcel;
    private String isGenJavaxValidation;
    // private String createTime;
    // private String updateTime;
}