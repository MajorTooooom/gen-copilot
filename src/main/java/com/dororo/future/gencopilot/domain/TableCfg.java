package com.dororo.future.gencopilot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TableCfg {
    private Integer id;

    private Integer userId;

    private String tableCfgName;

    private String connUrl;

    private String connUsername;

    private String connPassword;

    private String tableSchema;

    private String tableName;

    private String domainName;

    private String domainZnName;

    private String isDeleted;

    private String createTime;

    private String updateTime;
}