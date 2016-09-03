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
package com.jim.im.rest.request.message;

import com.jim.im.consts.TopicOwnerType;
import com.jim.im.mongo.entity.msg.ImMessage;
import com.jim.im.mongo.entity.msg.MsgType;
import org.apache.commons.lang3.ArrayUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @version 1.0.0
 */
public class SysMsgRequest implements Serializable {
    private static final long serialVersionUID = 3315768003221888892L;

    private List<Integer> receiverIds = new ArrayList<>();
    private String appId;
    private String tenantId;
    private String content;
    private MsgType msgType;

    public String getAppId() {
        return appId;
    }

    public SysMsgRequest setAppId(String appId) {
        this.appId = appId;
        return this;
    }

    public String getContent() {
        return content;
    }

    public SysMsgRequest setContent(String content) {
        this.content = content;
        return this;
    }

    public List<Integer> getReceiverIds() {
        return receiverIds;
    }

    public SysMsgRequest setReceiverIds(List<Integer> receiverIds) {
        if (receiverIds != null)
            this.receiverIds.addAll(receiverIds);
        return this;
    }

    public String getTenantId() {
        return tenantId;
    }

    public SysMsgRequest setTenantId(String tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public MsgType getMsgType() {
        return msgType;
    }

    public SysMsgRequest setMsgType(MsgType msgType) {
        this.msgType = msgType;
        return this;
    }

    public List<Integer> addReceivers(Integer... receivers) {
        if (ArrayUtils.isNotEmpty(receivers))
            this.receiverIds.addAll(Arrays.asList(receivers));
        return this.receiverIds;
    }

    public ImMessage toImMessage() {
        ImMessage imMessage = new ImMessage();
        imMessage.setAppId(appId);
        imMessage.setTenantId(tenantId);
        imMessage.setContent(content);
        imMessage.setMsgType(MsgType.SYSTEM);
        imMessage.setTopicOwnerType(TopicOwnerType.USER);
        return imMessage;
    }

    public static SysMsgRequest valueOf(String appId, String tenantId, String content, List<Integer> receiverIds) {
        SysMsgRequest sysMsgRequest = new SysMsgRequest();
        return sysMsgRequest.setAppId(appId).setTenantId(tenantId).setContent(content).setReceiverIds(receiverIds);
    }
}
