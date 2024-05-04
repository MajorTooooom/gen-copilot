package com.dororo.future.gencopilot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class LoggedGenSysUser extends GenSysUser {
    private Boolean isLoggedIn;
    private String token;
}
