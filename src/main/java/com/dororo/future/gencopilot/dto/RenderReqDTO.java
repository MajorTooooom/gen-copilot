package com.dororo.future.gencopilot.dto;

import com.dororo.future.gencopilot.domain.TemplateCfg;
import com.dororo.future.gencopilot.enums.RenderModeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RenderReqDTO {
    /**
     * @see RenderModeEnum
     */
    @NotNull(message = "模式不能为空")
    private Integer mode;
    @NotNull(message = "表配置ID不能为空")
    private Integer tableCfgId;
    @NotNull(message = "项目配置ID不能为空")
    private Integer projectCfgId;
    @NotEmpty(message = "模板配置不能为空")
    private List<TemplateCfg> list;
}
