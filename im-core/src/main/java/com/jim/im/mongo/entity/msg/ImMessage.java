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
package com.jim.im.mongo.entity.msg;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jim.im.consts.TopicOwnerType;
import com.jim.im.db.base.Entity;
import com.jim.im.db.base.Tenantable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.math.BigInteger;

/**
 * 
 * @version 1.0.0
 */
@Document(collection = "msg")
public class ImMessage implements Entity, Tenantable {
    private static final long serialVersionUID = 6378465291393047297L;

    @Id
    @Field("_id")
    @JsonProperty("mid")
    private BigInteger id;
    /** 应用ID */
    @JsonIgnore
    private String appId;
    /** 租户ID */
    @JsonIgnore
    private String tenantId;
    /** 此消息发送对象，可以是user/group */
    @JsonIgnore
    private Integer targetId;
    @JsonProperty("t")
    private String fromId;
    /** 消息类型 */
    @JsonProperty("mt")
    private MsgType msgType;
    @JsonIgnore
    private TopicOwnerType topicOwnerType;
    @JsonProperty("m")
    private String content;
    @JsonIgnore
    private String shortContent;
    @Indexed
    @JsonIgnore
    private String topicName;
    @JsonIgnore
    private Integer priority;
    @JsonIgnore
    private boolean internal = false;
    @JsonProperty("ct")
    private long createTime = System.currentTimeMillis();

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public MsgType getMsgType() {
        return msgType;
    }

    public void setMsgType(MsgType msgType) {
        this.msgType = msgType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Override
    public BigInteger getId() {
        return id;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public String getShortContent() {
        return shortContent;
    }

    public void setShortContent(String shortContent) {
        this.shortContent = shortContent;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public TopicOwnerType getTopicOwnerType() {
        return topicOwnerType;
    }

    public void setTopicOwnerType(TopicOwnerType topicOwnerType) {
        this.topicOwnerType = topicOwnerType;
    }

    public boolean isInternal() {
        return internal;
    }

    public void setInternal(boolean internal) {
        this.internal = internal;
    }

    public IosMessage toIosMessage() {
        IosMessage iosMessage = new IosMessage();
        iosMessage.setId(id);
        iosMessage.setAppId(appId);
        iosMessage.setTenantId(tenantId);
        iosMessage.setTargetId(targetId);
        iosMessage.setPriority(priority);
        iosMessage.setPushContent(shortContent);
        return iosMessage;
    }

    public CommMessage toCommMessage() {
        return CommMessage.valueOf(id, targetId, content);
    }

    public ImMessage clone(Integer userId) {
        ImMessage imMessage = new ImMessage();
        imMessage.setAppId(appId);
        imMessage.setTenantId(tenantId);
        imMessage.setTopicName(topicName);
        imMessage.setMsgType(msgType);
        imMessage.setPriority(priority);
        imMessage.setTopicOwnerType(topicOwnerType);
        imMessage.setTargetId(userId);
        imMessage.setFromId(fromId);
        return imMessage;
    }
}
