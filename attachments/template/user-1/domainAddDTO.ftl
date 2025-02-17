<#include "环境变量辅助.ftl"/>
package ${dtoPackage};

<#if isGenSwagger! =="true">
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
</#if>
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
<#list javaTypes as javaType>
import ${javaType};
</#list>

/**
* 新增`${domainChineseDescription}`DTO
*/
<#if isGenSwagger! == "true">
@ApiModel(value = "新增${domainChineseDescription}", description = "新增`${domainChineseDescription}`")
</#if>
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ${domainName}AddDTO {
<#list columns as column>
    <#if column.columnKey!?has_content && column.columnKey == "PRI">
    // private Integer id;
    <#else>
        <#if isGenSwagger! == "true">
    @ApiModelProperty(value = "${((column.columnSwaggerComment?trim!"")?length > 0)?then(column.columnSwaggerComment, column.javaName)}", required = true)
        </#if>
    @${((column.javaType?trim!"") == "java.lang.String")?then("NotBlank","NotNull")}(message = "${((column.columnValidationComment?trim!"")?length > 0)?then(column.columnValidationComment, column.javaName)}不能为空")
    private ${column.javaTypeClassName} ${column.javaName};
    </#if>
    <#if column?has_next>

    </#if>
</#list>
}