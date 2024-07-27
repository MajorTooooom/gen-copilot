package com.dororo.future.gencopilot.service;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.dororo.future.gencopilot.config.DruidDataSourcePool;
import com.dororo.future.gencopilot.constant.CacheConstants;
import com.dororo.future.gencopilot.domain.ColumnCfg;
import com.dororo.future.gencopilot.domain.ColumnMetadata;
import com.dororo.future.gencopilot.domain.LoggedGenSysUser;
import com.dororo.future.gencopilot.domain.TableCfg;
import com.dororo.future.gencopilot.dto.*;
import com.dororo.future.gencopilot.dto.common.R;
import com.dororo.future.gencopilot.enums.YesNoEnum;
import com.dororo.future.gencopilot.holder.LoginUserContextHolder;
import com.dororo.future.gencopilot.holder.PageContextHolder;
import com.dororo.future.gencopilot.mapper.ColumnCfgMapper;
import com.dororo.future.gencopilot.mapper.TableCfgMapper;
import com.dororo.future.gencopilot.vo.DbTableAndColumnResVo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TableCfgService {
    @Autowired
    private TableCfgService cacheThisVo;
    @Autowired
    private TableCfgMapper tableCfgMapper;
    @Autowired
    private ColumnCfgMapper columnCfgMapper;

    @Transactional(rollbackFor = Exception.class)
    public void add(TableCfgAddDTO addDTO) {
        LoggedGenSysUser user = LoginUserContextHolder.getUser();
        Assert.notNull(user, "用户未登录");
        TableCfg tableCfg = Convert.convert(TableCfg.class, addDTO);
        tableCfg.setUserId(user.getId());
        tableCfg.setIsDeleted(YesNoEnum.NO.code + "");
        tableCfg.setCreateTime(DateUtil.now());
        tableCfg.setUpdateTime(DateUtil.now());
        tableCfgMapper.insert(tableCfg);
        //
        Integer tableCfgId = tableCfg.getId();
        Assert.notNull(tableCfgId, "插入失败");
        List<ColumnCfg> columnCfgs = addDTO.getColumnCfgs();
        for (ColumnCfg columnCfg : columnCfgs) {
            columnCfg.setId(null);
            columnCfg.setUserId(user.getId());
            columnCfg.setTableCfgId(tableCfgId);
            columnCfgMapper.insert(columnCfg);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(TableCfgUpdateDTO updateDTO) {
        LoggedGenSysUser user = LoginUserContextHolder.getUser();
        Assert.notNull(user, "用户未登录");
        Assert.isTrue(updateDTO.getUserId() == user.getId(), "无权限");
        TableCfg tableCfg = Convert.convert(TableCfg.class, updateDTO);
        tableCfg.setUpdateTime(DateUtil.now());
        tableCfgMapper.updateByPrimaryKeySelective(tableCfg);
        //
        columnCfgMapper.deleteByUserIdAndTableCfgId(user.getId(), tableCfg.getId());
        List<ColumnCfg> columnCfgs = updateDTO.getColumnCfgs();
        for (ColumnCfg columnCfg : columnCfgs) {
            columnCfg.setId(null);
            columnCfg.setUserId(user.getId());
            columnCfg.setTableCfgId(tableCfg.getId());
            columnCfgMapper.insert(columnCfg);
        }
    }

    public List<TableCfg> page(TableCfgPageDTO tableCfgPageDTO) {
        LoggedGenSysUser user = LoginUserContextHolder.getUser();
        Assert.notNull(user, "用户未登录");
        TableCfg tableCfg = Convert.convert(TableCfg.class, tableCfgPageDTO);
        tableCfg.setUserId(user.getId());
        tableCfg.setIsDeleted(YesNoEnum.NO.code + "");
        PageContextHolder.startPage();
        List<TableCfg> list = tableCfgMapper.findByAll(tableCfg);
        return list;
    }

    @Cacheable(cacheNames = CacheConstants.TABLE_POOL_OPTIONS, key = "#cacheKey", unless = "#result == null")
    public R cacheableTablePoolOptions(String cacheKey, ConnCfgTablePoolDTO cfgTablePoolDTO) {
        return cacheThisVo.getTablePoolOptions(cfgTablePoolDTO);
    }

    @CachePut(cacheNames = CacheConstants.TABLE_POOL_OPTIONS, key = "#cacheKey", unless = "#result == null")
    public R cachePutTablePoolOptions(String cacheKey, ConnCfgTablePoolDTO cfgTablePoolDTO) {
        return cacheThisVo.getTablePoolOptions(cfgTablePoolDTO);
    }

    private R getTablePoolOptions(ConnCfgTablePoolDTO cfgTablePoolDTO) {
        DruidDataSource ds = DruidDataSourcePool.getOrDefault(cfgTablePoolDTO.getUrl(), cfgTablePoolDTO.getUsername(), cfgTablePoolDTO.getPassword());
        // 查询表列表
        String sql = "SELECT TABLE_SCHEMA AS `tableSchema`, TABLE_NAME AS `tableName` FROM information_schema.`TABLES` WHERE LOWER(TABLE_SCHEMA) NOT IN ( 'mysql', 'information_schema', 'performance_schema', 'sys' )";
        List<Entity> query = null;
        try {
            query = Db.use(ds).query(sql);
        } catch (Exception e) {
            throw new RuntimeException("查询失败", e);
        }
        Map<String, List<Entity>> groupMap = query.stream().collect(Collectors.groupingBy(e -> e.getStr("tableSchema")));
        List<JSONObject> options = new ArrayList<>();
        groupMap.forEach((tableSchema, entities) -> {
            JSONObject option = new JSONObject();
            option.put("label", tableSchema);
            option.put("value", tableSchema);
            List<JSONObject> children = new ArrayList<>();
            entities.forEach(e -> {
                JSONObject child = new JSONObject();
                child.put("label", e.getStr("tableName"));
                child.put("value", e.getStr("tableName"));
                children.add(child);
            });
            option.put("children", children);
            options.add(option);
        });

        R res = R.successData(options);
        res.setMsg(StrUtil.format("数据源:库[{}],表[{}]", groupMap.size(), query.size()));
        return res;
    }

    @SneakyThrows
    public DbTableAndColumnResVo getDbTableAndColumn(DbTableAndColumnReqDTO reqDTO) {
        DbTableAndColumnResVo resVo = new DbTableAndColumnResVo();
        // 实体类名
        String domainName = StrUtil.upperFirst(StrUtil.toCamelCase(reqDTO.getTableName()));
        resVo.setDomainName(domainName);

        // 实体类中文名
        DruidDataSource ds = DruidDataSourcePool.getOrDefault(reqDTO.getUrl(), reqDTO.getUsername(), reqDTO.getPassword());
        String sql = StrUtil.format("SELECT TABLE_COMMENT AS 'tableComment' FROM information_schema.`TABLES` WHERE TABLE_SCHEMA = '{}' AND TABLE_NAME = '{}'", reqDTO.getTableSchema(), reqDTO.getTableName());
        Entity entity = Db.use(ds).queryOne(sql);
        String tableComment = entity.getStr("tableComment");
        String domainZnName = StrUtil.isBlank(tableComment) ? domainName : StrUtil.removeSuffix(tableComment, "表");
        resVo.setDomainZnName(domainZnName);

        // 推荐的配置名称
        resVo.setTableCfgName(StrUtil.format("[{}]库[{}]表_配置_V1", reqDTO.getTableSchema(), reqDTO.getTableName()));

        // 查询表字段信息
        String sqlForColumn = StrUtil.format("SELECT * FROM information_schema.`COLUMNS` WHERE TABLE_SCHEMA = '{}' AND TABLE_NAME = '{}'", reqDTO.getTableSchema(), reqDTO.getTableName());
        List<Entity> query = Db.use(ds).query(sqlForColumn);
        List<ColumnCfg> columnCfgs = query.stream().map(e -> ColumnMetadata.specialConvert(e, ColumnCfg.class)).collect(Collectors.toList());// 解决字段转换
        List<ColumnCfg> sortedList = columnCfgs.stream().sorted(Comparator.comparing(cfg -> Convert.toInt(cfg.getOrdinalPosition(), 0))).collect(Collectors.toList());// 排序
        // TODO 填充扩展信息
        for (ColumnCfg columnCfg : sortedList) {
            // java字段名:列名转小写驼峰
            columnCfg.setJavaName(StrUtil.lowerFirst(StrUtil.toCamelCase(columnCfg.getColumnName())));
            // 根据列字段类型推荐java字段类型
            columnCfg.setJavaType(getRecommendedJavaType(columnCfg.getColumnType()));
            // 根据列字段类型推荐java字段类型全类名
            columnCfg.setJavaTypeClassName(getRecommendedJavaTypeClassName(columnCfg.getJavaType()));
            // Swagger注释 & validation注释 预设
            columnCfg.setColumnSwaggerComment(Optional.ofNullable(columnCfg.getColumnComment()).filter(StrUtil::isNotBlank).orElse(columnCfg.getJavaName()));
            columnCfg.setColumnValidationComment(Optional.ofNullable(columnCfg.getColumnComment()).filter(StrUtil::isNotBlank).orElse(columnCfg.getJavaName()));
            // jdbcType预设
            columnCfg.setJdbcType(getRecommendedJdbcType(columnCfg.getColumnType()));
        }
        resVo.setColumnCfgs(sortedList);

        // 其他信息
        resVo.setConnUrl(reqDTO.getUrl());
        resVo.setConnUsername(reqDTO.getUsername());
        resVo.setConnPassword(reqDTO.getPassword());
        resVo.setTableSchema(reqDTO.getTableSchema());
        resVo.setTableName(reqDTO.getTableName());


        return resVo;
    }

    private String getRecommendedJdbcType(String observedColumnType) {
        String string = ResourceUtil.readUtf8Str("mapperJdbcTypeConfig.json");
        JSONArray jsonArray = JSONUtil.parseArray(string);
        for (Object object : jsonArray) {
            JSONObject jsonObject = JSONUtil.parseObj(object);
            String columnType = jsonObject.getStr("columnType");
            String jdbcType = jsonObject.getStr("jdbcType");
            if (Pattern.matches(columnType, observedColumnType)) {
                return jdbcType;
            }
        }
        return null;
    }

    private String getRecommendedJavaType(String observedColumnType) {
        String string = ResourceUtil.readUtf8Str("mapperJavaTypeConfig.json");
        JSONArray jsonArray = JSONUtil.parseArray(string);
        for (Object object : jsonArray) {
            JSONObject jsonObject = JSONUtil.parseObj(object);
            String columnType = jsonObject.getStr("columnType");
            String javaType = jsonObject.getStr("javaType");
            if (Pattern.matches(columnType, observedColumnType)) {
                return javaType;
            }
        }
        return null;
    }

    private String getRecommendedJavaTypeClassName(String javaType) {
        if (StrUtil.isBlank(javaType)) {
            return null;
        }
        // 尝试通过全类名获取类的简单名字
        String simpleName = null;
        try {
            Class<?> aClass = Class.forName(javaType);
            simpleName = aClass.getSimpleName();
        } catch (ClassNotFoundException e) {
            // ignore
        }
        return simpleName;
    }

    public List<ColumnCfg> getColumnCfgsByTableCfgId(Integer tableCfgId) {
        LoggedGenSysUser user = LoginUserContextHolder.getUser();
        Assert.notNull(user, "用户未登录");
        List<ColumnCfg> columnCfgs = columnCfgMapper.findByAll(ColumnCfg.builder().userId(user.getId()).tableCfgId(tableCfgId).build());
        return columnCfgs;
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(TableCfgDeleteDTO deleteDTO) {
        LoggedGenSysUser user = LoginUserContextHolder.getUser();
        Assert.notNull(user, "用户未登录");
        List<Integer> ids = deleteDTO.getIds();
        for (Integer tableCfgId : ids) {
            tableCfgMapper.deleteByPrimaryKey(tableCfgId);
            columnCfgMapper.deleteByUserIdAndTableCfgId(user.getId(), tableCfgId);
        }
    }
}

