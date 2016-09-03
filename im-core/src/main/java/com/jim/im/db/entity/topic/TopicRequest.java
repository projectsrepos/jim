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
package com.jim.im.db.entity.topic;

import java.io.Serializable;

import com.jim.im.consts.TopicOwnerType;
import com.jim.im.consts.TopicType;

/**
 */
public class TopicRequest implements Serializable {

    /** 操作者ID(主题所有者ID/用户ID) */
    private Integer operatorId;
    /** 应用ID */
    private String appId;
    /** 租户ID */
    private String tenantId;
    /** 主题所有者类型 */
    private TopicOwnerType topicOwnerType;
    /** 主题类型 */
    private TopicType[] topicTypes;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public TopicOwnerType getTopicOwnerType() {
        return topicOwnerType;
    }

    public void setTopicOwnerType(TopicOwnerType topicOwnerType) {
        this.topicOwnerType = topicOwnerType;
    }

    public TopicType[] getTopicTypes() {
        return topicTypes;
    }

    public void setTopicTypes(TopicType[] topicTypes) {
        this.topicTypes = topicTypes;
    }

    public static TopicRequest build(Integer ownerId,
                                     String appId,
                                     String tenantId,
                                     TopicOwnerType topicOwnerType,
                                     TopicType... topicTypes) {
        TopicRequest request = new TopicRequest();
        request.setAppId(appId);
        request.setTenantId(tenantId);
        request.setOperatorId(ownerId);
        request.setTopicOwnerType(topicOwnerType);
        if (topicTypes != null)
            request.setTopicTypes(topicTypes);

        return request;
    }
}
