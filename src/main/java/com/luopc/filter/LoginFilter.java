package com.luopc.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.common.framework.base.LoginUser;
import com.common.framework.helper.RequestHolder;
import com.luopc.ucenter.model.SysUser;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginFilter implements Filter {

	/*
	 * 不需要过滤的请求关键字
	 */
	private static String[] passKeys = new String[] { "login", "static", "common", "login.jsp", "sendCode.do","loginPage.jsp" };

	/**
	 * 测试连接中是否存在不需要过滤的关键字
	 *
	 * @param url
	 * @return
	 */
	private boolean isPassedUrl(String url) {
		for (int i = 0; i < passKeys.length; i++) {
			String urlStr = passKeys[i];
			if (url.indexOf(urlStr) != -1) {
				return true;
			}
		}
		return false;
	}

	public void init(FilterConfig filterConfig) throws ServletException {

	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) servletRequest;
		HttpServletResponse resp = (HttpServletResponse) servletResponse;
		String uri = req.getRequestURI();
//		log.info(uri);
		try {
			if (!isPassedUrl(uri)) {
				SysUser sysUser = (SysUser) req.getSession().getAttribute("user");
				if (sysUser == null) {
					String path = req.getContextPath() + "/login.jsp";
					resp.sendRedirect(path);
					return;
				}
				LoginUser user = new LoginUser(sysUser);
				RequestHolder.add(user);
				RequestHolder.add(req);
				RequestHolder.add(resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			/** 如果出现异常则跳转到友好提示页面 */
			return;
		}
		filterChain.doFilter(servletRequest, servletResponse);
		return;
	}

	public void destroy() {

	}

}
