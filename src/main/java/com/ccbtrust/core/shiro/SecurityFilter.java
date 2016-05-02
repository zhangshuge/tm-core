package com.ccbtrust.core.shiro;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
/**
 * 配置过滤器过滤URL请求
 * @author ciyuan
 *
 */
@Repository
public class SecurityFilter implements Filter{

	private static final Logger logger = LoggerFactory.getLogger(SecurityFilter.class);
	/**
	 * 正则-请求过滤URL
	 */
	private static Pattern p = Pattern.compile("^.+\\.(css|js|jpg|gif|jpeg|bmp|png)$");
	/**
	 * 定义过滤连接集合
	 */
	private static Set<String> noLogin = new HashSet<String>();
	static{
		noLogin.add("/");
		noLogin.add("/login.do");
		noLogin.add("/logout.do");
		noLogin.add("/login/index.do");
	}
	public static void main(String[] args){
		String uri = "/login/index.do";
		System.out.println(noLogin.contains(uri));
	}
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		String uri = httpRequest.getRequestURI();
		String ctx = httpRequest.getContextPath();
		uri = uri.replaceFirst(ctx, "");
		while(uri.indexOf("/%20") != -1){
			uri = uri.replaceAll("/%20", "/");
		}
		while(uri.indexOf("//") != -1){
			uri = uri.replaceAll("//", "/");
		}
		if(p.matcher(uri).matches()){//过滤静态文件请求
			chain.doFilter(httpRequest, httpResponse);
		}else{
			logger.debug("进入过滤器(" + httpRequest.getRequestURL() + ")");
			/*判断用户安全状态和session*/
			if(!SecurityUtils.getSubject().isAuthenticated()
					|| SecurityUtils.getSubject().getSession().getAttribute("currentUser")==null){
				if(!noLogin.contains(uri)){
					String uriStr = uri.replace("/", "");
		        	if("index.do".equals(uriStr)){
		        		logger.debug("用户未登录，转向登陆页面");
		        		httpResponse.sendRedirect(ctx + "/login.do");		        		
		        	}else{
		        		httpResponse.sendError(600,"请登录系统后再访问");
		        	}
				}else{
					chain.doFilter(httpRequest, httpResponse);
				}
			}else{
				chain.doFilter(httpRequest, httpResponse);
			}
		}
	}

	@Override
	public void destroy() {
	}

}
