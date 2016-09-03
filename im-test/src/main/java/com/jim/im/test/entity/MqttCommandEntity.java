/*
 * Copyright (c) 2016. All rights reserved by XuanWu Wireless Technology Co.Ltd.
 */
package com.jim.im.test.entity;

import org.apache.commons.lang3.ArrayUtils;



/**  
 * mqtt 客户端操作实体
 * @author <a herf="huzhiwen@wxchina.com">huzhiwen</a>
 * @version 1.0.0   
 * @since 2016年4月13日
 */
public class MqttCommandEntity {
    private String url;
    private String clientID;
    private String command;
    private String content;
    private int qos;
    private String topic[];
    private boolean retained;
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getClientID() {
        return clientID;
    }
    public void setClientID(String clientID) {
        this.clientID = clientID;
    }
    public String getCommand() {
        return command;
    }
    public void setCommand(String command) {
        this.command = command;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String[] getTopic() {
        return topic;
    }
    public void setTopic(String[] topic) {
        this.topic = topic;
    }
    public int getQos() {
        return qos;
    }
    public void setQos(int qos) {
        this.qos = qos;
    }
    public boolean isRetained() {
        return retained;
    }
    public void setRetained(boolean retained) {
        this.retained = retained;
    }
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return command.concat(";").concat(url)
                .concat(";").concat(content)
                .concat(";").concat(ArrayUtils.toString(topic))
                .concat(";").concat(String.valueOf(qos))
                .concat(";").concat(String.valueOf(retained));
    }
    
    
    
}
