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

import com.jim.im.consts.TopicOwnerType;
import com.jim.im.consts.TopicType;
import com.jim.im.db.base.Entity;
import com.jim.im.db.base.GenericEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import java.util.Date;

/**
 * 主题信息
 * 
 * @version 1.0.0
 */
public class TopicInfo extends GenericEntity implements Entity {

    private static final long serialVersionUID = -7981985473742520513L;

    /** topic 自增主键 */
    private Integer id;

    /** topic 名字 */
    private String name;

    /** topic 所有者类型 */
    private TopicOwnerType topicOwnerType;

    /** topic 类型 */
    private TopicType topicType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public TopicOwnerType getTopicOwnerType() {
        return topicOwnerType;
    }

    public void setTopicOwnerType(TopicOwnerType topicOwnerType) {
        this.topicOwnerType = topicOwnerType;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public TopicType getTopicType() {
        return topicType;
    }

    public void setTopicType(TopicType topicType) {
        this.topicType = topicType;
    }

    public static TopicInfo build(Integer ownerId,
                                  String appId,
                                  String tenantId,
                                  TopicOwnerType topicOwnerType,
                                  TopicType topicType,
                                  String topicName) {
        TopicInfo info = new TopicInfo();
        info.setAppId(appId);
        info.setTenantId(tenantId);
        info.setCreatedBy(ownerId);
        info.setTopicOwnerType(topicOwnerType);
        info.setTopicType(topicType);
        info.setName(topicName);

        return info;
    }

    public static TopicInfo build(Integer ownerId, String appId, String tenantId) {
        return build(ownerId, appId, tenantId, null, null, null);
    }
}
