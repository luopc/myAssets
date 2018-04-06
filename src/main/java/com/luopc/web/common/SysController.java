package com.luopc.web.common;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.common.framework.base.LoginUser;
import com.common.framework.helper.RequestHolder;
import com.luopc.ucenter.model.SysUser;
import com.luopc.ucenter.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/sys")
public class SysController {
	
	@Autowired
	private UserService userService;

	@GetMapping(value = "/main.html")
	public ModelAndView main(HttpServletRequest request, HttpServletResponse response) {
		LoginUser user = RequestHolder.getCurrentUser();
//		log.info(user.toString());
		return new ModelAndView("pc/main");
	}

	@GetMapping(value = "/user.html")
	public ModelAndView user(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView view = new ModelAndView("pc/ucentor/user/userMain");
		List<SysUser> userList = userService.selectAllUser();
		view.addObject("userList", userList);
		return view;
	}
	
	@GetMapping(value = "/role.html")
	public ModelAndView role(HttpServletRequest request, HttpServletResponse response) {
		LoginUser user = RequestHolder.getCurrentUser();
		log.info(user.toString());
		return new ModelAndView("pc/ucentor/role/roleMain");
	}
	
	@GetMapping(value = "/acl.html")
	public ModelAndView acl(HttpServletRequest request, HttpServletResponse response) {
		LoginUser user = RequestHolder.getCurrentUser();
		log.info(user.toString());
		return new ModelAndView("pc/ucentor/acl/aclMain");
	}

}
