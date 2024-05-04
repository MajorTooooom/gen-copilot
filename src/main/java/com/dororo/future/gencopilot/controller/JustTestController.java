package com.dororo.future.gencopilot.controller;

import cn.hutool.json.JSONUtil;
import com.dororo.future.gencopilot.domain.LoggedGenSysUser;
import com.dororo.future.gencopilot.service.JustTestService;
import com.dororo.future.gencopilot.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class JustTestController {
    @Autowired
    private JustTestService justTestService;
    @Autowired
    private TokenService tokenService;

    @GetMapping("/getToken")
    public String test(String token) {
        LoggedGenSysUser user = tokenService.cacheableToken(token, null);
        return JSONUtil.toJsonStr(user);
    }

}
