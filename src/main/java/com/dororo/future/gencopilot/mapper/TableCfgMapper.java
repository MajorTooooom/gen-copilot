package com.dororo.future.gencopilot.mapper;

import com.dororo.future.gencopilot.domain.TableCfg;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TableCfgMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TableCfg record);

    int insertList(@Param("list") List<TableCfg> list);

    int insertSelective(TableCfg record);

    TableCfg selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TableCfg record);

    int updateByPrimaryKey(TableCfg record);

    List<TableCfg> findByAll(TableCfg tableCfg);

    List<TableCfg> selectAll();
}