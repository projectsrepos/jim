/*
 * Copyright (c) 2016 by XuanWu Wireless Technology Co.Ltd. 
 *             All rights reserved                         
 */
package com.jim.im.test.performance;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONObject;

import com.jim.im.test.utils.HttpClienUtils;

/**
 * 
 * @author <a href="huzhiwen@wxchina.com">huzhiwen</a>
 * @date 2016年6月21日
 * @version 1.0.0
 */
public class TestStrategyThread extends Thread {
	long initConnect;
	long maxConnect;
	int interval;
	String[] clientIps;
	PushmsgPerformanceClient pushmsgClient;
	public TestStrategyThread(long initConnect, long maxConnect, 
			int interval,String[] clientIps,
			PushmsgPerformanceClient pushmsgClient) {
		super();
		this.initConnect = initConnect;
		this.maxConnect = maxConnect;
		this.interval = interval;
		this.clientIps=clientIps;
		this.pushmsgClient=pushmsgClient;
	}
	
	@Override
	public void run() {
		int cl = clientIps.length;
		long mCount = initConnect/cl;
		long maxCount = maxConnect/cl;
		int increment = 1000/cl;
		while(true){
			if(mCount>maxCount)
				break;
			try{
				pushmsgClient.stoppush();
				for(int i=0;i<clientIps.length;i++){
					connectMqtt(clientIps[i], mCount, i+"clientgroup");
				}
				mCount+=increment;
				sleep(1*60*1000);
//				pushmsgClient.pushmsgCountRun(10, interval);
				sleep(10*60*1000);
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
	}
	
	

	private boolean connectMqtt(String ip,long connectcount,String preClientID){
		String url = "http://"+ip+":8100/api/im/testService/connectMqtt?connectcount="+connectcount+"&preClientID"+preClientID;
		try {
			JSONObject json = HttpClienUtils.get(url);
			if("1".equals(json.getString("status"))){
				return true;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
