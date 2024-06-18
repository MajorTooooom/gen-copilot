package com.dororo.future.gencopilot.service;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.dororo.future.gencopilot.domain.ConnCfg;
import com.dororo.future.gencopilot.domain.LoggedGenSysUser;
import com.dororo.future.gencopilot.dto.ConnCfgAddDTO;
import com.dororo.future.gencopilot.dto.ConnCfgDeleteDTO;
import com.dororo.future.gencopilot.dto.ConnCfgPageDTO;
import com.dororo.future.gencopilot.dto.common.ConnCfgUpdateDTO;
import com.dororo.future.gencopilot.enums.YesNoEnum;
import com.dororo.future.gencopilot.holder.LoginUserContextHolder;
import com.dororo.future.gencopilot.holder.PageContextHolder;
import com.dororo.future.gencopilot.mapper.ConnCfgMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConnCfgService {

    @Autowired
    private ConnCfgMapper connCfgMapper;

    public void add(ConnCfgAddDTO connCfgAddDTO) {
        ConnCfg connCfg = Convert.convert(ConnCfg.class, connCfgAddDTO);
        Assert.isTrue(StrUtil.startWith(connCfg.getUrl(), "jdbc:mysql://"), "当前仅支持MySQL数据库");
        LoggedGenSysUser user = LoginUserContextHolder.getUser();
        Assert.notNull(user, "用户未登录");
        connCfg.setUserId(user.getId());
        connCfg.setIsDeleted("0");
        connCfg.setCreateTime(DateUtil.now());
        connCfg.setUpdateTime(DateUtil.now());
        connCfgMapper.insertSelective(connCfg);
    }

    public List<ConnCfg> page(ConnCfgPageDTO connCfgPageDTO) {
        LoggedGenSysUser user = LoginUserContextHolder.getUser();
        Assert.notNull(user, "用户未登录");
        ConnCfg connCfg = Convert.convert(ConnCfg.class, connCfgPageDTO);
        connCfg.setUserId(user.getId());
        connCfg.setIsDeleted(YesNoEnum.NO.code + "");
        PageContextHolder.startPage();
        List<ConnCfg> list = connCfgMapper.findByAll(connCfg);
        return list;
    }


    public void update(ConnCfgUpdateDTO connCfgUpdateDTO) {
        LoggedGenSysUser user = LoginUserContextHolder.getUser();
        Assert.notNull(user, "用户未登录");
        //
        ConnCfg dbRow = connCfgMapper.selectByPrimaryKey(connCfgUpdateDTO.getId());
        Assert.notNull(dbRow, "数据库无此记录");
        Assert.isTrue(dbRow.getUserId().equals(user.getId()), "无权操作");
        //
        ConnCfg connCfg = Convert.convert(ConnCfg.class, connCfgUpdateDTO);
        connCfg.setUpdateTime(DateUtil.now());
        connCfgMapper.updateByPrimaryKeySelective(connCfg);
    }

    public void deleteRows(ConnCfgDeleteDTO connCfgDeleteDTO) {
        LoggedGenSysUser user = LoginUserContextHolder.getUser();
        Assert.notNull(user, "用户未登录");
        List<Integer> ids = connCfgDeleteDTO.getIds();
        Assert.notEmpty(ids, "ids不能为空");
        connCfgMapper.updateIsDeletedByIdIn(YesNoEnum.YES.code + "", ids);
    }

    public List<JSONObject> elSelectOptions() {
        LoggedGenSysUser user = LoginUserContextHolder.getUser();
        Assert.notNull(user, "用户未登录");
        List<ConnCfg> connCfgs = connCfgMapper.findByAll(ConnCfg.builder().userId(user.getId()).isDeleted(YesNoEnum.NO.code + "").build());
        List<JSONObject> options = connCfgs.stream().map(cfg -> {
            JSONObject option = JSONUtil.createObj()
                    .putOpt("value", cfg.getId())
                    .putOpt("label", StrUtil.format("（{}）{}", StrUtil.fillBefore(cfg.getId() + "", '0', 3), cfg.getCfgName()))
                    .putOpt("data", cfg);
            return option;
        }).collect(Collectors.toList());
        return options;
    }

    public String exportDictionary(ConnCfg connCfg) {



        return null;
    }
}
