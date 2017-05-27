package com.shiro.shiro;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class ShiroConfig {

	/**
	 * 安全管理器
	 */
	@Bean
	public DefaultWebSecurityManager securityManager(ShiroDbRealm shiroDbRealm) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(shiroDbRealm);
		return securityManager;
	}

	/**
	 * 项目自定义的Realm
	 */
	@Bean
	public ShiroDbRealm shiroDbRealm(CredentialsMatcher credentialsMatcher) {
		ShiroDbRealm shiroDbRealm = new ShiroDbRealm();
		shiroDbRealm.setCredentialsMatcher(credentialsMatcher);
		return shiroDbRealm;
	}

	/**
	 * 自定义CredentialsMatcher
	 * 
	 * @return
	 */
	@Bean
	public CredentialsMatcher credentialsMatcher() {
		return new CredentialsMatcher();
	}

	/**
	 * Shiro的过滤器链
	 */
	@Bean
	public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
		shiroFilter.setSecurityManager(securityManager);
		/**
		 * 默认的登陆访问url
		 */
		shiroFilter.setLoginUrl("/tologin");
		/**
		 * 登陆成功后跳转的url
		 */
		shiroFilter.setSuccessUrl("/");
		/**
		 * 没有权限跳转的url
		 */
		shiroFilter.setUnauthorizedUrl("/global/error");
		/**
		 * 配置shiro拦截器链
		 *
		 * anon 不需要认证 authc 需要认证 user 验证通过或RememberMe登录的都可以
		 *
		 */
		Map<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("/static/**", "anon");
		hashMap.put("/login", "anon");
		hashMap.put("/kaptcha", "anon");
		hashMap.put("/**", "user");
		shiroFilter.setFilterChainDefinitionMap(hashMap);
		return shiroFilter;
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
			DefaultWebSecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}
	
	  /**
     * 保证实现了Shiro内部lifecycle函数的bean执行
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 启用shrio授权注解拦截方式，AOP式方法级权限检查
     */
    @Bean
    @DependsOn(value = "lifecycleBeanPostProcessor") //依赖其他bean的初始化
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        return new DefaultAdvisorAutoProxyCreator();
    }
}
