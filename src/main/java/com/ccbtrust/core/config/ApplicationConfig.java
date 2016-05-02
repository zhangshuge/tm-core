package com.ccbtrust.core.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 定义Spring容器管理的Bean对象以及加载资源配置文件
 * @author ciyuan
 *
 */
@Configuration
@ComponentScan(basePackages="com.ccbtrust")
public class ApplicationConfig {

}
