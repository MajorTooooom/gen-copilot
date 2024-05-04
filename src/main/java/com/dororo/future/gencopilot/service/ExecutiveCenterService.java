package com.dororo.future.gencopilot.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.system.SystemUtil;
import com.dororo.future.gencopilot.constant.CommonConstant;
import com.dororo.future.gencopilot.domain.*;
import com.dororo.future.gencopilot.dto.ConventionOverConfigurationReqDTO;
import com.dororo.future.gencopilot.dto.RenderReqDTO;
import com.dororo.future.gencopilot.dto.TemplateEnvDTO;
import com.dororo.future.gencopilot.enums.RenderModeEnum;
import com.dororo.future.gencopilot.enums.YesNoEnum;
import com.dororo.future.gencopilot.holder.LoginUserContextHolder;
import com.dororo.future.gencopilot.mapper.ColumnCfgMapper;
import com.dororo.future.gencopilot.mapper.GenSysUserMapper;
import com.dororo.future.gencopilot.mapper.ProjectCfgMapper;
import com.dororo.future.gencopilot.mapper.TableCfgMapper;
import com.dororo.future.gencopilot.util.FmUtils;
import com.dororo.future.gencopilot.vo.ConventionOverConfigurationResVo;
import com.dororo.future.gencopilot.vo.RenderResVo;
import com.dororo.future.gencopilot.vo.UniteExecutiveOptionsResVo;
import lombok.SneakyThrows;
import net.lingala.zip4j.ZipFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static com.sun.jmx.snmp.defaults.DefaultPaths.getTmpDir;

@Service
public class ExecutiveCenterService {
    @Autowired
    private TableCfgMapper tableCfgMapper;
    @Autowired
    private ProjectCfgMapper projectCfgMapper;
    @Autowired
    private TemplateCfgService templateCfgService;
    @Autowired
    private ColumnCfgMapper columnCfgMapper;
    @Autowired
    private GenSysUserMapper genSysUserMapper;


    public UniteExecutiveOptionsResVo getUniteExecutiveOptions() {
        LoggedGenSysUser user = LoginUserContextHolder.getUser();
        Assert.notNull(user, "用户未登录");
        UniteExecutiveOptionsResVo resVo = UniteExecutiveOptionsResVo.builder().tableCfgOptions(getTableCfgOptions()).projectCfgOptions(getProjectCfgOptions())
                // .templateTableData(getTemplateTableData())
                .build();
        return resVo;
    }

    private List<JSONObject> getTableCfgOptions() {
        List<TableCfg> list = tableCfgMapper.findByAll(TableCfg.builder().userId(LoginUserContextHolder.getUser().getId()).isDeleted(YesNoEnum.NO.code + "").build());
        // 按照更新时间倒序
        list.sort((o1, o2) -> o2.getUpdateTime().compareTo(o1.getUpdateTime()));
        List<JSONObject> options = list.stream().map(rc -> {
            JSONObject option = JSONUtil.createObj().putOpt("label", StrUtil.format("({}) {}", rc.getId(), rc.getTableCfgName())).putOpt("value", rc.getId());
            return option;
        }).collect(Collectors.toList());
        return options;
    }

    private List<JSONObject> getProjectCfgOptions() {
        List<ProjectCfg> list = projectCfgMapper.findByAll(ProjectCfg.builder().userId(LoginUserContextHolder.getUser().getId()).isDeleted(YesNoEnum.NO.code + "").build());
        // 按照更新时间倒序
        list.sort((o1, o2) -> o2.getUpdateTime().compareTo(o1.getUpdateTime()));
        List<JSONObject> options = list.stream().map(rc -> {
            JSONObject option = JSONUtil.createObj().putOpt("label", StrUtil.format("({}) {}", rc.getId(), rc.getProjectCfgName())).putOpt("value", rc.getId());
            return option;
        }).collect(Collectors.toList());
        return options;
    }

    private List<TemplateCfg> getTemplateTableData() {
        List<TemplateCfg> page = templateCfgService.page();
        return page;
    }

