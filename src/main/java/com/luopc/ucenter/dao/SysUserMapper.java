package com.luopc.ucenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.common.framework.base.PageQuery;
import com.luopc.ucenter.model.SysUser;

public interface SysUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    SysUser findByKeyword(@Param("keyword") String keyword);

    int countByMail(@Param("mail") String mail, @Param("id") Integer id);

    int countByTelephone(@Param("telephone") String telephone, @Param("id") Integer id);

    int countByDeptId(@Param("deptId") int deptId);

    List<SysUser> getPageByDeptId(@Param("deptId") int deptId, @Param("page") PageQuery page);

    List<SysUser> getByIdList(@Param("idList") List<Integer> idList);

    List<SysUser> getAll();

    SysUser findUserByLoginName(String loginName);

	List<String> getAuthoritiesCodeByUserId(Integer userId);
}