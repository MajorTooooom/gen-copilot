<#include "环境变量辅助.ftl"/>
package ${servicePackage};

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.exception.ExcelCommonException;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.github.pagehelper.PageHelper;
import com.zhien.common.auth.dto.LoginUserDTO;
import com.zhien.common.auth.utils.AuthUserUtils;
import ${domainPackage}.${domainName};
import ${dtoPackage}.${domainName}AddDTO;
import ${dtoPackage}.${domainName}DeleteDTO;
import ${dtoPackage}.${domainName}ExportDTO;
import ${dtoPackage}.${domainName}ImportDTO;
import ${dtoPackage}.${domainName}PageDTO;
import ${dtoPackage}.${domainName}UpdateDTO;
import com.zhien.fac.utils.FieldUtils;
import ${easyExcelListenerPackage}.${domainName}ImportListener;
import com.zhien.fac.enums.YesNoEnum;
import ${utilsPackage}.JavaxValidationUtils;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

<#list javaTypes as javaType>
import ${javaType};
</#list>

import org.springframework.beans.factory.annotation.Autowired;

import ${mapperPackage}.${domainName}Mapper;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ${domainName}Service {
    @Autowired
    private ${domainName}Mapper ${domainName?uncap_first}Mapper;

    public void add(${domainName}AddDTO addDTO) {
        LoginUserDTO currentUser = AuthUserUtils.getCurrentUser();
        Assert.notNull(currentUser, "用户未登录");
        ${domainName} ${domainName?uncap_first} = Convert.convert(${domainName}.class, addDTO);
        // 对象中字符串类型的字段进行trim操作
        ${domainName?uncap_first} = FieldUtils.strTrim(${domainName?uncap_first});
        // `XXXXX`唯一性校验
        Assert.isNull(${domainName?uncap_first}Mapper.selectOne(${domainName}.builder()
        // TODO 改为实际字段
        .XXXXXX(${domainName?uncap_first}.getXXXXXX())
        .isEnabled(YesNoEnum.YES.getCode())
        // TODO 改为实际字段
        .build()), "同名 XXXXXX 已存在");
        // TODO 补全其他业务逻辑
        //
        ${domainName?uncap_first}.setIsEnabled(YesNoEnum.YES.getCode());
        ${domainName?uncap_first}.setCreateUser(currentUser.getLoginName());
        ${domainName?uncap_first}.setCreateTime(new Date());
        ${domainName?uncap_first}.setUpdateUser(currentUser.getLoginName());
        ${domainName?uncap_first}.setUpdateTime(new Date());
        //
        int i = ${domainName?uncap_first}Mapper.insertSelective(${domainName?uncap_first});
        Assert.isTrue(i > 0, "新增失败@INSERT");
    }

    public void delete(${primaryKeyColumnCfg.javaTypeClassName} id) {
        LoginUserDTO user = AuthUserUtils.getCurrentUser();
        Assert.notNull(user, "用户未登录");
        // ${domainName?uncap_first}Mapper.deleteByPrimaryKey(id);// 物理删除
        // 逻辑删除
        ${domainName} updateVo = ${domainName}.builder()
        .${primaryKeyColumnCfg.javaName}(id)
        .isEnabled(YesNoEnum.NO.getCode())
        .updateUser(user.getLoginName())
        .updateTime(new Date())
        .build();
        ${domainName?uncap_first}Mapper.updateByPrimaryKeySelective(updateVo);
    }

    public List delete(${domainName}DeleteDTO deleteDTO) {
        List<JSONObject> result = new ArrayList<>();
        for (${primaryKeyColumnCfg.javaTypeClassName} id : deleteDTO.getIds()) {
            try {
                delete(id);
            } catch (Exception e) {
                result.add(JSONUtil.createObj().put("id", id).put("msg", e.getMessage()));
            }
        }
        return result;
    }

    public void update(${domainName}UpdateDTO updateDTO) {
        LoginUserDTO currentUser = AuthUserUtils.getCurrentUser();
        Assert.notNull(currentUser, "用户未登录");
        ${domainName} ${domainName?uncap_first} = Convert.convert(${domainName}.class, updateDTO);
        // TODO 补全其他业务逻辑
        ${domainName?uncap_first}.setUpdateUser(currentUser.getLoginName());
        ${domainName?uncap_first}.setUpdateTime(new Date());
        ${domainName?uncap_first}Mapper.updateByPrimaryKeySelective(${domainName?uncap_first});
    }

    public ${domainName} selectByPrimaryKey(${primaryKeyColumnCfg.javaTypeClassName} id) {
        return ${domainName?uncap_first}Mapper.selectByPrimaryKey(id);
    }

    public List<${domainName}ExportDTO> conditionalQueryPage(${domainName}PageDTO pageDTO) {
        List<${domainName}ExportDTO> list = ${domainName?uncap_first}Mapper.findAllByPageCondition(pageDTO);
        return list;
    }

    public List<${domainName}ExportDTO> conditionalQueryAllPage(${domainName}PageDTO pageDTO) {
        int pageNum = 1;
        final int pageSize = 1000;
        TimeInterval overtimeProtectionTimer = DateUtil.timer();
        long maxSecond = 30;
        List<${domainName}ExportDTO> result = new ArrayList<>();
        while (true) {
            PageHelper.startPage(pageNum, pageSize);
            List<${domainName}ExportDTO> pageResult = conditionalQueryPage(pageDTO);
            result.addAll(pageResult);
            if (CollectionUtil.isEmpty(pageResult) || pageResult.size() < pageSize) {
                break;
            }
            if (overtimeProtectionTimer.intervalSecond() > maxSecond) {
                StrUtil.format("查询超时[{}/{}]秒，请检查数据量是否过大", overtimeProtectionTimer.intervalSecond(), maxSecond);
                throw new RuntimeException();
            }
            pageNum++;
        }
        return result;
    }

    public List importExcel(MultipartFile[] files) {
        List<${domainName}ImportListener.ImportResultDTO> result = new ArrayList<>();
        for (MultipartFile file : files) {
            ${domainName}ImportListener.ImportResultDTO importResultDTO = new ${domainName}ImportListener.ImportResultDTO();
            try {
                importResultDTO.setFileName(file.getOriginalFilename());
                handleSingleExcel(file, importResultDTO);
            } catch (Exception e) {
                importResultDTO.addMessage(e.getMessage());
            }
            result.add(importResultDTO);
        }
        return result;
    }

    @SneakyThrows
    public void handleSingleExcel(MultipartFile file, ${domainName}ImportListener.ImportResultDTO importResultDTO) {
        // 基于EasyExcel监听器
        ${domainName}ImportListener listener = new ${domainName}ImportListener(importResultDTO);
        ExcelReaderBuilder read = EasyExcel.read(file.getInputStream(), ${domainName}ImportDTO.class, listener);
        ExcelReaderSheetBuilder sheet = null;
        try {
            sheet = read.sheet();
        } catch (NullPointerException e) {
            importResultDTO.addMessage(StrUtil.format("[{}]未找到工作表,请检查文件是否损坏,可尝试保存为一个新文件再导入", file.getOriginalFilename()));
            return;
        } catch (ExcelCommonException ee) {
            importResultDTO.addMessage(StrUtil.format("[{}]文件格式错误,请检查文件格式或另存为", file.getOriginalFilename()));
            return;
        }
        /**
         * 逐行处理,详情见{@link ${domainName}ImportListener#onException}、{@link ${domainName}ImportListener#invoke}等
         */
        sheet.doRead();
    }

    public void handleSingleExcelSingleRow(${domainName}ImportDTO row) {
        // 导入时新增的最终逻辑与页面新增逻辑一致
        ${domainName}AddDTO addDTO = Convert.convert(${domainName}AddDTO.class, row);
        // 手动触发注解校验
        JavaxValidationUtils.verify(addDTO);
        // 走新增逻辑
        add(addDTO);
    }
}