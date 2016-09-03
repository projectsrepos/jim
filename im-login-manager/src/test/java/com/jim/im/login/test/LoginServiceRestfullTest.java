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
package com.jim.im.login.test;

import com.google.common.collect.Maps;
import com.jim.im.login.service.RedisClientService;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

/**
 * 
 * @version 1.0.0
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = IMLoginserver.class)
//@WebIntegrationTest
public class LoginServiceRestfullTest {
	@Autowired
	private UserManagerService userManagerService;
	
	private RestTemplate template = new TestRestTemplate();

	private String getServerUrl = "http://localhost:8091/api/im/loginService/getServer";

	private registerobj registerobj;
	@Autowired
	private RedisClientService redisClientService;

	//@Test
	public void testAddUserInfo(){
		Map<Object, Object> keyValueMap = redisClientService.getKeyValueMap("1000");
		Map<String, String> map = Maps.newHashMap();
		for (Map.Entry<Object, Object> entry : keyValueMap.entrySet()) {
			map.put(entry.getKey().toString(), entry.getValue().toString());
		}

		for (Integer index = 0; index < 1; index++) {
			map.put("userID", index.toString());
			redisClientService.updateMap(index.toString(), map);
		}
	}

	//@Before
	public void init() throws AppException {
		registerobj = userManagerService.loginbymobile("1592015269303",
				"1008728");
		userManagerService.updatetoken(registerobj.sessionid, 
				"76dfd63df6914c06dadfb1b91498805fa35b3786e2c57ef1a8bea29e030b930d", null);
	}

	//@Test
	public void testGetServer() {
		String url = getServerUrl+"?sessionID="
				+registerobj.sessionid
				+"&userID="+registerobj.usernumber;
		String result = template
				.getForObject(url, String.class);
		System.out.println(registerobj.usernumber);
		System.out.println("LoginServiceRestfullTest.testGetServer():");
		System.out.println(result);
		Assert.assertNotNull(result);
	}

	/**
	 * getServer sessionID为空
	 */
	 //@Test
	public void testGetServer1() {
		String url = getServerUrl+"?sessionID=&userID="+registerobj.usernumber;
		String result = template
				.getForObject(url, String.class);
		System.out.println("LoginServiceRestfullTest.testGetServer1():");
		System.out.println(result);
		Assert.assertNotNull(result);
	}

	/**
	 * getServer userID为空
	 */
	//@Test
	public void testGetServer2() {
		String url = getServerUrl+"?sessionID="
				+registerobj.sessionid
				+"&userID=";
		String result = template
				.getForObject(url, String.class);
		System.out.println("LoginServiceRestfullTest.testGetServer2() :");
		System.out.println(result);
		Assert.assertNotNull(result);
	}

	/**
	 * getServer sessionID和userID 不匹配
	 */
	 //@Test
	public void testGetServer3() {
		String url = getServerUrl+"?sessionID="
				+registerobj.sessionid
				+"&userID=111";
		String result = template
				.getForObject(url, String.class);
		System.out.println("LoginServiceRestfullTest.testGetServer3():");
		System.out.println(result);
		Assert.assertNotNull(result);
	}

}
