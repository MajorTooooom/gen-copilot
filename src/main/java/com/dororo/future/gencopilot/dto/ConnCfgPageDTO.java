package com.dororo.future.gencopilot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConnCfgPageDTO {
    private Integer id;

    private Integer userId;

    private String cfgName;

    private String url;

    private String username;

    private String password;

    // private String isDeleted;

    private String createTime;

    private String updateTime;
}