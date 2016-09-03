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
package com.jim.im.login.rpc;

import com.jim.im.api.LoginRpcService;
import com.jim.im.consts.OsType;
import com.jim.im.consts.TerminalStatus;
import com.jim.im.db.entity.login.TerminalSessionInfo;
import com.jim.im.exception.ImException;
import com.jim.im.exception.ImParamException;
import com.jim.im.exception.ImRpcCallException;
import com.jim.im.login.service.RedisClientServiceImpl;
import com.jim.im.login.util.GatewayUserstatusEnum;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * LoginServiceRpcHandle 登录服务RPC接口实现
 * 
 * @version V1.0.0
 */
@Component
public class LoginRpcServiceImpl implements LoginRpcService {
	@Autowired
	private RedisClientServiceImpl redisClient;

	private Log logger = LogFactory.getLog(this.getClass());

	@Override
	public List<String> getIosTokens(String appId, String tenantId, int userId)
			throws ImRpcCallException {
		logger.info("RpcLoginServiceImpl.getIosTokens(" + appId + ","
				+ tenantId + "," + userId + ")");
		List<String> ls = new ArrayList<String>();
		
		Map<Object, Object> map = redisClient.getKeyValueMap(String.valueOf(userId));
		
		if (map == null || map.size()==0) {
			return ls;
		}
		
		//ANDROID 判断
		String osType = (String) map.get("OsType");
		if(OsType.ANDROID.name().equals(osType)){
			return ls;
		}
		
		String token = (String) map.get(
				GatewayUserstatusEnum.devicetoken.name());
		if(token != null && token.trim().length()>0)
			ls.add(token);

		return ls;
	}

	@Override
	public void updateConnectStatus(String im_id, TerminalStatus status)
			throws ImException {

		logger.info("LoginRpcService.updateConnectStatus(" + im_id + ","
				+ status + ");");
		if (StringUtils.isEmpty(im_id))
			throw new ImParamException("参数不能为null");

		Map<String, String> map = new HashMap<String, String>();
		map.put("TerminalStatus", status.name());

		redisClient.updateMap(im_id, map);
	}

	@Override
	public TerminalSessionInfo checkConnectStatus(String im_id) 
			throws ImException {
		logger.info("LoginRpcService.checkConnectStatus(" + im_id + ");");

		checkParamNull(im_id);

		return checkUserSession(im_id);
	}

	/**
	 * 检查用户是否已经登录,并返回终端状态信息
	 * 
	 * @param im_id
	 * @throws ImException
	 */
	private TerminalSessionInfo checkUserSession(String im_id) throws ImException {
		Map<Object, Object> map = redisClient.getKeyValueMap(im_id);
		if(map == null)
			return null;
		return redisMap2Info(map);
	}
	
	/**
	 * redis result Map resove to bean Info
	 * @param map
	 * @return
	 */
	private TerminalSessionInfo redisMap2Info(Map<Object, Object> map){
		TerminalSessionInfo info = new TerminalSessionInfo();
		String osType = (String) map.get("OsType");
		String sessionID = (String) map.get("sessionID");
		String devicetoken = (String) map.get("devicetoken");
		String userID = (String) map.get("userID");
		String terminalStatus = (String) map.get("TerminalStatus");
		info.setUserID(userID);
		info.setSessionID(UUID.fromString(sessionID));
		info.setDeviceToken(StringUtils.isEmpty(devicetoken)?null:devicetoken);
		info.setTerminalStatus(
				StringUtils.isEmpty(terminalStatus)
				? null:TerminalStatus.valueOf(terminalStatus));
		info.setOsType(StringUtils.isEmpty(osType)?OsType.NONE:OsType.valueOf(osType));
		return info;
	}

	/**
	 * 检验空值参数
	 * @param params
	 * @throws ImParamException
	 */
	private static void checkParamNull(Object... params) throws ImParamException {
		if(params == null)
			return;
		
		for(Object param : params){
			if(param == null || StringUtils.isEmpty(param.toString().trim())){
				throw new ImParamException("参数不能为null");
			}
		}
	}
}
