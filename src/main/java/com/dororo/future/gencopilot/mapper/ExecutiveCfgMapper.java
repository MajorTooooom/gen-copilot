package com.dororo.future.gencopilot.mapper;

import com.dororo.future.gencopilot.domain.ExecutiveCfg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 请输入类描述
 *
 * @author Dororo
 * @date 2024-05-15 16:39
 */
@Mapper
public interface ExecutiveCfgMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ExecutiveCfg record);

    int insertList(@Param("list") List<ExecutiveCfg> list);

    int insertSelective(ExecutiveCfg record);

    ExecutiveCfg selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ExecutiveCfg record);

    int updateByPrimaryKey(ExecutiveCfg record);

    List<ExecutiveCfg> findByAll(ExecutiveCfg executiveCfg);

    List<ExecutiveCfg> selectAll();

    ExecutiveCfg findOneByAll(ExecutiveCfg executiveCfg);

    List<ExecutiveCfg> selectIdAndUpdateTime();

}