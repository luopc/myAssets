package com.luopc.ucenter.service;

import java.util.List;

import com.luopc.ucenter.model.SysRole;

public interface RoleService {
	
	List<SysRole> getRolesByUserId(Integer userId);

}
