/*
 * Copyright (c) 2016. All rights reserved by XuanWu Wireless Technology Co.Ltd.
 */
package com.jim.im.test.performance;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jim.im.test.utils.MD5Utils;

/**
 * MqttClient Factory
 * 
 * @author <a herf="huzhiwen@wxchina.com">huzhiwen</a>
 * @version 1.0.0
 * @since 2016年4月12日
 */
@Component
public class MqttPerformanceClient {
	@Value("${broker_address}")
	private String broker_address;

	@Value("${topics}")
	private String[] topics;

	private static Log logger = LogFactory.getLog(MqttPerformanceClient.class);

	public static ConcurrentHashMap<String, MqttClient> clientMap = new ConcurrentHashMap<String, MqttClient>();

	public static ArrayBlockingQueue<Object[]> arriveQueue = new ArrayBlockingQueue<Object[]>(
			100000000);
	@Value("${preClientID}")
	private String preClientID = "testClient_";
	
	@Value("${clientCount}")
	private int clientCount;
	
	@Value("${isInitClient}")
	private boolean isInitClient; 
	
	@Value("${connectThreadCount}")
	private int connectThreadCount;
	
	public static volatile int isInitConnect = 0;
	
	public List<MsgHandleInterface> handleList = new ArrayList<MsgHandleInterface>();
	
	public List<String> checkIsConnected(){
		List<String> disConnectIds = new ArrayList<String>();
		if(clientMap.size()==0)
			return disConnectIds;
		
		for(Entry<String, MqttClient> entry : clientMap.entrySet()){
			if(!entry.getValue().isConnected()){
				disConnectIds.add(entry.getKey());
			}
		}
		return disConnectIds;
	}
	
	@PostConstruct
	public void init(){
		connectThreadCount = connectThreadCount<1?1:connectThreadCount;
		if(isInitClient)
			for(int i = 0 ;i < connectThreadCount ;i++)
				connectThreadRun(i+preClientID, clientCount, topics);
	}

	/**
	 * connect Mqtt
	 * @param mqttClientCount is Client size
	 */
	public void connectMqtt(String preClientID,long mqttClientCount,String subID) {
		this.preClientID = preClientID;
		if (mqttClientCount <= 0) {
			return;
		}
		String topic = "/sfa/hengda/USER/"+subID+"/PUSH";
		connectThreadRun(preClientID, mqttClientCount, new String[]{topic});
	}
	
	
	private void connectThreadRun(String preClientID,long mqttClientCount,String[] topic){
		isInitConnect = 1;
		new Thread() {
			public void run() {
				try {
					
					long offset = clientMap.size();
					if(mqttClientCount>=offset){
						logger.info("add mqtt client starting! count:"+(mqttClientCount-offset));
						initMqttClient(preClientID,offset,mqttClientCount-offset,topic);
					}else{
						logger.info("remove mqtt client starting! count:"+(offset-mqttClientCount));
						removeMqttClient(mqttClientCount,offset-mqttClientCount);
					}
					isInitConnect = 2;
					logger.info(mqttClientCount + " client is connected");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	private void removeMqttClient(long offset,long count){
		long end = offset+count;
		for (long i = offset; i < end; i++) {
			close(preClientID+i);
		}
	}

	/**
	 * init Mqtt Client
	 * @param mqtt_connect_count is connect size
	 */
	private void initMqttClient(String preClientID,long offset,long mqtt_connect_count,String topics[]) {
		logger.info("MqttPerformanceClient.performanceTestRun() starting");
		long end = offset+mqtt_connect_count;
		for (long i = offset; i < end; i++) {
			try {
				MqttClient client = getMqttClient(preClientID + i);
				client.subscribe(topics);
				clientMap.put(preClientID + i, client);
				logger.info("connected by ID :"+preClientID + i);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.info("MqttPerformanceClient.performanceTestRun() end");
	}

	/**
	 * get MqttClient by clientKey
	 * 
	 * @param clientKey
	 * @return
	 * @throws MqttException
	 * @throws NoSuchAlgorithmException
	 */
	private MqttClient getMqttClient(String clientId) throws MqttException {
		MqttClientPersistence persistence = new MemoryPersistence();

		MqttClient client = new MqttClient(broker_address, clientId,
				persistence);
		
		MqttConnectOptions connOpts = new MqttConnectOptions();
		MqttCallback callback = new MqttCallback() {
			public void messageArrived(String topic, MqttMessage message)
					throws Exception {
				long arriveTime = System.currentTimeMillis();
				String msgID = message.toString();
				for(MsgHandleInterface handle : handleList)
					handle.handle(msgID,topic);
				Object[] str = {msgID,arriveTime};
				arriveQueue.put(str);
			}

			public void deliveryComplete(IMqttDeliveryToken token) {

			}

			public void connectionLost(Throwable cause) {
			}
		};
		client.setCallback(callback);
		connOpts.setKeepAliveInterval(3600);
		connOpts.setCleanSession(true);
		client.connect(connOpts);
		return client;
	}

	/**
	 * close all mqtt client
	 * @throws MqttException
	 */
	public void closeAll(){
		if (clientMap == null || clientMap.size() == 0) {
			return;
		}
		for(Entry<String, MqttClient> entry : clientMap.entrySet()){
			close(entry.getKey());
		}
		clientMap.clear();
	}
	
	private void close(String clientID){
		close(clientMap.get(clientID));
		clientMap.remove(clientID);
	}

	private void close(MqttClient client){
		try {
			if(client.isConnected())
				client.disconnect();
			logger.info("client disconnected! clientID:"+client.getClientId());
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	public int getClientCount() {
		return clientMap.size();
	}
}
