package com.ccbtrust.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 定义Spring容器管理的Bean对象以及加载资源配置文件
 * @author ciyuan
 *
 */
@Configuration
@ComponentScan(basePackages="com.ccbtrust")
/*ignoreResourceNotFound=true当文件不存在时不抛出异常，默认false*/
@PropertySources({@PropertySource(value="classpath:/jdbc.properties",ignoreResourceNotFound=true)})
@EnableScheduling
public class ApplicationConfig {
	/**
	 * PropertySourcesPlaceholderConfigurer 需配合@value注解使用，方可获取文件内用。    提倡使用使用Environment获取文件中内容。
	 * @Value("${db.type}")
	 * private String dbType;
	 * 
	 * @Autowired
	 * Environment environment;
	 * environment.getProperty("sql.map.config.file")
	 * @return
	 */
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
