package com.luopc.ucenter.service;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.common.util.ArrayUtil;
import com.luopc.ucenter.model.SysRole;
import com.luopc.ucenter.model.SysUser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {
		"classpath*:spring/applicationContext-context.xml",
        "classpath*:spring/applicationContext-database.xml", 
        "classpath*:spring/applicationContext-mybatis.xml",
        "classpath*:spring/applicationContext-resources.xml"})
public class UserServiceTest {

	private UserService userService;
	
	private RoleService roleService;

	@Test
	public void findAllUser() {
		List<SysUser> uList = userService.selectAllUser();
		for (SysUser user : uList) {
			System.out.println(user.getRoleList().size());
		}
		System.out.println(uList.size());
	}

	@Test
	public void testFindUserByLoginName() {
		SysUser user = userService.findUserByLoginName("Admin");
		System.out.println(user.getUsername());
	}

	@Test
	public void testHasMatchUser() {
		Boolean has = userService.hasMatchUser("Admin", "123456");
		System.out.println(has);
	}
	
	@Test
	public void testAclList() {
		List<String> roles = userService.getAuthoritiesCodeByUserId(1);
		System.out.println(roles);
	}
	
	@Test
	public void testSysRole() {
		List<SysRole> roles = roleService.getRolesByUserId(1);
		System.out.println(roles.size());
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Autowired
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

}
