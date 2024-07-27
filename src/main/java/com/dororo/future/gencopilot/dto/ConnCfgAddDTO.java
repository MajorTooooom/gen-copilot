package com.dororo.future.gencopilot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConnCfgAddDTO {
    @NotBlank(message = "配置名称不能为空")
    private String cfgName;
    @NotBlank(message = "URL不能为空")
    private String url;
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
}