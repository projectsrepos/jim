/*
 * Copyright (c) 2016. All rights reserved by XuanWu Wireless Technology Co.Ltd.
 */
package com.jim.im.test.config;

import org.springframework.stereotype.Component;

import com.jim.im.config.GenericJerseyConfig;

/**
* Jersey Config
* @author <a herf="huzhiwen@wxchina.com">huzhiwen</a>
* @version 1.0.0   
* @since 2016年4月12日
 */
@Component
public class JerseyConfig extends GenericJerseyConfig {
	/**
	 * 初始化Jersey配置
	 */
    public JerseyConfig() {
    	super();
        packages("com.jim.im.test");
    }
}
