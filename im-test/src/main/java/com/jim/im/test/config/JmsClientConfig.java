/*
 * Copyright (c) 2016. All rights reserved by XuanWu Wireless Technology Co.Ltd.
 */
package com.jim.im.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.remoting.JmsInvokerProxyFactoryBean;

import com.jim.im.api.LoginRpcService;
import com.jim.im.config.GenericMQConfig;
import com.jim.im.consts.IMConstant;


/**
* Jms client Config
* @author <a herf="huzhiwen@wxchina.com">huzhiwen</a>
* @version V1.0.0   
* @since 2016年4月7日 上午9:18:53
 */
@Configuration
public class JmsClientConfig extends GenericMQConfig{
    /**
     * @Title: jmsInvokerProxyFactoryBean  
     * @Description: rpc请求代理工厂
     * @param targetConnectionFactory
     * @return
      */
     @Bean
     public JmsInvokerProxyFactoryBean jmsInvokerProxyFactoryBean(){
         return buildRpcServiceProxyFactoryBean(IMConstant.API_QUEUE_LOGIN_SERVICE, LoginRpcService.class);
     }
    
}
