/*
 * Copyright (c) 2016. All rights reserved by XuanWu Wireless Technology Co.Ltd.
 */
package com.jim.im.test.mqtt.handle;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**  
 * 默认消息接受处理
 * @author <a herf="huzhiwen@wxchina.com">huzhiwen</a>
 * @version 1.0.0   
 * @since 2016年4月12日
 */
public class DefaultMsgReceivedHandle implements IMessageReceivedHandle {

    /* (non-Javadoc)
     * @see com.jim.im.test.mqtt.IMessageReceivedHandle#handle(java.lang.String, org.eclipse.paho.client.mqttv3.MqttMessage)
     */
    @Override
    public void handle(MqttClient mqttClient,String topic, MqttMessage message) {
        System.out.println("Default topic Message Handle: \"" + message.toString()  
                + "\" on topic \"" + topic.toString() + "\" for instance \""  
                 + "\"");  
    }

}
