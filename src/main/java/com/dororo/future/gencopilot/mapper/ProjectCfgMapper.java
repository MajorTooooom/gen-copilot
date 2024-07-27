package com.dororo.future.gencopilot.mapper;

import com.dororo.future.gencopilot.domain.ProjectCfg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProjectCfgMapper {
    int insert(ProjectCfg record);

    int insertList(@Param("list") List<ProjectCfg> list);

    int insertSelective(ProjectCfg record);

    ProjectCfg selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProjectCfg record);

    int updateByPrimaryKey(ProjectCfg record);

    List<ProjectCfg> findByAll(ProjectCfg projectCfg);

    List<ProjectCfg> selectAll();

    int updateBatchSelective(List<ProjectCfg> list);
}