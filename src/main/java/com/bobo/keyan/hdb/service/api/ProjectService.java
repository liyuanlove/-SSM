package com.bobo.keyan.hdb.service.api;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bobo.keyan.hdb.model.Project;
import com.bobo.keyan.hdb.model.ProjectExample;

public interface ProjectService {
    int countByExample(ProjectExample example);

    int deleteByExample(ProjectExample example);

    int deleteByPrimaryKey(Integer pId);

    int insert(Project record);

    int insertSelective(Project record);

    List<Project> selectByExample(ProjectExample example);

    Project selectByPrimaryKey(Integer pId);

    int updateByExampleSelective(@Param("record") Project record, @Param("example") ProjectExample example);

    int updateByExample(@Param("record") Project record, @Param("example") ProjectExample example);

    int updateByPrimaryKeySelective(Project record);

    int updateByPrimaryKey(Project record);
}
