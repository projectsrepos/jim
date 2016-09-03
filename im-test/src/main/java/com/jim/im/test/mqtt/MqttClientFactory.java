/*
 * Copyright (c) 2016. All rights reserved by XuanWu Wireless Technology Co.Ltd.
 */
package com.jim.im.test.mqtt;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
* MqttClient Factory
* @author <a herf="huzhiwen@wxchina.com">huzhiwen</a>
* @version 1.0.0   
* @since 2016年4月12日
 */
public class MqttClientFactory{
    
    private static HashMap<String, MqttClient> clientMap = new HashMap<String, MqttClient>();
    
    private static final ReentrantLock lock = new ReentrantLock();
    
    public static final String redisArrivedPrefix = "MQTTARRIVED:";
    
    public final static String redisPrefix = "MQTTSEND:";
    
    /**
     * get MqttClient by clientKey
     * @param clientKey
     * @return
     * @throws MqttException
     */
    public static MqttClient getMqttClient(String serverURI, String clientId,StringRedisTemplate redisTemplate) 
                    throws MqttException{
    	 String clientKey=serverURI.concat(clientId);
         if(clientMap.get(clientKey)==null){
             lock.lock();
                 if(clientMap.get(clientKey)==null){
                	 MqttClientPersistence persistence = new MemoryPersistence();
                	
                     MqttClient client = new MqttClient(serverURI, clientId, persistence);
                     MqttConnectOptions connOpts = new MqttConnectOptions();
                     
                     MqttCallback callback = new IMMqttCallBack(client,redisTemplate);
                     client.setCallback(callback);
                     
                     connOpts.setCleanSession(true);
                     client.connect(connOpts);
                     clientMap.put(clientKey, client);
                 }
              lock.unlock();
         }
          return clientMap.get(clientKey);
    }
 
    /**
     *  client close  
     * @param clientKey
     * @throws MqttException
     */
    public static void close(
    		String serverURI, String clientId) 
            throws MqttException{
    	String clientKey=serverURI.concat(clientId);
        if(clientMap.get(clientKey)!=null){
            lock.lock();
            if(clientMap.get(clientKey)!=null){
                clientMap.get(clientKey).disconnect();
                clientMap.remove(clientKey);
            }
            lock.unlock();
        }
    }
}
