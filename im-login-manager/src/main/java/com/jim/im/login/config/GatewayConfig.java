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

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Gateway Config
 * @version 1.0.0
 */
@Configuration
public class GatewayConfig {
	@Value("${gateway_ip}")
	private String gateway_ip;
	
	@Value("${gateway_port}")
	private int gateway_port;
	
	/**
	 * geteway UserManagerService bean
	 * @return
	 * @throws UnknownHostException
	 */
	@Bean
    public UserManagerService UserManagerService() throws UnknownHostException {
	/*	ProtocolRegister.registerProcotol(
                CommandIDs.vlsp_maUserManagerService.loginbymobileMethod,
                null,
                new UserManagerService_loginbymobileRequest(),
                new UserManagerService_loginbymobileResponse());
            
		ProtocolRegister.registerProcotol(
				  CommandIDs.vlsp_maUserManagerService.updatetokenMethod,
                null,
                new UserManagerService_updatetokenRequest(),
                new UserManagerService_updatetokenResponse());
            */
            UserManagerService service = new UserManagerService(
                    InetAddress.getByName(gateway_ip), gateway_port);
            
            return service;
    }
	
	/**
	 * geteway EntSystemManagerService bean
	 * @return
	 * @throws UnknownHostException
	 */
	@Bean
	public EntSystemManagerService entSystemManagerService() throws UnknownHostException{
		 ProtocolRegister.registerProcotol(
	                CommandIDs.vlsp_eaEntSystemManagerService.getuserstatusMethod,
	                null,
	                new EntSystemManagerService_getuserstatusRequest(),
	                new EntSystemManagerService_getuserstatusResponse());
        
		EntSystemManagerService service = new EntSystemManagerService(
                InetAddress.getByName(gateway_ip), gateway_port);
        
        return service;
	}
}
