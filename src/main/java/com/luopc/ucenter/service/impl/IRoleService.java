package com.luopc.ucenter.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luopc.ucenter.dao.SysRoleMapper;
import com.luopc.ucenter.model.SysRole;
import com.luopc.ucenter.service.RoleService;

@Service(value="roleService")
public class IRoleService implements RoleService{
	
	private SysRoleMapper sysRoleMapper;

	@Override
	public List<SysRole> getRolesByUserId(Integer userId) {		
		return sysRoleMapper.getRolesByUserId(userId);
	}

	@Autowired
	public void setSysRoleMapper(SysRoleMapper sysRoleMapper) {
		this.sysRoleMapper = sysRoleMapper;
	}

	
	
}
