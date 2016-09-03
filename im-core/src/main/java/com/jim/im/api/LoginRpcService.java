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
package com.jim.im.api;

import com.jim.im.consts.TerminalStatus;
import com.jim.im.db.entity.login.TerminalSessionInfo;
import com.jim.im.exception.ImException;
import com.jim.im.exception.ImRpcCallException;
import java.util.List;

/**
 * 登录服务rpc接口
 * 
 * @author huzhiwen
 * @version V1.0.0
 */
public interface LoginRpcService {

	/**
	 * 跟新连接状态接口
	 * 
	 * @param im_id
	 * @param status
	 * @throws ImRpcCallException
	 * @throws ImException
	 */
	void updateConnectStatus(String im_id, TerminalStatus status)
			throws ImRpcCallException, ImException;

	/**
	 * 终端状态接口
	 * @param im_id
	 * @return
	 * @throws ImRpcCallException
	 * @throws ImException
	 */
	TerminalSessionInfo checkConnectStatus(String im_id)
			throws ImRpcCallException, ImException;

	/**
	 * get Ios Tokens
	 * 
	 * @param appId
	 * @param tenantId
	 * @param userId
	 * @return
	 * @throws ImRpcCallException
	 */
	List<String> getIosTokens(String appId, String tenantId, int userId)
			throws ImRpcCallException;

}