    public Object getConventionOverConfiguration(ConventionOverConfigurationReqDTO reqDTO) {
        TableCfg tableCfg = tableCfgMapper.selectByPrimaryKey(reqDTO.getTableCfgId());
        Assert.notNull(tableCfg, "表配置不存在");
        ProjectCfg projectCfg = projectCfgMapper.selectByPrimaryKey(reqDTO.getProjectCfgId());
        Assert.notNull(projectCfg, "项目配置不存在");
        //
        ConventionOverConfigurationResVo resVo = ConventionOverConfigurationResVo.builder().build();
        // 模板文件
        List<TemplateCfg> templateTableData = getTemplateTableData();
        for (TemplateCfg templateCfg : templateTableData) {
            templateCfg.setUse(true);
            // 不带扩展名文件名
            String mainName = FileUtil.mainName(templateCfg.getAbsPath());
            if (StrUtil.equalsIgnoreCase(mainName, "controller")) {
                templateCfg.setExpectName(StrUtil.format("{}Controller.java", tableCfg.getDomainName()));
                templateCfg.setDestDirAbsPath(projectCfg.getSourceCodeAbsPath());
                templateCfg.setDestPackage(projectCfg.getControllerPackage());
            } else if (StrUtil.equalsIgnoreCase(mainName, "domain") || StrUtil.equalsIgnoreCase(mainName, "entity")) {
                templateCfg.setExpectName(StrUtil.format("{}.java", tableCfg.getDomainName()));
                templateCfg.setDestDirAbsPath(projectCfg.getSourceCodeAbsPath());
                templateCfg.setDestPackage(projectCfg.getDomainPackage());
            } else if (StrUtil.equalsIgnoreCase(mainName, "domainAddDTO") || StrUtil.equalsIgnoreCase(mainName, "entityAddDTO")) {
                templateCfg.setExpectName(StrUtil.format("{}AddDTO.java", tableCfg.getDomainName()));
                templateCfg.setDestDirAbsPath(projectCfg.getSourceCodeAbsPath());
                templateCfg.setDestPackage(projectCfg.getDtoPackage());
            } else if (StrUtil.equalsIgnoreCase(mainName, "domainExportDTO") || StrUtil.equalsIgnoreCase(mainName, "entityExportDTO")) {
                templateCfg.setExpectName(StrUtil.format("{}ExportDTO.java", tableCfg.getDomainName()));
                templateCfg.setDestDirAbsPath(projectCfg.getSourceCodeAbsPath());
                templateCfg.setDestPackage(projectCfg.getDtoPackage());
            } else if (StrUtil.equalsIgnoreCase(mainName, "domainImportDTO") || StrUtil.equalsIgnoreCase(mainName, "entityImportDTO")) {
                templateCfg.setExpectName(StrUtil.format("{}ImportDTO.java", tableCfg.getDomainName()));
                templateCfg.setDestDirAbsPath(projectCfg.getSourceCodeAbsPath());
                templateCfg.setDestPackage(projectCfg.getDtoPackage());
            } else if (StrUtil.equalsIgnoreCase(mainName, "domainPageDTO") || StrUtil.equalsIgnoreCase(mainName, "entityPageDTO")) {
                templateCfg.setExpectName(StrUtil.format("{}PageDTO.java", tableCfg.getDomainName()));
                templateCfg.setDestDirAbsPath(projectCfg.getSourceCodeAbsPath());
                templateCfg.setDestPackage(projectCfg.getDtoPackage());
            } else if (StrUtil.equalsIgnoreCase(mainName, "domainUpdateDTO") || StrUtil.equalsIgnoreCase(mainName, "entityUpdateDTO")) {
                templateCfg.setExpectName(StrUtil.format("{}UpdateDTO.java", tableCfg.getDomainName()));
                templateCfg.setDestDirAbsPath(projectCfg.getSourceCodeAbsPath());
                templateCfg.setDestPackage(projectCfg.getDtoPackage());
            } else if (StrUtil.equalsIgnoreCase(mainName, "easyExcelListener")) {
                templateCfg.setExpectName(StrUtil.format("{}ImportListener.java", tableCfg.getDomainName()));
                templateCfg.setDestDirAbsPath(projectCfg.getSourceCodeAbsPath());
                templateCfg.setDestPackage(projectCfg.getEasyExcelListenerPackage());
            } else if (StrUtil.equalsIgnoreCase(mainName, "mapper")) {
                templateCfg.setExpectName(StrUtil.format("{}Mapper.java", tableCfg.getDomainName()));
                templateCfg.setDestDirAbsPath(projectCfg.getSourceCodeAbsPath());
                templateCfg.setDestPackage(projectCfg.getMapperPackage());
            } else if (StrUtil.equalsIgnoreCase(mainName, "mapperXml")) {
                templateCfg.setExpectName(StrUtil.format("{}Mapper.xml", tableCfg.getDomainName()));
                templateCfg.setDestDirAbsPath(projectCfg.getResourceAbsPath());
                templateCfg.setDestPackage(projectCfg.getMapperXmlPackage());
            } else if (StrUtil.equalsIgnoreCase(mainName, "service")) {
                templateCfg.setExpectName(StrUtil.format("{}Service.java", tableCfg.getDomainName()));
                templateCfg.setDestDirAbsPath(projectCfg.getSourceCodeAbsPath());
                templateCfg.setDestPackage(projectCfg.getServicePackage());
            } else if (StrUtil.endWithIgnoreCase(mainName, "vo")) {
                String upperFirst = StrUtil.upperFirst(mainName);
                String aCase = StrUtil.removeSuffixIgnoreCase(upperFirst, "VO");
                templateCfg.setExpectName(StrUtil.format("{}{}VO.java", tableCfg.getDomainName(), aCase));
                templateCfg.setDestDirAbsPath(projectCfg.getSourceCodeAbsPath());
                templateCfg.setDestPackage(projectCfg.getVoPackage());
            } else {
            }
        }
        // 设置虚拟ID
        for (int i = 0; i < templateTableData.size(); i++) {
            templateTableData.get(i).setId(i + 1);
        }
        resVo.setList(templateTableData);
        return resVo;
    }

