package com.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

	@RequestMapping("/tologin")
	public String toLogin() {
		return "login";
	}

	@RequestMapping("/login")
	public String login(String username, String password) {
		UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
		Subject subject = SecurityUtils.getSubject();
		subject.login(usernamePasswordToken);
		return "index";
	}

	public void logout() {

	}

	@RequestMapping("/add")
	@RequiresPermissions("add")
	public String add() {
		return "success";
	}
}
