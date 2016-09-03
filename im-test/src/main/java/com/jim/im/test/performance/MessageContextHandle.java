/*
 * Copyright (c) 2016 by XuanWu Wireless Technology Co.Ltd. 
 *             All rights reserved                         
 */
package com.jim.im.test.performance;

import org.glassfish.jersey.client.JerseyClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * 
 * @author <a href="huzhiwen@wxchina.com">huzhiwen</a>
 * @date 2016年6月27日
 * @version 1.0.0
 */
@Component
public class MessageContextHandle implements MsgHandleInterface {
	
	@Value("${offlineMassagesURL}")
	String offlineMassagesURL;
	
	@Value("${appId}")
    String appId;
    
    @Value("${tenantId}")
    String tenantId;
    
    @Value("${operatorId}")
    String operatorId;
    
    
    public static ArrayBlockingQueue<Object[]> msgQueue = new ArrayBlockingQueue<Object[]>(1000000);

	/* (non-Javadoc)
	 * @see com.jim.im.test.performance.MsgHandleInterface#handle(java.lang.String)
	 */
	@Override
	public void handle(String msg_ID,String topic) {
		try {
			Client client= JerseyClientBuilder.newBuilder().build();
			Map<String ,Object> header = new HashMap<String, Object>();
			header.put("appId", appId);
			header.put("tenantId", tenantId);
			header.put("operatorId", operatorId);
			
			Map<String, Object> body = new HashMap<String, Object>();
			body.put("lastMsgId", msg_ID);
			String[] topics = {topic};
			body.put("topics",topics);
	        Response rsp = client.target(offlineMassagesURL)
	        		.request()
	        		.accept(MediaType.APPLICATION_JSON)
	        		.headers(new MultivaluedHashMap<String, Object>(header))
	        		.buildPost(Entity.entity(body, MediaType.APPLICATION_JSON))
	        		.invoke();
	        HashMap<String, Object> ret = rsp.readEntity(HashMap.class);
	        Object[] obj = {msg_ID,System.currentTimeMillis(),1};
	        if(!"1".equals(ret.get("status").toString())){
				obj[2]=0;
			}
	        msgQueue.put(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
