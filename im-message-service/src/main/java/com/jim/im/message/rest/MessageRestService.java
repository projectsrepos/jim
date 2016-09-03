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
package com.jim.im.message.rest;

import com.google.common.collect.Lists;
import com.jim.im.consts.Delimiters;
import com.jim.im.consts.TopicOwnerType;
import com.jim.im.exception.ImException;
import com.jim.im.message.service.MessageService;
import com.jim.im.mongo.entity.msg.ImMessage;
import com.jim.im.mongo.entity.msg.MsgType;
import com.jim.im.rest.request.message.PushMsgRequest;
import com.jim.im.rest.response.RestResult;
import com.jim.im.utils.ImUtils;
import com.jim.im.utils.RequestEnv;
import com.jim.im.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * 
 * @version 1.0.0
 */
@Component
@Path("/")
public class MessageRestService {

    @Autowired
    MessageService messageService;

    @POST
    @Path("pushmsg")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json; charset=UTF-8")
    public Response pushMsg(@NotNull @Valid PushMsgRequest req) throws ImException {
        String[] userIds = StringUtil.split(req.receiverIds, Delimiters.COMMA);
        Integer[] userIdInts = ImUtils.transferString2Int(userIds, "The userIds may be including NaN.");
        List<String> msgIds = batchPushMsg(userIdInts, req, TopicOwnerType.USER);
        return RestResult.success(msgIds);
    }

    @POST
    @Path("pushmsgByGroup")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json; charset=UTF-8")
    public Response pushMsgByGroup(@NotNull @Valid PushMsgRequest req) throws ImException {
        String[] groupIds = StringUtil.split(req.receiverIds, Delimiters.COMMA);
        Integer[] groupIdInts = ImUtils.transferString2Int(groupIds, "The groupIds may be including NaN.");
        List<String> msgIds = batchPushMsg(groupIdInts, req, TopicOwnerType.GROUP);
        return RestResult.success(msgIds);
    }

    /**
     * 批量发送消息
     * 
     * @param targetIds
     * @param req
     * @param topicOwnerType
     * @throws ImException
     */
    private List<String> batchPushMsg(Integer[] targetIds, PushMsgRequest req, TopicOwnerType topicOwnerType)
            throws ImException {
        List<String> sendMsgIds = Lists.newArrayList();
        for (Integer receiverId : targetIds) {
            ImMessage imMessage = buildImMessage(req.content, req.shortContent, receiverId, topicOwnerType);
            sendMsgIds.add(messageService.pushMessage(imMessage));
        }
        return sendMsgIds;
    }

    /**
     * 构建IM消息
     * 
     * @param content 内容
     * @param targetId 接收者ID
     * @param topicOwnerType 主题所有者类型
     * @return
     * @see ImMessage
     */
    private ImMessage buildImMessage(String content,
                                     String shortContent,
                                     int targetId,
                                     TopicOwnerType topicOwnerType) {
        ImMessage msg = new ImMessage();
        msg.setTopicOwnerType(topicOwnerType);
        RequestEnv.wrapEntity(msg);
        msg.setContent(content);
        msg.setShortContent(shortContent);
        msg.setMsgType(MsgType.PUSH);
        msg.setTargetId(targetId);
        msg.setFromId(RequestEnv.getOperatorId().toString());
        return msg;
    }
}
