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
public class ConnCfgTablePoolDTO {
    @NotBlank(message = "url不能为空")
    private String url;
    @NotBlank(message = "连接用户名不能为空")
    private String username;
    @NotBlank(message = "连接密码不能为空")
    private String password;
}
