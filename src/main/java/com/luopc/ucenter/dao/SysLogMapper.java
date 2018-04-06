package com.luopc.ucenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.common.framework.base.PageQuery;
import com.luopc.ucenter.dto.SearchLogDto;
import com.luopc.ucenter.model.SysLog;
import com.luopc.ucenter.model.SysLogWithBLOBs;

public interface SysLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysLogWithBLOBs record);

    int insertSelective(SysLogWithBLOBs record);

    SysLogWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysLogWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(SysLogWithBLOBs record);

    int updateByPrimaryKey(SysLog record);

    int countBySearchDto(@Param("dto") SearchLogDto dto);

    List<SysLogWithBLOBs> getPageListBySearchDto(@Param("dto") SearchLogDto dto, @Param("page") PageQuery page);
}