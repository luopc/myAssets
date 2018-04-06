package com.luopc.common.base;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginCommand {

	@NotBlank
	@Min(value =3, message = "用户名最小长度为3")
	@Max(value = 20, message = "用户名最大长度为20")
	private String loginName;

	@NotBlank
	@Min(value = 4, message = "密码最小长度为6")
	@Max(value = 20, message = "密码最大长度为20")
	private String password;

}
