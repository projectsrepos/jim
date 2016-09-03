/*
 * Copyright (c) 2016. All rights reserved by XuanWu Wireless Technology Co.Ltd.
 */
package com.jim.im.test;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jersey.JerseyAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;
import org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ServerPropertiesAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import com.jim.im.test.config.JDBCTemplateConfig;
import com.jim.im.test.config.JerseyConfig;
import com.jim.im.test.config.JmsClientConfig;
import com.jim.im.test.config.RedisConfig;


/**  
 * Test model Server
 * @author <a herf="huzhiwen@wxchina.com">huzhiwen</a>
 * @version V1.0.0   
 * @since 2016年4月7日 
 */
@Configurable
@ComponentScan( basePackages = "com.jim.im" )
@Import({PropertyPlaceholderAutoConfiguration.class,
    ServerPropertiesAutoConfiguration.class,
    EmbeddedServletContainerAutoConfiguration.class,
    JerseyAutoConfiguration.class,
    DataSourceAutoConfiguration.class,
    JDBCTemplateConfig.class,
    JmsAutoConfiguration.class,
    JerseyConfig.class,JmsClientConfig.class,
    RedisAutoConfiguration.class,
    RedisConfig.class})
public class TestServer {
	public static void main(String[] args) {
		SpringApplication.run(TestServer.class, args);
	}
}
