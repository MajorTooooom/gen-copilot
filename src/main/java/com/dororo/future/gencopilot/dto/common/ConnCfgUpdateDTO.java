package com.dororo.future.gencopilot.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConnCfgUpdateDTO {
    @NotNull(message = "ID不能为空")
    private Integer id;
    private String cfgName;
    private String url;
    private String username;
    private String password;
}