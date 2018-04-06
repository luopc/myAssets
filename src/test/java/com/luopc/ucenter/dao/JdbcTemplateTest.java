package com.luopc.ucenter.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.luopc.ucenter.model.SysUser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {
		"classpath*:spring/applicationContext-context.xml",
        "classpath*:spring/applicationContext-database.xml", 
        "classpath*:spring/applicationContext-mybatis.xml",
        "classpath*:spring/applicationContext-resources.xml"})
public class JdbcTemplateTest {
	
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Test
	public void queryInt() {
		String sql = "select count(*) from sys_user";
		Integer c = jdbcTemplate.queryForObject(sql, Integer.class);
		System.out.println(c);
	}

	@Test
	public void queryString() {
		String sql = "select count(*) from sys_user";
		String c = jdbcTemplate.queryForObject(sql, String.class);
		System.out.println(c);
	}

	@Test
	public void queryObject() {
		String sql = "select * from sys_user where loginname = ?";
		SysUser u = new SysUser();
		jdbcTemplate.query(sql, new Object[] { "admin" }, (rs) -> {
			u.setUsername(rs.getString("username"));
		});
		System.out.println(u.getUsername());
	}

	@Test
	public void queryList() {
		String sql = "select * from sys_user ";
		List<SysUser> uList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(SysUser.class));
		System.out.println(uList.size());
	}

	@Test
	public void queryList2() {
		String sql = "select * from sys_user ";
		RowMapper<SysUser> ur = (rs, i) -> {
			SysUser u = new SysUser();
			u.setUsername(rs.getString("username"));
			return u;
		};
		List<SysUser> uList = jdbcTemplate.query(sql, ur);
		System.out.println(uList.size());
		System.out.println(uList.get(0).getUsername());
	}
	
}
