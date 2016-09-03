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
package com.jim.im.task.job;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jim.im.exception.ImException;
import com.jim.im.task.entity.TaskInfo;

/**
 * im push msg job handle
 * 
 * @version 1.0.0
 */
@Component
public class IMPushMsgJobHandle extends AbstractJobHandle {
	@Value("${im.task.pushmsgByGroup.url}")
	private String pushmsgByGroupUrl;

	@Value("${im.task.pushmsg.url}")
	private String pushmsgUrl;

	private static Log logger = LogFactory
			.getLog(IMPushMsgJobHandle.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jim.im.task.job.AbstractJobHandle#handle(com.jim.im.task.job
	 * .JobParamer)
	 */
	@Override
	void handle(Object objectBean) throws Exception {
		TaskInfo info = (TaskInfo) objectBean;
		logger.info(info.toString());

		Map<String, Object> header = headerBuild(info);
		Map<String, Object> body = bodyBuild(info);
		Map<String, Object> resultJson = null;
		if ("group".equals(info.getPushType())) {
			resultJson = pushmsgByGroup(header, body);
		} else if ("user".equals(info.getPushType())) {
			resultJson = pushmsg(header, body);
		}
		
		if (resultJson == null
				|| !"1".equals(resultJson.get("status").toString())) {
			throw new ImException(
					"http restfull push message  response status with :"
							+ resultJson.get("status"));
		}

	}

	/**
	 * push message by user
	 * 
	 * @param header
	 * @param body
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private Map<String, Object> pushmsg(Map<String, Object> header,
			Map<String, Object> body){
	 	Client client= JerseyClientBuilder.newBuilder().build();
	        
        Response rsp = client.target(pushmsgUrl)
        		.request()
        		.accept(MediaType.APPLICATION_JSON)
        		.headers(new MultivaluedHashMap<String, Object>(header))
        		.buildPost(Entity.entity(body, MediaType.APPLICATION_JSON))
        		.invoke();
        HashMap<String, Object> ret = rsp.readEntity(HashMap.class);
		return ret;
	}

	/**
	 * push message by group
	 * 
	 * @param header
	 * @param body
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private Map<String, Object> pushmsgByGroup(Map<String, Object> header,
			Map<String, Object> body){
		Client client= JerseyClientBuilder.newBuilder().build();
        Response rsp = client.target(pushmsgByGroupUrl)
        		.request()
        		.accept(MediaType.APPLICATION_JSON)
        		.headers(new MultivaluedHashMap<String, Object>(header))
        		.buildPost(Entity.entity(body, MediaType.APPLICATION_JSON))
        		.invoke();
        HashMap<String, Object> ret = rsp.readEntity(HashMap.class);
		return ret;
	}

	/**
	 * Build request header
	 * 
	 * @param info
	 * @return
	 */
	private Map<String, Object> headerBuild(TaskInfo info) {
		Map<String, Object> header = new HashMap<String, Object>();
		header.put("appId", info.getAppId());
		header.put("tenantId", info.getTenantId());
		header.put("operatorId", info.getOperatorid());
		return header;
	}

	/**
	 * Build request body
	 * 
	 * @param info
	 * @return
	 */
	private Map<String, Object> bodyBuild(TaskInfo info) {
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("receiverIds", info.getPushIds());
		body.put("content", info.getContent());
		body.put("shortContent", info.getPushContent());
		return body;
	}

}
