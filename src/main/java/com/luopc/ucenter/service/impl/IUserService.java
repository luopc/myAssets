package com.luopc.ucenter.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.common.framework.helper.RequestHolder;
import com.common.grid.model.Pager;
import com.common.grid.utils.GridUtils;
import com.luopc.ucenter.dao.SysUserMapper;
import com.luopc.ucenter.model.SysUser;
import com.luopc.ucenter.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service("userService")
@Slf4j
public class IUserService implements UserService {

	private SysUserMapper sysUserMapper;

	private JdbcTemplate jdbcTemplate;
	

	@Override
	public List<String> getAuthoritiesCodeByUserId(Integer userId) {		
		return sysUserMapper.getAuthoritiesCodeByUserId(userId);
	}
	

	@Override
	public void selectUserList(Pager pager) throws Exception {
		String sql = "select * from sys_user ";
		GridUtils.queryForGrid(jdbcTemplate, sql, pager, RequestHolder.getCurrentRequest(),
				RequestHolder.getCurrentResponse(), new Object[] {});
	}

	public List<SysUser> selectAllUser() {
		List<SysUser> uList = sysUserMapper.getAll();
		return uList;
	}

	@Override
	public SysUser findUserByLoginName(String loginName) {
		SysUser u = sysUserMapper.findUserByLoginName(loginName);
		return u;
	}

	@Override
	public SysUser findUserById(String id) {
		SysUser u = sysUserMapper.selectByPrimaryKey(Integer.parseInt(id));
		log.info("查询出的用户名为：" + u.getUsername());
		return u;
	}

	@Override
	public void loginSuccess(SysUser user) {
		log.info("用户名为【" + user.getUsername() + "】的用户登录成功");
	}

	@Override
	public boolean hasMatchUser(String loginName, String password) {
		SysUser u = sysUserMapper.findUserByLoginName(loginName);
		if (u == null)
			return false;
		return u.getPassword().equals(password);
	}

	@Autowired
	public void setSysUserMapper(SysUserMapper sysUserMapper) {
		this.sysUserMapper = sysUserMapper;
	}
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	

}
