package com.dororo.future.gencopilot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableCfgPageDTO {
    private Integer id;

    private String tableCfgName;

    private String connUrl;

    private String connUsername;

    private String connPassword;

    private String tableSchema;

    private String tableName;

    private String domainName;

    private String domainZnName;

    private String createTime;

    private String updateTime;
}