    private TemplateEnvDTO getTemplateEnv(RenderReqDTO reqDTO) {
        LoggedGenSysUser user = LoginUserContextHolder.getUser();
        Assert.notNull(user, "用户未登录");
        //
        TableCfg tableCfg = tableCfgMapper.selectByPrimaryKey(reqDTO.getTableCfgId());
        Assert.notNull(tableCfg, "表配置不存在");
        // 字段
        List<ColumnCfg> columnCfgs = columnCfgMapper.findByAll(ColumnCfg.builder().userId(user.getId()).tableCfgId(reqDTO.getTableCfgId()).build());
        //
        ProjectCfg projectCfg = projectCfgMapper.selectByPrimaryKey(reqDTO.getProjectCfgId());
        Assert.notNull(projectCfg, "项目配置不存在");
        // 反引号
        for (ColumnCfg columnCfg : columnCfgs) {
            List<String> mysqlKeywords = CommonConstant.MYSQL_KEYWORDS;
            boolean aCase = StrUtil.equalsAnyIgnoreCase(columnCfg.getColumnName(), mysqlKeywords.toArray(new String[0]));
            columnCfg.setRequireBackQuote(aCase);
        }
        // TODO 封装
        TemplateEnvDTO templateEnv = new TemplateEnvDTO();
        // 拷贝相同字段
        BeanUtil.copyProperties(tableCfg, templateEnv);
        BeanUtil.copyProperties(projectCfg, templateEnv);
        // 字段列表
        // 注解注释的文字不能出现换行符,否则将导致报错
        templateEnv.setColumns(fixColumnCfgComment(columnCfgs));
        // 时间格式化
        templateEnv.setDateTime(DateUtil.now());
        // 作者
        templateEnv.setAuthor(genSysUserMapper.selectByPrimaryKey(tableCfg.getUserId()).getUsername());
        //
        // 需要记录全部的javaType,用于生成import语句
        List<String> list = CollectionUtil.toList("java.lang.String", "java.lang.Integer");// 不需要写import的类型
        List<String> javaTypes = new ArrayList<>(columnCfgs.stream().map(c -> c.getJavaType()).filter(StrUtil::isNotBlank).filter(jt -> !(StrUtil.equalsAny(jt, list.toArray(new String[0])))).collect(Collectors.toSet()));
        templateEnv.setJavaTypes(javaTypes);
        //
        // 模拟MCHP的时间类型字段,生成的时间格式形如:`Mon Apr 01 15:23:29 CST 2024`
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        String formattedDate = sdf.format(DateUtil.date());
        templateEnv.setMchpFormatDate(formattedDate);
        //
        return templateEnv;
    }

    private List<ColumnCfg> fixColumnCfgComment(List<ColumnCfg> columnCfgs) {
        // 修复字段注释中的换行符
        for (ColumnCfg columnCfg : columnCfgs) {
            String columnSwaggerComment = columnCfg.getColumnSwaggerComment();
            if (StrUtil.isNotBlank(columnSwaggerComment)) {
                // 替换所有的换行符,需要考虑不同操作系统
                columnSwaggerComment = StrUtil.replace(columnSwaggerComment, StrUtil.CRLF, StrUtil.SPACE);
                columnSwaggerComment = StrUtil.replace(columnSwaggerComment, StrUtil.LF, StrUtil.SPACE);
                columnCfg.setColumnSwaggerComment(columnSwaggerComment);
            }
            String columnValidationComment = columnCfg.getColumnValidationComment();
            if (StrUtil.isNotBlank(columnValidationComment)) {
                // 替换所有的换行符,需要考虑不同操作系统
                columnValidationComment = StrUtil.replace(columnValidationComment, StrUtil.CRLF, StrUtil.SPACE);
                columnValidationComment = StrUtil.replace(columnValidationComment, StrUtil.LF, StrUtil.SPACE);
                columnCfg.setColumnValidationComment(columnValidationComment);
            }
        }
        return columnCfgs;
    }

