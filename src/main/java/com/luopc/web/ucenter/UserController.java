package com.luopc.web.ucenter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.common.framework.base.JsonData;
import com.common.framework.base.TableJson;
import com.common.framework.exception.PermissionException;
import com.common.grid.model.Pager;
import com.common.grid.utils.PagerPropertyUtils;
import com.luopc.ucenter.model.SysUser;
import com.luopc.ucenter.service.UserService;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/list.json")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<SysUser> userList = userService.selectAllUser();
		TableJson data = TableJson.success(userList, "查询成功", userList.size());
		return new ModelAndView("jsonView", data.toMap());
	}
	
	@RequestMapping("/edit/{id}")
	public ModelAndView edit(@PathVariable String id, HttpServletResponse response) {
		ModelAndView view = new ModelAndView("pc/ucentor/user/userEdit");
		SysUser u = userService.findUserById(id);
		view.addObject("userInfo", u);
		return view;
	}

	@RequestMapping("/show/{id}")
	public ModelAndView show(@PathVariable String id, HttpServletResponse response) {
		ModelAndView view = new ModelAndView("pc/ucentor/user/userView");
		SysUser u = userService.findUserById(id);
		view.addObject("userInfo", u);
		return view;
	}

	@RequestMapping("/test.json")
	public ModelAndView test(HttpServletRequest request, HttpServletResponse response) {
		List<SysUser> userList = userService.selectAllUser();
		JsonData data = JsonData.success(userList, "查询成功");
		throw new PermissionException("test exception");
	}
}
