/*
 * Copyright (c) 2016. All rights reserved by XuanWu Wireless Technology Co.Ltd.
 */
package com.jim.im.test.mqtt.handle;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**  
 * Common topic handle
 * @author <a herf="huzhiwen@wxchina.com">huzhiwen</a>
 * @version 1.0.0   
 * @since 2016年4月13日
 */
public class CommonMsgReceivedHandle implements IMessageReceivedHandle {

    /* (non-Javadoc)
     * @see com.jim.im.test.mqtt.handle.IMessageReceivedHandle#handle(org.eclipse.paho.client.mqttv3.MqttClient, java.lang.String, org.eclipse.paho.client.mqttv3.MqttMessage)
     */
    @Override
    public void handle(MqttClient mqttClient, String topic, MqttMessage message) {
        System.out.println("Common topic Message Handle: \"" + message.toString()  
                + "\" on topic \"" + topic.toString() + "\" for instance \""  
                 + "\"");  
//        try {
//            mqttClient.publish("/default",  new MqttMessage("default msg test".getBytes()));
//        } catch (Exception e) {
//            e.printStackTrace();
//        } 
    }

}
