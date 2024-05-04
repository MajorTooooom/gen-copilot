package com.dororo.future.gencopilot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * element-ui 表单属性KEY
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UiFormFieldDTO {
    // 字段名
    private String prop;
    // 标签名
    private String label;
    // 组件名称
    private String component;
    // 是否禁用
    private Boolean disabled;
    // 当组件为`el-select`时,下拉选项数据
    private List<Option> options;
    // 组件属性,例如placeholder='请输入'
    private Map<String, Object> componentPropMap;
    // 样式属性
    private Map<String, Object> styleMap;


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Option {
        private String value;
        private String label;
    }
}