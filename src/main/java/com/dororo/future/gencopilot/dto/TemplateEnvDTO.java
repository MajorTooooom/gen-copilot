package com.dororo.future.gencopilot.dto;

import com.dororo.future.gencopilot.domain.ColumnCfg;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateEnvDTO {
    // from ColumnCfg
    List<ColumnCfg> columns;
    private ColumnCfg primaryKeyColumnCfg;// 从columns中获取主键cfg
    // from tableCfg
    private String tableName;// 表名
    private String domainName;// 实体类名
    private String domainZnName;// 实体类中文名
    // from genCfg
    private String sourceCodeAbsPath;// 源代码目录绝对路径
    private String domainPackage;// 实体类包名
    private String dtoPackage;// dto包名
    private String voPackage;// vo包名
    private String mapperPackage;// mapper包名
    private String servicePackage;// service包名
    private String controllerPackage;// controller包名
    private String easyExcelListenerPackage;// easyExcelListener包名
    private String resourceAbsPath;// 资源目录绝对路径
    private String mapperXmlPackage;// mapper.xml包名
    private String isExtendTkMapper;// 是否继承tkMapper
    private String tkMapperPackage;// tkMapper包名
    private String isGenSwagger;// 是否生成swagger注解
    private String isGenComment;// 是否生成javadoc注释
    private String isGenEasyExcel;// 是否生成easyExcel注解
    private String isGenJavaxValidation;// 是否生成javax.validation注解
    // 扩展
    private String author;
    private String dateTime;
    private List<String> javaTypes;
    //
    private String mchpFormatDate;
}
