package com.dororo.future.gencopilot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateCfgBatchDeleteReqDTO {
    @NotEmpty(message = "names不能为空")
    private List<String> names;
}
