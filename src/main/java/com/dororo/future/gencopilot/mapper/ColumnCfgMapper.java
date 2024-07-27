package com.dororo.future.gencopilot.mapper;
import java.util.Collection;

import com.dororo.future.gencopilot.domain.ColumnCfg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ColumnCfgMapper {
    int insert(ColumnCfg record);

    int insertList(@Param("list") List<ColumnCfg> list);

    int insertSelective(ColumnCfg record);

    List<ColumnCfg> findByAll(ColumnCfg columnCfg);

    List<ColumnCfg> selectAll();

    int deleteByUserIdAndTableCfgId(@Param("userId") Integer userId, @Param("tableCfgId") Integer tableCfgId);

    int deleteByIdIn(@Param("idCollection")Collection<Integer> idCollection);

}