package com.luopc.ucenter.service;

import java.util.List;

import com.common.grid.model.Pager;
import com.luopc.ucenter.model.SysUser;

public interface UserService {

	List<SysUser> selectAllUser();

	SysUser findUserByLoginName(String loginName);

	void loginSuccess(SysUser user);

	boolean hasMatchUser(String loginName, String password);

	SysUser findUserById(String id);

	void selectUserList(Pager pager) throws Exception;
	
	List<String> getAuthoritiesCodeByUserId(Integer userId);
}