    public RenderResVo renderDispatch(RenderReqDTO reqDTO) {
        // 返回结果
        RenderResVo resVo = new RenderResVo();
        // 模板环境(公共)
        TemplateEnvDTO envDTO = getTemplateEnv(reqDTO);
        Integer mode = reqDTO.getMode();
        // 如果是打包下载模式,则创建临时目录作为项目目录
        String tmpDirName = DateUtil.format(DateUtil.date(), "yyyyMMddHHmmssSSS");
        resVo.setTempDirName(tmpDirName);
        String tmpDirAbsPath = FileUtil.getAbsolutePath(FileUtil.file(SystemUtil.getUserInfo().getCurrentDir(), "attachments", "tmp", tmpDirName));
        //
        List<TemplateCfg> templateCfgs = reqDTO.getList();
        int successCount = 0;
        int failCount = 0;
        for (int i = 0; i < templateCfgs.size(); i++) {
            TemplateCfg templateCfg = templateCfgs.get(i);
            // 如果是打包下载模式,则将前置路径替换为临时目录
            if (RenderModeEnum.DOWNLOAD.equals(RenderModeEnum.getByCode(mode))) {
                templateCfg.setDestDirAbsPath(tmpDirAbsPath);
            }
            templateCfg.setMode(mode);
            templateCfg.setEnv(envDTO);
            templateCfg.setRenderSuccess(null);
            try {
                // TODO 渲染分发
                engineDispatch(templateCfg);
                templateCfg.setRenderSuccess(true);
                successCount++;
            } catch (Exception e) {
                templateCfg.setRenderSuccess(false);
                templateCfg.setRenderException(e.getMessage());
                failCount++;
            }
        }
        // 简化list
        List<TemplateCfg> simpleList = templateCfgs.stream().map(cfg -> TemplateCfg.builder().id(cfg.getId()).renderSuccess(cfg.getRenderSuccess()).renderException(cfg.getRenderException()).build()).collect(Collectors.toList());
        resVo.setList(simpleList);
        resVo.setSuccessCount(successCount);
        resVo.setFailCount(failCount);
        return resVo;
    }

    // 根据模板类型选择不同的引擎
    private void engineDispatch(TemplateCfg templateCfg) {
        String extName = FileUtil.extName(templateCfg.getAbsPath());
        if (StrUtil.equalsIgnoreCase(extName, "ftl")) {
            // TODO Freemarker
            handleByFreemarker(templateCfg);
        } else if (StrUtil.equalsIgnoreCase(extName, "vm")) {
            // TODO Velocity
            handleByVelocity(templateCfg);
        } else {
            throw new RuntimeException(StrUtil.format("暂不支持模板类型:[{}]", extName));
        }
    }

    private void handleByFreemarker(TemplateCfg templateCfg) {
        Assert.notBlank(templateCfg.getDestDirAbsPath(), "目标目录不能为空");
        File destDir = FileUtil.file(templateCfg.getDestDirAbsPath());
        // 如果还配置了包目录,则进行拼接
        if (StrUtil.isNotBlank(templateCfg.getDestPackage())) {
            List<String> pkgs = StrUtil.split(templateCfg.getDestPackage(), StrUtil.DOT);
            for (int i = 0; i < pkgs.size(); i++) {
                destDir = FileUtil.file(destDir, pkgs.get(i));
            }
        }
        File destArtifact = FileUtil.file(destDir, templateCfg.getExpectName());
        // 从模板文件渲染到目标文件
        FmUtils.fileToFile(FileUtil.file(templateCfg.getAbsPath()), destArtifact, templateCfg.getEnv());
    }

    private void handleByVelocity(TemplateCfg templateCfg) {
        throw new RuntimeException("Velocity引擎 适配中....");
    }

    @SneakyThrows
    public void download(String name, HttpServletResponse response) {
        File tmpDir = FileUtil.file(SystemUtil.getUserInfo().getCurrentDir(), "attachments", "tmp", name);
        File zipFile = FileUtil.file(SystemUtil.getUserInfo().getCurrentDir(), "attachments", "tmp", name + ".zip");
        ZipFile zip = new ZipFile(zipFile);
        zip.addFolder(tmpDir);
        response.setContentType("application/zip");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + name + ".zip");
        try (FileInputStream fis = new FileInputStream(zipFile)) {
            StreamUtils.copy(fis, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error while sending ZIP file");
        }
    }
}
