package com.ranze.schedule.mapper;

import com.ranze.schedule.pojo.ClockIn;
import com.ranze.schedule.pojo.ClockInExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ClockInMapper {
    long countByExample(ClockInExample example);

    int deleteByExample(ClockInExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ClockIn record);

    int insertSelective(ClockIn record);

    List<ClockIn> selectByExample(ClockInExample example);

    ClockIn selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ClockIn record, @Param("example") ClockInExample example);

    int updateByExample(@Param("record") ClockIn record, @Param("example") ClockInExample example);

    int updateByPrimaryKeySelective(ClockIn record);

    int updateByPrimaryKey(ClockIn record);
}