/*
 * Copyright (c) 2016 by XuanWu Wireless Technology Co.Ltd. 
 *             All rights reserved                         
 */
package com.jim.im.test.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 
 * @author <a href="huzhiwen@wxchina.com">huzhiwen</a>
 * @date 2016年6月16日
 * @version 1.0.0
 */
@Configuration
public class JDBCTemplateConfig {
	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource){
		return new JdbcTemplate(dataSource);
	}
}
