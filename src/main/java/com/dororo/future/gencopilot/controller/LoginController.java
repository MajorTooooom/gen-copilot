package com.dororo.future.gencopilot.controller;

import com.dororo.future.gencopilot.domain.LoggedGenSysUser;
import com.dororo.future.gencopilot.dto.GenSysUserSubmitDTO;
import com.dororo.future.gencopilot.dto.common.R;
import com.dororo.future.gencopilot.service.GenSysUserService;
import com.dororo.future.gencopilot.service.TokenService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Resource
    private GenSysUserService genSysUserService;
    @Resource
    private TokenService tokenService;

    @PostMapping("/submit")
    public R login(@RequestBody @Validated GenSysUserSubmitDTO submitDTO) {
        return R.successData(genSysUserService.login(submitDTO)).setMsg("登录成功");
    }

    @PostMapping("/signUp")
    public R signUp(@RequestBody GenSysUserSubmitDTO submitDTO) {
        genSysUserService.signUp(submitDTO);
        return R.success("注册成功");
    }

    /**
     * 根据token检查用户是否登录
     */
    @GetMapping("/check")
    public R token(@RequestParam("token") String token) {
        LoggedGenSysUser loggedGenSysUser = tokenService.cacheableToken(token, null);
        if (loggedGenSysUser == null) {
            return R.error("登陆状态已失效,请重新登陆");
        }
        return R.successData(true);
    }

}
