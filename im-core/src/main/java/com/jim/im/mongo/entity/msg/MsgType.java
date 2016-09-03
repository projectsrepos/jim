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

import com.jim.im.consts.TopicType;

/**
 * 消息类型<br/>
 *
 * <i>注意：推送和系统类型的主题信息只对Android生效，IOS走APN</i>
 * 
 * @version 1.0
 */
public enum MsgType {
    /** 仅推送消息 */
    PUSH(TopicType.PUSH),
    /** 仅发送聊天消息 */
    CHAT(TopicType.CHAT),
    /** 仅系统消息 */
    SYSTEM(TopicType.SYS);

    private TopicType topicType;
    private MsgType(TopicType topicType) {
        this.topicType = topicType;
    }

    public TopicType getTopicType() {
        return this.topicType;
    }
}
