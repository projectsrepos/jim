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

import com.jim.im.consts.TerminalStatus;
import com.jim.im.db.entity.login.TerminalSessionInfo;
import com.jim.im.exception.ImException;
import com.jim.im.exception.ImRpcCallException;
import com.jim.im.login.IMLoginserver;
import com.jim.im.login.rpc.LoginRpcServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.List;

/**
 * 
 * @version 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IMLoginserver.class)
public class RpcLoginServiceTest {
	@Autowired
	private LoginRpcServiceImpl service;

	@Autowired
	private UserManagerService userManagerService;
	
	private registerobj registerobj;

//	@Before
	public void init() throws AppException {
		registerobj = userManagerService.loginbymobile("1592015269303",
				"1008728");
	}

	//@Test
	public void testGetIosTokens() throws ImRpcCallException{
		List<String> ls = service.getIosTokens("", "", 642693);
		System.out.println(ls);
	}
	
	/**
	 * userID is failed
	 * @throws ImRpcCallException 
	 */
	//@Test
	public void testGetIosTokens1() throws ImRpcCallException{
		List<String> ls = service.getIosTokens("", "", 0);
		System.out.println(ls);
	}
	
	//@Test
	public void testupdateConnectStatus() throws ImException {
		service.updateConnectStatus(
				String.valueOf(registerobj.usernumber),
				TerminalStatus.ONLINE);
	}

	//@Test
	public void testupdateConnectStatus1() throws ImException {
		service.updateConnectStatus(
				String.valueOf(registerobj.usernumber),
				TerminalStatus.ONLINE);
	}

	//@Test
	public void testupdateConnectStatus2() throws ImException {
		service.updateConnectStatus(
				String.valueOf(registerobj.usernumber),
				TerminalStatus.ONLINE);
	}

	//@Test
	public void testupdateConnectStatus3() throws ImException {
		service.updateConnectStatus(
				String.valueOf(registerobj.usernumber),
				TerminalStatus.ONLINE);
	}

	//@Test
	public void testcheckConnectStatus() throws ImException {
		TerminalSessionInfo status = service.checkConnectStatus(
				String.valueOf(registerobj.usernumber));
		System.out.println(status);
	}

	//@Test
	public void testcheckConnectStatus1() throws ImException {
		TerminalSessionInfo status = service.checkConnectStatus("642693");
		System.out.println(status.getSessionID());
		System.out.println(status.getUserID());
		System.out.println(status.getOsType());
		System.out.println(status.getTerminalStatus());
		System.out.println(status.getDeviceToken());
	}

	//@Test
	public void testcheckConnectStatus2() throws ImException {
		TerminalSessionInfo status = service.checkConnectStatus(
				String.valueOf(registerobj.usernumber));
		System.out.println(status.toString());
	}

	//@Test
	public void testcheckConnectStatus3() throws ImException {
		TerminalSessionInfo status = service.checkConnectStatus(
				String.valueOf(registerobj.usernumber));
		System.out.println(status);
	}

	@Test
	public void testDemo() {

	}
}
