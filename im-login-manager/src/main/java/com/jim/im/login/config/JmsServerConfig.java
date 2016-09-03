/*
 * Copyright 2014 Jim. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jim.im.login.config;

import com.jim.im.api.LoginRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.jms.remoting.JmsInvokerProxyFactoryBean;
import org.springframework.jms.remoting.JmsInvokerServiceExporter;

import com.jim.im.config.GenericMQConfig;
import com.jim.im.consts.IMConstant;


/**
* 登录服务jms配置项 
* @author huzhiwen
* @version V1.0.0   
 */
@Configuration
public class JmsServerConfig extends GenericMQConfig {
    
    @Autowired
    private LoginRpcService loginService;
    
    /**
     * @Title: jmsInvokerProxyFactoryBean  
     * @Description: rpc请求代理工厂
     * @return
      */
     @Bean
     public JmsInvokerProxyFactoryBean jmsInvokerProxyFactoryBean(){
    	 return buildRpcServiceProxyFactoryBean(
    			 IMConstant.API_QUEUE_LOGIN_SERVICE, LoginRpcService.class);
     }
    
    /**
    * @Title: jmsInvokerServiceExporter  
    * @Description: 向外提供服务
    * @return
     */
	@Bean
	public JmsInvokerServiceExporter jmsInvokerServiceExporter(){
	    return buildRpcServiceExport(loginService, LoginRpcService.class);
	}
	
	/**
	* @Title: jmsContainer  
	* @Description: 定义jms监听容器
	* @param messageListener
	* @return DefaultMessageListenerContainer
	 */
	@Bean
	public MessageListenerContainer jmsContainer(JmsInvokerServiceExporter messageListener){
		return buildListenerContainer(messageListener, IMConstant.API_QUEUE_LOGIN_SERVICE);
	}

}
