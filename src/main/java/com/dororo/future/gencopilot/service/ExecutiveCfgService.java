package com.dororo.future.gencopilot.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.dororo.future.gencopilot.domain.*;
import com.dororo.future.gencopilot.dto.RenderReqDTO;
import com.dororo.future.gencopilot.holder.LoginUserContextHolder;
import com.dororo.future.gencopilot.mapper.ExecutiveCfgMapper;
import com.dororo.future.gencopilot.mapper.ProjectCfgMapper;
import com.dororo.future.gencopilot.mapper.TableCfgMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 请输入类描述
 *
 * @author Dororo
 * @date 2024-05-15 16:40
 */
@Service
public class ExecutiveCfgService {

    @Autowired
    private ExecutiveCfgMapper executiveCfgMapper;
    @Autowired
    private TableCfgMapper tableCfgMapper;
    @Autowired
    private ProjectCfgMapper projectCfgMapper;

    /**
     * 保存
     */
    public void smartSave(Integer userId, RenderReqDTO reqDTO) {
        Assert.notNull(userId, "userId不能为空");
        Assert.notNull(reqDTO, "reqDTO不能为空");
        // 记录时不关注模式
        reqDTO.setMode(null);
        // 清空上次渲染结果
        List<TemplateCfg> afterList = reqDTO.getList().stream().map(templateCfg -> {
            templateCfg.setRenderSuccess(null);
            templateCfg.setRenderException(null);
            return templateCfg;
        }).collect(Collectors.toList());
        reqDTO.setList(afterList);

        // 查询是否已存在
        ExecutiveCfg searchVo = ExecutiveCfg.builder()
                .userId(userId)
                .cfgData(JSONUtil.toJsonStr(reqDTO))
                .build();
        ExecutiveCfg executiveCfg = executiveCfgMapper.findOneByAll(searchVo);
        if (executiveCfg != null) {
            // 相同的配置不重复保存, 更新时间
            executiveCfgMapper.updateByPrimaryKeySelective(ExecutiveCfg.builder().id(executiveCfg.getId()).updateTime(DateUtil.now()).build());
        } else {
            // 保存
            executiveCfgMapper.insert(ExecutiveCfg.builder()
                    .userId(userId)
                    .cfgData(JSONUtil.toJsonStr(reqDTO))
                    .createTime(DateUtil.now())
                    .updateTime(DateUtil.now())
                    .build());
        }
        // 如果记录数过多,删除旧记录
        List<ExecutiveCfg> matchList = executiveCfgMapper.findByAll(ExecutiveCfg.builder().userId(userId).build());
        // 超过N条记录,删除最早的记录
        int maxCount = 10;
        if (matchList.size() > maxCount) {
            // ID倒序排序
            List<ExecutiveCfg> after = matchList.stream().sorted(((o1, o2) -> o2.getId().compareTo(o1.getId()))).collect(Collectors.toList());
            for (int i = 0; i < after.size(); i++) {
                if (i > (maxCount - 1)) {
                    executiveCfgMapper.deleteByPrimaryKey(after.get(i).getId());
                }
            }
        }
    }

    public List<ExecutiveCfg> getHistoryCfgs() {
        LoggedGenSysUser user = LoginUserContextHolder.getUser();
        Assert.notNull(user, "用户未登录");
        List<ExecutiveCfg> executiveCfgs = executiveCfgMapper.findByAll(ExecutiveCfg.builder().userId(user.getId()).build());
        for (ExecutiveCfg executiveCfg : executiveCfgs) {
            String cfgData = executiveCfg.getCfgData();
            RenderReqDTO reqDTO = JSONUtil.toBean(cfgData, RenderReqDTO.class);
            TableCfg tableCfg = tableCfgMapper.selectByPrimaryKey(reqDTO.getTableCfgId());
            ProjectCfg projectCfg = projectCfgMapper.selectByPrimaryKey(reqDTO.getProjectCfgId());
            String msg = StrUtil.format("{}    {}", tableCfg.getTableCfgName(), projectCfg.getProjectCfgName());
            executiveCfg.setCfgData(msg);
        }
        // ID倒序排序
        executiveCfgs = executiveCfgs.stream().sorted((o1, o2) -> o2.getId().compareTo(o1.getId())).collect(Collectors.toList());
        return executiveCfgs;
    }

    public RenderReqDTO useHistoryCfg(Integer id) {
        ExecutiveCfg executiveCfg = executiveCfgMapper.selectByPrimaryKey(id);
        String cfgData = executiveCfg.getCfgData();
        RenderReqDTO reqDTO = JSONUtil.toBean(cfgData, RenderReqDTO.class);
        return reqDTO;
    }
}