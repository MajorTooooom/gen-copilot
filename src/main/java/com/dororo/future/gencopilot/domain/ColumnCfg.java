package com.dororo.future.gencopilot.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;

/**
 * @see ColumnMetadata
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ColumnCfg {
    private Integer id;

    private Integer userId;

    private Integer tableCfgId;

    private String tableSchema;

    private String tableName;

    private String ordinalPosition;

    private String columnName;

    private String columnType;

    private String columnComment;

    private String dataType;

    private String tableCatalog;

    private String columnDefault;

    private String isNullable;

    private String characterMaximumLength;

    private String characterOctetLength;

    private String numericPrecision;

    private String numericScale;

    private String datetimePrecision;

    private String characterSetName;

    private String collationName;

    private String columnKey;

    private String extra;

    private String privileges;

    private String isGenerated;

    private String generationExpression;

    private Integer srsId;

    private String javaName;

    private String javaType;

    private String javaTypeClassName;

    private String columnSwaggerComment;

    private String columnValidationComment;

    private String jdbcType;

    // =========================================================== 扩展字段 ===========================================================
    /**
     * 是否需要反引号
     */
    @Transient
    private Boolean RequireBackQuote;
}