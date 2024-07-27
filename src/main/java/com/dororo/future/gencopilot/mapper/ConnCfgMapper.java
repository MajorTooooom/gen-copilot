package com.dororo.future.gencopilot.mapper;

import com.dororo.future.gencopilot.domain.ConnCfg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

@Mapper
public interface ConnCfgMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ConnCfg record);

    int insertSelective(ConnCfg record);

    ConnCfg selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ConnCfg record);

    int updateByPrimaryKey(ConnCfg record);

    List<ConnCfg> findByAll(ConnCfg connCfg);

    int updateIsDeletedByIdIn(@Param("updatedIsDeleted") String updatedIsDeleted, @Param("idCollection") Collection<Integer> idCollection);
}