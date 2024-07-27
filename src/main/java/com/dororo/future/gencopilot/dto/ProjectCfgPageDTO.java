package com.dororo.future.gencopilot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectCfgPageDTO {
    private Integer id;
    private Integer userId;
    private String projectCfgName;
    private String sourceCodeAbsPath;
    private String domainPackage;
    private String dtoPackage;
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
    private String createTime;
    private String updateTime;
}