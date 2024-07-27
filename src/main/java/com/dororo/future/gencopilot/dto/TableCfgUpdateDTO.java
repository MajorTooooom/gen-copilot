package com.dororo.future.gencopilot.dto;

import com.dororo.future.gencopilot.domain.ColumnCfg;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TableCfgUpdateDTO {
    @NotNull(message = "ID不能为空")
    private Integer id;
    private Integer userId;
    @NotBlank(message = "表配置名称不能为空")
    private String tableCfgName;
    @NotBlank(message = "数据源URL不能为空")
    private String connUrl;
    @NotBlank(message = "数据源用户名不能为空")
    private String connUsername;
    @NotBlank(message = "数据源密码不能为空")
    private String connPassword;
    @NotBlank(message = "库名不能为空")
    private String tableSchema;
    @NotBlank(message = "表名不能为空")
    private String tableName;
    @NotBlank(message = "实体类名不能为空")
    private String domainName;
    @NotBlank(message = "实体类中文名不能为空")
    private String domainZnName;
    //
    private List<ColumnCfg> columnCfgs;
}