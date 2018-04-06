package com.luopc.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.luopc.ucenter.model.SysUser;
import com.luopc.ucenter.service.UserService;

public class AuthUserRealm extends AuthorizingRealm {
	
	private UserService userService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
		SysUser user = (SysUser) principal.fromRealm(this.getClass().getName()).iterator().next();
		
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken usernamePasswordToken  = (UsernamePasswordToken) token;
		String userName = usernamePasswordToken.getUsername();
		SysUser user = userService.findUserByLoginName(userName);
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), this.getClass().getName());
		return info;
	}

}
