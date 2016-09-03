/*
 * Copyright (c) 2016. All rights reserved by XuanWu Wireless Technology Co.Ltd.
 */
package com.jim.im.test.mqtt.handle;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**  
 * mqtt消息处理接口
 * @author <a herf="huzhiwen@wxchina.com">huzhiwen</a>
 * @version 1.0.0   
 * @since 2016年4月12日
 */
public interface IMessageReceivedHandle {
    /**
    * @Title: handle  
    * @Description: 处理接受到的消息
    * @param mqttClient
    * @param topic
    * @param message
     */
    void handle(MqttClient mqttClient ,String topic,MqttMessage message);
}
