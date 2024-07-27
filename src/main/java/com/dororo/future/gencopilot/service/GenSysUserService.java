package com.dororo.future.gencopilot.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.dororo.future.gencopilot.domain.GenSysUser;
import com.dororo.future.gencopilot.domain.LoggedGenSysUser;
import com.dororo.future.gencopilot.dto.GenSysUserSubmitDTO;
import com.dororo.future.gencopilot.enums.YesNoEnum;
import com.dororo.future.gencopilot.mapper.GenSysUserMapper;
import com.dororo.future.gencopilot.util.CryptoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GenSysUserService {
    @Resource
    private TokenService tokenService;

    @Autowired
    private GenSysUserMapper genSysUserMapper;

    public LoggedGenSysUser login(GenSysUserSubmitDTO submitDTO) {
        List<GenSysUser> matchList = genSysUserMapper.findByAll(GenSysUser.builder().username(submitDTO.getUsername()).build());
        if (CollectionUtil.isEmpty(matchList)) {
            throw new RuntimeException("用户不存在");
        }
        GenSysUser genSysUser = matchList.get(0);
        // 摘要算法加密
        String coverPassword = CryptoUtils.summaryEncrypt(submitDTO.getPassword());
        if (!coverPassword.equals(genSysUser.getPassword())) {
            throw new RuntimeException("密码错误");
        }
        // 允许登录
        return tokenService.loginAction(genSysUser);
    }

    public void signUp(GenSysUserSubmitDTO submitDTO) {
        List<GenSysUser> matchList = genSysUserMapper.findByAll(GenSysUser.builder().username(submitDTO.getUsername()).build());
        if (CollectionUtil.isNotEmpty(matchList)) {
            throw new RuntimeException("用户已存在");
        }
        // 摘要算法加密
        String coverPassword = CryptoUtils.summaryEncrypt(submitDTO.getPassword());
        GenSysUser genSysUser = GenSysUser.builder()
                .username(submitDTO.getUsername())
                .password(coverPassword)
                .enable(YesNoEnum.YES.code)
                .createTime(DateUtil.now())
                .updateTime(DateUtil.now()).build();
        // 注册成功
        genSysUserMapper.insert(genSysUser);
    }
}
