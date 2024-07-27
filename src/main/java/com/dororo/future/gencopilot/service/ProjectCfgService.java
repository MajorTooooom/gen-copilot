package com.dororo.future.gencopilot.service;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import com.dororo.future.gencopilot.domain.LoggedGenSysUser;
import com.dororo.future.gencopilot.domain.ProjectCfg;
import com.dororo.future.gencopilot.dto.ProjectCfgAddDTO;
import com.dororo.future.gencopilot.dto.ProjectCfgDeleteDTO;
import com.dororo.future.gencopilot.dto.ProjectCfgPageDTO;
import com.dororo.future.gencopilot.dto.ProjectCfgUpdateDTO;
import com.dororo.future.gencopilot.enums.YesNoEnum;
import com.dororo.future.gencopilot.holder.LoginUserContextHolder;
import com.dororo.future.gencopilot.holder.PageContextHolder;
import com.dororo.future.gencopilot.mapper.ProjectCfgMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectCfgService {

    @Autowired
    private ProjectCfgMapper projectCfgMapper;


    public void add(ProjectCfgAddDTO projectCfgAddDTO) {
        LoggedGenSysUser user = LoginUserContextHolder.getUser();
        Assert.notNull(user, "用户未登录");
        ProjectCfg projectCfg = Convert.convert(ProjectCfg.class, projectCfgAddDTO);
        projectCfg.setUserId(user.getId());
        projectCfg.setIsDeleted(YesNoEnum.NO.code + "");
        projectCfg.setCreateTime(DateUtil.now());
        projectCfg.setUpdateTime(DateUtil.now());
        projectCfgMapper.insert(projectCfg);
    }


    public void delete(ProjectCfgDeleteDTO deleteDTO) {
        LoggedGenSysUser user = LoginUserContextHolder.getUser();
        Assert.notNull(user, "用户未登录");
        List<Integer> ids = deleteDTO.getIds();
        Assert.notEmpty(ids, "ids不能为空");
        List<ProjectCfg> list = ids.stream().map(id -> ProjectCfg.builder().id(id).isDeleted(YesNoEnum.YES.code + "").updateTime(DateUtil.now()).build()).collect(Collectors.toList());
        for (ProjectCfg projectCfg : list) {
            projectCfgMapper.updateByPrimaryKeySelective(projectCfg);
        }
    }

    public void update(ProjectCfgUpdateDTO projectCfgUpdateDTO) {
        LoggedGenSysUser user = LoginUserContextHolder.getUser();
        Assert.notNull(user, "用户未登录");
        ProjectCfg projectCfg = Convert.convert(ProjectCfg.class, projectCfgUpdateDTO);
        projectCfg.setUpdateTime(DateUtil.now());
        projectCfgMapper.updateByPrimaryKeySelective(projectCfg);
    }

    public Object elSelectOptions() {
        return null;
    }

    public List<ProjectCfg> page(ProjectCfgPageDTO projectCfgPageDTO) {
        LoggedGenSysUser user = LoginUserContextHolder.getUser();
        Assert.notNull(user, "用户未登录");
        ProjectCfg projectCfg = Convert.convert(ProjectCfg.class, projectCfgPageDTO);
        projectCfg.setUserId(user.getId());
        projectCfg.setIsDeleted(YesNoEnum.NO.code + "");
        PageContextHolder.startPage();
        List<ProjectCfg> list = projectCfgMapper.findByAll(projectCfg);
        return list;
    }
}
