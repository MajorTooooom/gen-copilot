<#include "环境变量辅助.ftl"/>
package ${dtoPackage};

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
<#list javaTypes as javaType>
import ${javaType};
</#list>
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 删除`${domainChineseDescription}`DTO
 */
@ApiModel(value = "删除${domainChineseDescription}", description = "删除`${domainChineseDescription}`")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ${domainName}DeleteDTO {
    @NotEmpty(message = "ids不能为空")
    <#if primaryKeyColumnCfg?? && primaryKeyColumnCfg.javaTypeClassName??>
    private List<${primaryKeyColumnCfg.javaTypeClassName}> ids;
    <#else >
    private List<Integer> ids;
    </#if>
}