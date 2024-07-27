package com.dororo.future.gencopilot.service;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.system.SystemUtil;
import com.dororo.future.gencopilot.domain.LoggedGenSysUser;
import com.dororo.future.gencopilot.domain.TemplateCfg;
import com.dororo.future.gencopilot.dto.TemplateCfgBatchDeleteReqDTO;
import com.dororo.future.gencopilot.holder.LoginUserContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TemplateCfgService {
    /**
     * 获取模板仓库目录
     */
    public static File getTemplateRepositoryDir() {
        LoggedGenSysUser user = LoginUserContextHolder.getUser();
        Assert.notNull(user, "用户未登录");
        String currentDir = SystemUtil.getUserInfo().getCurrentDir();
        return FileUtil.file(currentDir, "attachments", "template", StrUtil.format("user{}", user.getId()));
    }

    public List<TemplateCfg> page() {
        LoggedGenSysUser user = LoginUserContextHolder.getUser();
        Assert.notNull(user, "用户未登录");
        File templateRepositoryDir = getTemplateRepositoryDir();
        File[] files = FileUtil.ls(FileUtil.getAbsolutePath(templateRepositoryDir));
        //
        List<String> allowedExtNames = ListUtil.toList("ftl", "vm");
        List<TemplateCfg> list = Arrays.stream(files)
                .filter(f -> {
                    String name = FileUtil.getName(f);
                    if (StrUtil.equalsIgnoreCase(name, ".gitkeep")) {
                        return false;
                    }
                    String extName = FileUtil.extName(f);
                    if (!allowedExtNames.contains(extName)) {
                        return false;
                    }
                    return true;
                })
                .map(file -> {
                    TemplateCfg templateCfg = TemplateCfg.builder()
                            .absPath(FileUtil.getAbsolutePath(file))
                            .userId(user.getId())
                            .templateName(FileUtil.getName(file))
                            .expectName(null)
                            .updateTime(DateUtil.format(FileUtil.lastModifiedTime(file), DatePattern.NORM_DATETIME_PATTERN))
                            .build();
                    return templateCfg;
                }).collect(Collectors.toList());


        return list;
    }

    public String upload(MultipartFile[] files) {
        LoggedGenSysUser user = LoginUserContextHolder.getUser();
        Assert.notNull(user, "用户未登录");
        File templateRepositoryDir = getTemplateRepositoryDir();
        //
        List<String> msgList = new ArrayList<>();
        for (MultipartFile multipartFile : files) {
            try {
                int max = 50;
                if (FileUtil.ls(FileUtil.getAbsolutePath(templateRepositoryDir)).length > max) {
                    throw new RuntimeException(StrUtil.format("你已经上传了{}个模板文件，不能再上传了", max));
                }
                File expectFile = FileUtil.file(templateRepositoryDir, multipartFile.getOriginalFilename());
                multipartFile.transferTo(expectFile);
            } catch (IOException e) {
                msgList.add(e.getMessage());
            }
        }
        return msgList.stream().collect(Collectors.joining(";"));
    }

    public void download(String userId, String name, HttpServletResponse response) {

        // 获取当前用户的工作目录
        String currentDir = SystemUtil.getUserInfo().getCurrentDir();

        // 定义文件的存储路径
        File templateRepositoryDir = FileUtil.file(currentDir, "attachments", "template", StrUtil.format("user{}", userId));

        // 解码传入的文件名
        String decodedTemplateName = URLUtil.decode(name);

        // 定位到具体的文件
        File template = FileUtil.file(templateRepositoryDir, decodedTemplateName);

        if (FileUtil.exist(template) && FileUtil.isFile(template)) {
            // 设置响应的MIME类型
            response.setContentType("application/octet-stream");
            // 通知客户端内容将被当作附件处理
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + URLUtil.encode(template.getName()) + "\"");

            // 读取文件并写入到HTTP响应中
            try (BufferedInputStream inputStream = FileUtil.getInputStream(template)) {
                IoUtil.copy(inputStream, response.getOutputStream());
            } catch (IOException e) {
                throw new RuntimeException("Error sending file", e);
            }
        } else {
            // 如果文件不存在或不是文件，返回404状态码
            try {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("File not found: " + decodedTemplateName);
            } catch (IOException e) {
                throw new RuntimeException("Error writing error response", e);
            }
        }


    }

    public void batchDelete(TemplateCfgBatchDeleteReqDTO reqDTO) {
        LoggedGenSysUser user = LoginUserContextHolder.getUser();
        Assert.notNull(user, "用户未登录");
        File templateRepositoryDir = getTemplateRepositoryDir();
        for (String name : reqDTO.getNames()) {
            File template = FileUtil.file(templateRepositoryDir, name);
            // 物理删除
            FileUtil.del(template);
        }
    }


    // 推荐文件名
    private String getExpectName(String name) {
        return null;
    }
}

