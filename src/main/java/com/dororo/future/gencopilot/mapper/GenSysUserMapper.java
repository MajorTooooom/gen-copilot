package com.dororo.future.gencopilot.mapper;

import com.dororo.future.gencopilot.domain.GenSysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GenSysUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GenSysUser record);

    int insertList(@Param("list") List<GenSysUser> list);

    int insertSelective(GenSysUser record);

    GenSysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GenSysUser record);

    int updateByPrimaryKey(GenSysUser record);

    List<GenSysUser> findByAll(GenSysUser genSysUser);

    List<GenSysUser> selectAll();
}