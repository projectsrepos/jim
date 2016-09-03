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
package com.jim.im.broker.handler;

import com.jim.im.api.LoginRpcService;
import com.jim.im.consts.TerminalStatus;
import com.jim.im.exception.ImException;
import com.jim.im.exception.ImRpcCallException;
import io.moquette.broker.interception.AbstractInterceptHandler;
import io.moquette.broker.interception.messages.InterceptConnectMessage;
import io.moquette.broker.interception.messages.InterceptDisconnectMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @date 2016年5月9日
 * @version 1.0.0
 */
public class LoginInterceptHandler extends AbstractInterceptHandler {
	private final static Logger LOGGER = LoggerFactory.getLogger(LoginInterceptHandler.class);

	private LoginRpcService loginRpcService;

	public LoginInterceptHandler(LoginRpcService loginRpcService) {
		this.loginRpcService = loginRpcService;
	}

	@Override
	public void onConnect(InterceptConnectMessage msg) {
		try {
			loginRpcService.updateConnectStatus(msg.getClientID(), TerminalStatus.ONLINE);
		} catch (ImRpcCallException e) {
			LOGGER.error("Rpc call failure.", e);
		} catch (ImException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	@Override
	public void onDisconnect(InterceptDisconnectMessage msg) {
		try {
			loginRpcService.updateConnectStatus(msg.getClientID(), TerminalStatus.OFFLINE);
		} catch (ImRpcCallException e) {
			LOGGER.error("Rpc call failure.", e);
		} catch (ImException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
}
