package com.ccbtrust.core.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ccbtrust.common.log.ThreadValue;
import com.ccbtrust.common.utils.SequenceUtil;

public class ControllerInterceptor implements HandlerInterceptor {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * preHandle方法是进行处理器拦截用的，顾名思义，该方法将在Controller处理之前进行调用，SpringMVC中的Interceptor拦截器是链式的，可以同时存在 
     * 多个Interceptor，然后SpringMVC会根据声明的前后顺序一个接一个的执行，而且所有的Interceptor中的preHandle方法都会在 
     * Controller方法调用之前调用。SpringMVC的这种Interceptor链式结构也是可以进行中断的，这种中断方式是令preHandle的返 
     * 回值为false，当preHandle的返回值为false的时候整个请求就结束了。 
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String path = request.getContextPath();//获取项目路径
		/*
		 * 获得序列
		 */
		Long id = SequenceUtil.getInstance().getSequenceFile("wapPay");
		String num= SequenceUtil.formatSequence(id, 5);
		ThreadValue.put("rpid", num);//获取rpid
		String moduleId = this.getModuleId(path);
		ThreadValue.put("moduleId", moduleId);//模块ID
		//ThreadValue.put("userName", userDeatil.getUserName());//获取操作人
		//ThreadValue.put("pdCode", userDeatil.getPdCode());//获取产品id
		ThreadValue.put("IP", getRemoteHost(request));
		return true;
	}

	
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO 自动生成的方法存根

	}

	
	/**
	 * 从request获得IP地址
	 * @param request
	 * @return
	 */
	private String getRemoteHost(javax.servlet.http.HttpServletRequest request){
	    String ip = request.getHeader("x-forwarded-for");
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getRemoteAddr();
	    }
	    return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
	}
	/**
	 * 获取模块类型
	 * @param path 请求路径
	 * @return
	 */
	private String getModuleId(String path) {
		String moduleId = null;
		if (!StringUtils.isEmpty(path)) {
			if (path.indexOf("tm-accounting") > -1) {
				moduleId = "A";
			} else if (path.indexOf("tm-gaccounting") > -1) {
				moduleId = "G";
			} else if (path.indexOf("tm-invest") > -1) {
				moduleId = "I";
			} else if (path.indexOf("tm-product") > -1) {
				moduleId = "P";
			} else if (path.indexOf("tm-report") > -1) {
				moduleId = "R";
			} else if (path.indexOf("tm-sales") > -1) {
				moduleId = "S";
			} else if (path.indexOf("tm-searc") > -1) {
				moduleId = "E";
			}
		}
		return moduleId;
	}
}
