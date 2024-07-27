package com.dororo.future.gencopilot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectCfgUpdateDTO {
    @NotNull(message = "id不能为空")
    private Integer id;
    @NotNull(message = "用户ID不能为空")
    private Integer userId;
    private String projectCfgName;
    private String sourceCodeAbsPath;
    private String domainPackage;
    private String dtoPackage;
    private String voPackage;
    private String mapperPackage;
    private String servicePackage;
    private String controllerPackage;
    private String easyExcelListenerPackage;
    private String resourceAbsPath;
    private String mapperXmlPackage;
    private String isExtendTkMapper;
    private String tkMapperPackage;
    private String isGenSwagger;
    private String isGenComment;
    private String isGenEasyExcel;
    private String isGenJavaxValidation;
    // private String isDeleted;
    // private String createTime;
    // private String updateTime;
}