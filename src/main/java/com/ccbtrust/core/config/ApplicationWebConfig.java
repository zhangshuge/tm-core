package com.ccbtrust.core.config;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.ccbtrust.common.utils.Constants;
import com.ccbtrust.core.interceptor.ControllerInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * Spring web mvc 相关配置
 * @author ciyuan
 *
 */
@Configuration
@EnableWebMvc//激活Spring MVC 配置
@EnableAspectJAutoProxy(proxyTargetClass=true)//激活切面AOP
public class ApplicationWebConfig extends WebMvcConfigurerAdapter {

	/*
	 * (non-Javadoc)
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
	 * #configureViewResolvers(
	 * org.springframework.web.servlet.config.annotation.ViewResolverRegistry)
	 * 配置视图解析翻译从控制器返回到具体的基于字符串的视图名称视图 实现执行与渲染。
	 */
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.enableContentNegotiation(new MappingJackson2JsonView());
		registry.jsp("/WEB-INF/views/", ".jsp");//注册JSP视图解析器指定的前缀和后缀。
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addResourceHandlers(
	 * org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry)
	 * 添加处理程序服务静态资源，如图片，JS，并从其他受Web应用程序根目录的特定位置，类路径中，cssfiles。
	 */
	 @Override
	 public void addResourceHandlers(ResourceHandlerRegistry registry) {
	   registry.addResourceHandler("/styles/**").addResourceLocations("/styles/");
	   registry.addResourceHandler("/scripts/**").addResourceLocations("/scripts/");
	   registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	 }
	 /*
	  * (non-Javadoc)
	  * 
	  * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
	  * #addViewControllers( org.springframework.web.servlet.config.annotation.ViewControllerRegistry)
      * 
	  * 配置视图控制器
	  */
	 @Override
	 public void addViewControllers(ViewControllerRegistry registry) {
	   registry.addViewController("/").setViewName("/login");
	   registry.addViewController("/login.do").setViewName("/login");
	   registry.addViewController("/index.do").setViewName("/login/index");
	 }
	 /*
	   * (non-Javadoc)
	   * 
	   * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
	   * #configureDefaultServletHandling(org.springframework.web.servlet.config.
	   * annotation.DefaultServletHandlerConfigurer)
	   */
	  @Override
	  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
	    configurer.enable();
	  }

	  /*
	   * (non-Javadoc)
	   * 
	   * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#
	   * configureMessageConverters(java.util.List)
	   */
	  @Override
	  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
	    // Date to/from json
	    ObjectMapper objectMapper = new ObjectMapper();
	    objectMapper.setDateFormat(new SimpleDateFormat(Constants.DatePatterns.Date.getPattern()));
	    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
	    converter.setObjectMapper(objectMapper);
	    converters.add(converter);
	  }
	  /**
	   * 添加拦截器,可添加多个。
	   */
	  @Override
	  public void addInterceptors(InterceptorRegistry registry){
		  /*
		   * 自定义拦截器，在调用控制器前，将rpid、记录日志等信息放在线程池中
		   */
		  registry.addInterceptor(new ControllerInterceptor());
	  }
}
