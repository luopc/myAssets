package com.common.framework.base;


import com.luopc.ucenter.model.SysUser;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude= {"md5PassWord","passWord"})
public class LoginUser {

    

	private String userId;

    private String userName;

    private String passWord;

    private String loginName;

    private String md5PassWord;

    private String userEmail;

    private String telePhone;

    private String mobile;

    private String position;

    private String orgId;

    private String orgName;

    private String officId;

    private String officName;

    private String orgIds;

    private String orgNames;

    private String officIds;

    private String officNames;
    
    public LoginUser(SysUser sysUser) {
    	this.userId = String.valueOf(sysUser.getId());
    	this.userName = sysUser.getUsername();
    	this.loginName = sysUser.getLoginname();
    	this.userEmail = sysUser.getMail();
	}

}
