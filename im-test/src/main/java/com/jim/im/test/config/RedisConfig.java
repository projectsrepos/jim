/*
 * Copyright (c) 2016. All rights reserved by XuanWu Wireless Technology Co.Ltd.
 */
package com.jim.im.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
* Redis bean(redisTemplate) Config
* @author <a herf="huzhiwen@wxchina.com">huzhiwen</a>
* @version V1.0.0   
* @since 2016年4月6日
 */
@Configuration
public class RedisConfig {
	
	  /**
	  * @Title: redisTemplate  
	  * @Description: 生成RedisTemplate
	  * @param factory
	  * @return StringRedisTemplate
	  * @throws
	   */
	  @Bean
	  public StringRedisTemplate redisTemplate(
	      RedisConnectionFactory factory) {
	    final StringRedisTemplate template = new StringRedisTemplate(factory);
	    return template;
	  }
	  
}
