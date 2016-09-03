/*
 * Copyright (c) 2016 by XuanWu Wireless Technology Co.Ltd. 
 *             All rights reserved                         
 */
package com.jim.im.test.performance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.jim.im.rest.response.RestResult;
import com.jim.im.test.utils.MD5Utils;

/**
 * 
 * @author <a href="huzhiwen@wxchina.com">huzhiwen</a>
 * @date 2016年6月1日
 * @version 1.0.0
 */
@Component
public class PushmsgPerformanceClient {
	@Value("${pushMsgUrl}")
	private String pushMsgUrl;
	
    @Value("${appId}")
    private String appId;
    
    @Value("${tenantId}")
    private String tenantId;
    
    @Value("${operatorId}")
    private String operatorId;
    
    @Value("${userIds}")
    private String userIds;
    
    @Value("${msgType}")
    private String msgType;
    
    @Value("${priority}")
    private String priority;
    
    public static ArrayBlockingQueue<Object[]> pushTimeQueue = new ArrayBlockingQueue<Object[]>(1000000);
    
    public List<Thread> pushThreadList = new ArrayList<Thread>();
    
    private static Log logger = LogFactory.getLog(PushmsgPerformanceClient.class);
    
    private static String pushSql = "insert into perfor_push_info(str,optime) values(?,?)";
    
	@Autowired
	private JdbcTemplate jdbcTemplate;
    
    /**
     * push Count msg
     * @param count
     */
	public void pushmsgCountRun(int publish_body_size,int interval,String receiverIds) {
		if(!StringUtils.isEmpty(receiverIds))
			userIds = receiverIds;
		Thread thread = new PushmsgThread(publish_body_size, interval);
		pushThreadList.add(thread);
		thread.start();
	}
	
	public void stoppush(){
		for(Thread thread : pushThreadList){
			while(!thread.isAlive());
			thread.interrupt();
		}
		pushThreadList.clear();
	}
	
	class PushmsgThread extends Thread{
		private int publish_body_size;
		private int interval;
		
		public PushmsgThread(int publish_body_size, int interval) {
			super();
			this.publish_body_size = publish_body_size;
			this.interval = interval;
		}

		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			while(!isInterrupted()) {
				try {
					messagePush(publish_body_size);
					if(interval >0)
						Thread.currentThread().sleep(interval);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			logger.info("push message thread was interrupted!");
		}
	}
    
	/**
	 * message restfull Push
	 */
	public void messagePush(int publish_body_size){
		try {
			byte[] content = BodyProduce.randomLengthString(publish_body_size);
			String con = new String(content);
	        Map<String, Object> body = new HashMap<String, Object>();
	        body.put("receiverIds", userIds);
	        body.put("content", con);
	        body.put("shortContent", con);
	        
	        Client client= JerseyClientBuilder.newBuilder().build();
	        long time = System.currentTimeMillis();
//	        
	        Response rsp = client.target(pushMsgUrl)
	        		.request().accept(MediaType.APPLICATION_JSON)
	        		.header("appId", appId)
	        		.header("tenantId", tenantId)
	        		.header("operatorId", operatorId)
	        		.buildPost(Entity.entity(body, MediaType.APPLICATION_JSON)).invoke();
	        String ret = rsp.readEntity(String.class);
	        JSONObject object = new JSONObject(ret);
	        JSONArray json = object.getJSONArray("data");
	        if(json==null)
	        	return ;
	        String id ;
	        for(int i = 0 ; i <json.length();i++){
	        	id = json.getString(i);
	        	Object[] str = {id,time};
		        jdbcTemplate.update(pushSql, str);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
