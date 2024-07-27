package com.dororo.future.gencopilot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class GenSysUser {
    private Integer id;

    private String username;

    private String password;

    private Integer enable;

    private String createTime;

    private String updateTime;
}