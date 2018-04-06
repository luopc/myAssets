package com.luopc.web.common;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.common.framework.base.JsonData;
import com.luopc.common.base.LoginCommand;
import com.luopc.ucenter.model.SysUser;
import com.luopc.ucenter.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {
	@Autowired
	private UserService userService;
	
	
	@RequestMapping(value = "/login" ,method = RequestMethod.POST)
	public ModelAndView login(HttpServletRequest request, LoginCommand loginCommand) {
		log.info("Login user=====" + loginCommand);
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(loginCommand.getLoginName(), loginCommand.getPassword());
		String remember = WebUtils.getCleanParam(request, "remember");
		log.info("remember=" + remember);
		try {
	    	if(remember != null && remember.equalsIgnoreCase("on")) {
	    	    token.setRememberMe(true);
	    	}
			subject.login(token);
			return new ModelAndView(new RedirectView(request.getContextPath()+"/sys/main.html"));
		} catch(UnknownAccountException ue) {
			token.clear();
			JsonData data = JsonData.fail("登录失败，您输入的账号不存在");
			return new ModelAndView(new RedirectView(request.getContextPath()+"/login"));
		} catch(IncorrectCredentialsException ie) {
			token.clear();
			JsonData data = JsonData.fail("登录失败，密码不匹配");
			return new ModelAndView(new RedirectView(request.getContextPath()+"/login"));
		} catch(RuntimeException re) {
			token.clear();
			JsonData data = JsonData.fail("登录失败");
			return new ModelAndView(new RedirectView(request.getContextPath()+"/login"));
		}
	}

	@GetMapping
	public void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect(request.getContextPath() + "/loginPage.jsp");
	}
	
	@GetMapping(value = "/loginCheck.do")
	public void loginPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect(request.getContextPath() + "/login.jsp");
	}

	@PostMapping(value = "/loginCheck.do")
	public ModelAndView loginCheck(HttpServletRequest request, HttpServletResponse response, LoginCommand loginCommand)
			throws IOException, ServletException {
		log.info("loginName:" + loginCommand.getLoginName());
		log.info("password:" + loginCommand.getPassword());
		// BeanValidator.check(loginCommand);
		boolean isValidUser = userService.hasMatchUser(loginCommand.getLoginName(), loginCommand.getPassword());
		if (!isValidUser) {
//			JsonData data = JsonData.fail("用户名或密码错误！");
//			return new ModelAndView("jsonView", data.toMap());
			return new ModelAndView(new RedirectView(request.getContextPath()+"/login"));
		} else {
			SysUser user = userService.findUserByLoginName(loginCommand.getLoginName());
			user.setOperateIp(request.getLocalAddr());
			user.setOperateTime(new Date());
			userService.loginSuccess(user);
			request.getSession().setAttribute("user", user);
//			response.sendRedirect(request.getContextPath() + "/sys/main.html");
			return new ModelAndView(new RedirectView(request.getContextPath()+"/sys/main.html"));
		}
	}

	
}
