package com.dororo.future.gencopilot.service;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.system.SystemUtil;
import cn.smallbun.screw.core.Configuration;
import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.engine.EngineFileType;
import cn.smallbun.screw.core.engine.EngineTemplateType;
import cn.smallbun.screw.core.execute.DocumentationExecute;
import cn.smallbun.screw.core.process.ProcessConfig;
import com.alibaba.druid.pool.DruidDataSource;
import com.dororo.future.gencopilot.config.DruidDataSourcePool;
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
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;
import java.io.File;
import java.util.ArrayList;
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

    public Long exportDictionary(ConnCfg connCfg) {
        // 从URL中将数据库名称提取出来
        String url = connCfg.getUrl();
        String dbName = StrUtil.subAfter(url, "/", false);
        dbName = StrUtil.subBefore(dbName, "?", false);


        // 生成临时文件并打包为zip
        String currentDir = SystemUtil.getUserInfo().getCurrentDir();
        // 雪花算法生成唯一ID
        long id = IdUtil.getSnowflakeNextId();
        File dbDictDir = FileUtil.file(currentDir, "attachments/tmp/db-dict", Convert.toStr(id));
        FileUtil.mkdir(dbDictDir);
        // 数据源
        DruidDataSource ds = DruidDataSourcePool.getOrDefault(connCfg.getUrl(), connCfg.getUsername(), connCfg.getPassword());
        // 三种文档类型都生成
        List<EngineFileType> typeList = ListUtil.toList(EngineFileType.HTML, EngineFileType.WORD, EngineFileType.MD);
        for (EngineFileType engineFileType : typeList) {
            // 生成配置
            EngineConfig engineConfig = EngineConfig.builder()
                    // 生成文件路径
                    .fileOutputDir(FileUtil.getAbsolutePath(dbDictDir))
                    // 打开目录
                    .openOutputDir(false)
                    // 文件类型
                    .fileType(engineFileType)
                    // 生成模板实现
                    .produceType(EngineTemplateType.freemarker)
                    // 自定义文件名称
                    .fileName(StrUtil.format("数据库字典_{}", dbName))
                    .build();
            // 忽略表
            ArrayList<String> ignoreTableName = new ArrayList<>();
            ignoreTableName.add("test_user_dev");
            ignoreTableName.add("test_group_dev");
            // 忽略表前缀
            ArrayList<String> ignorePrefix = new ArrayList<>();
            ignorePrefix.add("test_dev_");
            // 忽略表后缀
            ArrayList<String> ignoreSuffix = new ArrayList<>();
            // ignoreSuffix.add("_test");
            ProcessConfig processConfig = ProcessConfig.builder()
                    // 指定生成逻辑、当存在指定表、指定表前缀、指定表后缀时，将生成指定表，其余表不生成、并跳过忽略表配置
                    // 根据名称指定表生成
                    .designatedTableName(new ArrayList<>())
                    // 根据表前缀生成
                    .designatedTablePrefix(new ArrayList<>())
                    // 根据表后缀生成
                    .designatedTableSuffix(new ArrayList<>())
                    // 忽略表名
                    .ignoreTableName(ignoreTableName)
                    // 忽略表前缀
                    .ignoreTablePrefix(ignorePrefix)
                    // 忽略表后缀
                    .ignoreTableSuffix(ignoreSuffix).build();
            // 配置
            Configuration config = Configuration.builder()
                    // 版本
                    .version("1.0.0")
                    // 描述
                    .description("数据库设计文档生成")
                    // 数据源
                    .dataSource(ds)
                    // 生成配置
                    .engineConfig(engineConfig)
                    // 生成配置
                    .produceConfig(processConfig).build();
            // 执行生成
            new DocumentationExecute(config).execute();
        }
        return id;
    }
}
