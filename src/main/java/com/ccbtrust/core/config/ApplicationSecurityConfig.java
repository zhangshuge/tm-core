package com.ccbtrust.core.config;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ccbtrust.core.shiro.SecurityFilter;
import com.ccbtrust.core.shiro.ShiroRealm;

/**
 * 应用程序安全配置
 * @author ciyuan
 *
 */
@Configuration
public class ApplicationSecurityConfig {

	private static final Logger logger = LoggerFactory.getLogger(ApplicationSecurityConfig.class);
	
	@Autowired
	private ApplicationContext applicationContext;
	/**
	 * 定义shiro filter安全过滤器
	 * @return
	 */
	@Bean
	public Object shiroFilter(){
		ShiroFilterFactoryBean sffb = new ShiroFilterFactoryBean();
		sffb.setSecurityManager(securityManager());
		sffb.setLoginUrl("");
		sffb.setSuccessUrl("");
		sffb.setUnauthorizedUrl("");
		
		Map<String,Filter> filterMap = new LinkedHashMap<String,Filter>();
		filterMap.put("security", applicationContext.getBean(SecurityFilter.class));
		sffb.setFilters(filterMap);
		
		// 请求地址安全过滤配置
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		filterChainDefinitionMap.put("/styles/**", "anon");
		filterChainDefinitionMap.put("/scripts/**", "anon");
		filterChainDefinitionMap.put("/index.jsp", "anon");
		filterChainDefinitionMap.put("/login", "anon");
		filterChainDefinitionMap.put("/signIn", "anon");
		filterChainDefinitionMap.put("/logout", "anon");
		filterChainDefinitionMap.put("/**/**.do", "security");
		sffb.setFilterChainDefinitionMap(filterChainDefinitionMap);
		try {
			return sffb.getObject();
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 定义安全管理对象
	 * @return
	 */
	public DefaultWebSecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager(); 
		securityManager.setRealm(applicationContext.getBean(ShiroRealm.class));
		return securityManager;
	}
}
