package com.shiro.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.shiro.model.User;

public class ShiroDbRealm extends AuthorizingRealm {

	/**
	 * 权限认证 
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		System.out.println("权限认证开始＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");
		User user = (User)principalCollection.getPrimaryPrincipal();
		System.out.println(user.getUsername());
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addStringPermission("add");
		return info;
	}

	/**
	 * 登录认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
			throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
		User user = new User();
		user.setUsername(token.getUsername());
		user.setPassword("123456");
		return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
	}

}
