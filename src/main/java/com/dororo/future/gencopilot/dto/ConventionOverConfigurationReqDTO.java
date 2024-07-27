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
public class ConventionOverConfigurationReqDTO {
    @NotNull(message = "表(&表字段)配置ID不能为空")
    private Integer tableCfgId;
    @NotNull(message = "(渲染)项目配置ID不能为空")
    private Integer projectCfgId;
}
