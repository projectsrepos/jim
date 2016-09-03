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
package com.jim.im.login.rest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.jms.JMSException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//import com.jim.easyinfo.accountservice.protocol.service.EntSystemManagerService;
//import com.jim.easyinfo.eimodel.dictionaryobj;
//import com.jim.easyinfo.eimodel.rowobj;
import com.jim.im.consts.IMConstant;
import com.jim.im.consts.OsType;
import com.jim.im.consts.TerminalStatus;
import com.jim.im.exception.ImException;
import com.jim.im.exception.ImParamException;
import com.jim.im.login.service.RedisClientServiceImpl;
import com.jim.im.login.util.GatewayUserstatusEnum;
import com.jim.im.rest.response.RestResult;

/**
 * 登录服务restFull接口
 * 
 * @version V1.0.0
 */
@Component
@Path("/")
public class LoginRestService {

	@Autowired
	private RedisClientServiceImpl redisService;

	@Autowired
	private EntSystemManagerService entSystemManagerService;

	/**
	 * @Title: getServer
	 * @Description: 4.2.2.2.1 获取连接服务器IP接口
	 * @param sessionID
	 * @throws ImException
	 * @return Response
	 * @throws JMSException
	 * @throws AppException
	 */
	@GET
	@Path("getServer")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getServer(@QueryParam("sessionID") String sessionID,
			@QueryParam("userID") int userID) throws ImException {

		checkParamValid(sessionID, userID);

		Map<String, String> userinfo = getUserStatus(sessionID, userID);
		if(userinfo == null || userinfo.size() == 0){
			return RestResult.failure("2001", "Session or userID failed!");
		}
		userinfo.put("sessionID", sessionID);
		storeSessionInfo(userID, userinfo);

		// 返回数据
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("ip", redisService.getServerIpString(String.valueOf(userID)));
		return RestResult.success(map);
	}

	/**
	 * 检查参数是否合法
	 * 
	 * @param sessionID
	 * @param userID
	 * @throws ImParamException
	 */
	private void checkParamValid(String sessionID, int userID)
			throws ImParamException {
		if (StringUtils.isEmpty(sessionID))
			throw new ImParamException("sessionID must be not null");

		if (userID == 0)
			throw new ImParamException("userID must be not null");
		
		try {
			UUID.fromString(sessionID);
		} catch (Exception e) {
			throw new ImParamException("sessionID must is UUID String",e);
		}
		
	}

	/**
	 * 通过sessionID和userID在网关获得sessionInfo
	 * 
	 * @param sessionID
	 * @param userID
	 * @return
	 * @throws ImParamException
	 */
	private Map<String, String> getUserStatus(String sessionID, int userID)
			throws ImParamException {
		Map<String, String> map = null;
		try {
			// 网关验证及获取userinfo
			dictionaryobj[] paras = new dictionaryobj[1];
			paras[0] = new dictionaryobj();
			paras[0].Itemcode = "usernumbers";
			paras[0].Itemname = String.valueOf(userID);
			rowobj[] rowobjs = entSystemManagerService.getuserstatus(
					UUID.fromString(sessionID), userID, paras);
			if (rowobjs != null && rowobjs.length != 0) {
				map = new HashMap<String, String>();
				dictionaryobj[] objs = rowobjs[0].Values;
				for (dictionaryobj retobj : objs) {
					String key = retobj.Itemcode;
					String value = retobj.Itemname;
					if (value == null) {
						value = "";
					}

					if (GatewayUserstatusEnum.clienttypeno.name().equals(key)
							|| GatewayUserstatusEnum.clienttypename.name()
									.equals(key)
							|| GatewayUserstatusEnum.clienttypeno.name()
									.equals(key)
							|| GatewayUserstatusEnum.clientversionno.name()
									.equals(key)
							|| GatewayUserstatusEnum.devicetoken.name().equals(
									key)
							|| GatewayUserstatusEnum.versionname.name().equals(
									key)) {
						map.put(key, value);
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ImParamException(
					"get geteway userinfoobj fail by sessionID:" + sessionID
							+ ",userID:" + userID, e);
		}
		return map;
	}

	/**
	 * 缓存sessionInfo到redis
	 * 
	 * @param key
	 * @param obj
	 * @throws ImException
	 */
	private void storeSessionInfo(int userID, Map<String, String> obj)
			throws ImException {
		try {
			// 缓存到redis
			Map<String, String> param = new HashMap<String, String>();
			param.put("userID", String.valueOf(userID));
			param.putAll(obj);
			String clienttypeno = obj.get(GatewayUserstatusEnum.clienttypeno
					.name());
			if (IMConstant.GATEWAY_CLIENTTYPENO_ANDROID.equals(clienttypeno)) {
				param.put("OsType", OsType.ANDROID.name());
			} else if (IMConstant.GATEWAY_CLIENTTYPENO_IOS.equals(clienttypeno)) {
				param.put("OsType", OsType.IOS.name());
			} else {
				param.put("OsType", "");
			}

			param.put("TerminalStatus", TerminalStatus.READING.name());

			redisService.savaMap(String.valueOf(userID), param);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ImException("Store session to redis fail!", e);
		}

	}
}
