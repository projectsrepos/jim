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
package com.jim.im.group.rpc;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jim.im.db.entity.group.User;
import com.jim.im.exception.ImException;
import com.jim.im.group.service.UserService;

/**
 * 
 * @version 1.0.0
 */
@Component
public class UserRegisterListener implements MessageListener{
	
	@Autowired
	UserService userService;
	
	private static Log logger = LogFactory.getLog(UserRegisterListener.class);

	/* (non-Javadoc)
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	@Override
	public void onMessage(Message arg0) {
		BytesMessage bytesmsg = (BytesMessage) arg0;
		int length;
		try {
			length = (int) bytesmsg.getBodyLength();
			byte[] bytes = new byte[length];
			bytesmsg.readBytes(bytes);
//			ProtocolStream stream = new ProtocolStream(bytes);
//			registerobj obj = registerobjSerializer.getregisterobj(stream);
			User user = new User();
			Integer usernumber = 1;
			user.setGatewayUserId(usernumber);
			user.setAppId("sfa");
			user.setTenantId("hengda");
			userService.createUser(user);
			logger.info("UserRegisterListener.onMessage arrived user :"+usernumber);
		} catch (JMSException e) {
			e.printStackTrace();
		} catch (ImException e) {
			e.printStackTrace();
		}
		
	}
}
