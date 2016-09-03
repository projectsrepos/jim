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

import com.jim.im.consts.TopicType;
import com.jim.im.db.base.Entity;
import com.jim.im.db.base.GenericEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import java.io.Serializable;

/**
 */
public class TopicSubMap extends GenericEntity implements Entity {
    private static final long serialVersionUID = 1512389595557078645L;

    private Integer id;

    /** topic ID */
    private Integer topicId;

    /** topic(名冗余字段) */
    private String topicName;

    private TopicType topicType;

    /** 订阅者ID */
    private Integer subId;

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public Integer getSubId() {
        return subId;
    }

    public void setSubId(Integer subId) {
        this.subId = subId;
    }

    public TopicType getTopicType() {
        return topicType;
    }

    public void setTopicType(TopicType topicType) {
        this.topicType = topicType;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public Serializable getId() {
        return this.id;
    }

    public static TopicSubMap build(Integer userId,
                                    String appId,
                                    String tenantId,
                                    String topicName,
                                    Integer topicId,
                                    TopicType topicType) {
        TopicSubMap topicSubMap = new TopicSubMap();
        topicSubMap.setAppId(appId);
        topicSubMap.setTenantId(tenantId);
        topicSubMap.setCreatedBy(userId);
        topicSubMap.setSubId(userId);
        topicSubMap.setTopicName(topicName);
        topicSubMap.setTopicId(topicId);
        topicSubMap.setTopicType(topicType);

        return topicSubMap;
    }
}
