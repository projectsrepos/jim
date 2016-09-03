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
package com.jim.im.group.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jim.im.api.MessageRpcService;
import com.jim.im.api.TopicRpcService;
import com.jim.im.consts.TopicOperaterType;
import com.jim.im.consts.TopicOwnerType;
import com.jim.im.consts.TopicType;
import com.jim.im.db.base.QueryParameters;
import com.jim.im.db.entity.group.Group;
import com.jim.im.db.entity.group.GroupEnumTypes.GroupUserRole;
import com.jim.im.db.entity.group.GroupUserMap;
import com.jim.im.db.entity.group.User;
import com.jim.im.db.entity.topic.TopicInfo;
import com.jim.im.db.entity.topic.TopicRequest;
import com.jim.im.exception.ImException;
import com.jim.im.exception.ImParamException;
import com.jim.im.exception.TopicRpcException;
import com.jim.im.group.dao.GroupDao;
import com.jim.im.group.dao.GroupUserMapDao;
import com.jim.im.group.dao.UserDao;
import com.jim.im.rest.request.message.SysMsgRequest;
import com.jim.im.utils.Assert;
import com.jim.im.utils.ImParamExceptionAssert;
import com.jim.im.utils.RequestEnv;

import edu.emory.mathcs.backport.java.util.Arrays;

/**
 * 
 * @author Martin
 * @version 1.0.0
 */
@Component
@Transactional(rollbackFor = {ImException.class})
public class GroupServiceImpl implements GroupService {

    @Autowired
    GroupDao groupDao;

    @Autowired
    UserDao userDao;

    @Autowired
    GroupUserMapDao groupUserMapDao;

    @Autowired
    TopicRpcService topicRpcService;
    
    @Autowired
    MessageRpcService MessageRpcService;

    public GroupServiceImpl() {}
    
    /**
     * Message RPC 调用
     * 发送系统消息
     * @param appId
     * @param tenantId
     * @param targetIds
     * @param body
     * @throws ImException
     */
    private void sendSystemMessage(
    		String appId,String tenantId,List<Integer> targetIds,
    		String body) throws ImException{
    	 if(targetIds == null)
    		 return;
    	 
    	 MessageRpcService.firedMessage(
    			 SysMsgRequest.valueOf(
    					 appId, tenantId, body, targetIds));
    }
    
    /**
     * 创建系统消息主内容  结构体 "m"
     * @param operaterType
     * @param topics TopicInfo集合
     * @return 
     */
    private String buildSystemMessageBody(TopicOperaterType operaterType,Collection<TopicInfo> topics){
    	StringBuffer buf = new StringBuffer();
        for (TopicInfo info : topics) {
        	buf.append(",").append(info.getName());
		}
        String strTopic = buf.substring(1);
        return buildSystemMessageBody(operaterType, strTopic);
    }
    
    /**
     * System Message Body json组装
     * @param operaterType
     * @param topics
     * @return
     */
    private String buildSystemMessageBody(TopicOperaterType operaterType,String topics){
    	return "{mid:null,op:\""+operaterType.name()+"\",m:{"+topics+"}}";
    }
    	
    /**
     * 发送操作类型的系统消息
     * @param appId
     * @param tenantId
     * @param targetIds
     * @param operaterType
     * @param topics
     * @throws ImException
     */
    private void sendSystemOperaterMessage(
    		String appId,String tenantId,List<Integer> targetIds,
    		TopicOperaterType operaterType,
    		Collection<TopicInfo> topics) throws ImException{
    	String body = buildSystemMessageBody(operaterType, topics);
        sendSystemMessage(appId, tenantId,
        		targetIds, body);
    }
    
    
    @Override
    public void createGroup(Group group) throws ImException {

        Assert.isNotNull(group);

        QueryParameters param = new QueryParameters();
        param.addParam("name", group.getName());
        param.addParam("appId", group.getAppId());
        param.addParam("tenanetId", group.getTenantId());
        int count = groupDao.findResultCount(param);
        if (count > 0) {
            throw new ImParamException(
                    String.format("同app:%s同租户:%s下群组名称必须唯一", RequestEnv.getAppId(), RequestEnv.getTenantId()));
        }

        groupDao.add(group);
        
        TopicRequest topicReq = TopicRequest.build(group.getId(), RequestEnv.getAppId(), RequestEnv.getTenantId(), TopicOwnerType.GROUP, TopicType.values());
        Collection<TopicInfo> topics = topicRpcService.createTopics(topicReq);
        topicRpcService.subscribeTopic(topicReq, RequestEnv.getOperatorId());
        List<Integer> notifyUserIds = new ArrayList<Integer>();
        notifyUserIds.add(RequestEnv.getOperatorId());
        sendSystemOperaterMessage(group.getAppId(), 
        		group.getTenantId(),notifyUserIds, TopicOperaterType.SUBSCRIBE,topics);
       
    }

    @Override
    public void inviteUsers(String appId, String tenantId, int groupId, String message, Integer... inviteeIds)
            throws ImException {
        Group group = groupDao.getById(groupId);
        ImParamExceptionAssert.isNotNull(group, "群组不存在，请检查参数");

        int count = userDao.findUsersCountInUserIds(appId, tenantId, inviteeIds);
        ImParamExceptionAssert.check(count == inviteeIds.length, "传入的用户在数据库中不存在");

        doInviteUserInDB(appId, tenantId, groupId, inviteeIds);
        
        TopicRequest topicReq = TopicRequest.build(groupId, appId, tenantId, TopicOwnerType.GROUP, TopicType.values());
        topicRpcService.subscribeTopic(topicReq, inviteeIds);
        Map<TopicType,TopicInfo> map = topicRpcService.getTopicInfo(topicReq);
        
        sendSystemOperaterMessage(appId, tenantId, Arrays.asList(inviteeIds),
        		TopicOperaterType.SUBSCRIBE, map.values());
    }

    @Override
    public void removeUsersFromGroup(int groupId, String appId, String tenantId, Integer... userIds)
            throws ImException {
        Group group = groupDao.getById(groupId);
        ImParamExceptionAssert.isNotNull(group, "群组不存在，请检查参数");

        int count = userDao.findUsersCountInUserIds(appId, tenantId, userIds);
        ImParamExceptionAssert.check(count == userIds.length, "传入的用户在数据库中不存在");

        doRemoveUserInDB(appId, tenantId, groupId, userIds);

        TopicRequest topicReq = TopicRequest.build(groupId, appId, tenantId, TopicOwnerType.GROUP, TopicType.values());
        topicRpcService.unSubscribeTopic(topicReq, userIds);
        Map<TopicType,TopicInfo> map = topicRpcService.getTopicInfo(topicReq);
        sendSystemOperaterMessage(appId, tenantId, Arrays.asList(userIds),
        		TopicOperaterType.UNSUBSCRIBER, map.values());
    }

    @Override
    public void deleteGroup(int groupId) throws ImException {
        Group group = groupDao.getById(groupId);
        ImParamExceptionAssert.isNotNull(group, "群组不存在，请检查参数");

        GroupUserMap example = new GroupUserMap();
        example.setGroupId(groupId);
        int count = groupUserMapDao.findByExampleCount(example);
        ImParamExceptionAssert.check(count == 0, "群组中存在用户，踢出所有用户后才能删除群组");

        groupDao.remove(group);
        TopicRequest topicReq = TopicRequest.build(groupId, RequestEnv.getAppId(), RequestEnv.getTenantId(), TopicOwnerType.GROUP, TopicType.values());
        List<TopicInfo> deleteTopics = topicRpcService.deleteTopic(topicReq);
        
        List<User> userList = listGroupUser(RequestEnv.getAppId(), RequestEnv.getTenantId(), groupId);
        if(userList ==null)
        	return;
        
        List<Integer> userIDs = new ArrayList<Integer>();
        for(User user : userList){
        	userIDs.add(user.getGatewayUserId());
        }
        sendSystemOperaterMessage(group.getAppId(), group.getTenantId(), userIDs,
        		TopicOperaterType.UNSUBSCRIBER, deleteTopics);
    }

    @Override
    public List<Group> listGroup(String appId, String tenantId, int operatorId, Boolean showAll)
            throws ImParamException {
        if (showAll) {
            Group example = new Group();
            example.setAppId(appId);
            example.setTenantId(tenantId);
            return groupDao.findByExample(example);
        } else {
            return groupDao.findGroupEntered(appId, tenantId, operatorId);
        }
    }


    private void doRemoveUserInDB(String appId, String tenantId, int groupId, Integer... userIds)
            throws ImParamException {
        for (int userId : userIds) {
            GroupUserMap map = new GroupUserMap();
            map.setGroupId(groupId);
            map.setUserId(userId);
            map.setAppId(appId);
            map.setTenantId(tenantId);
            groupUserMapDao.remove(map);
        }
    }

    @Override
    public List<User> listGroupUser(String appId, String tenantId, Integer groupId) {
        return userDao.listGroupUser(appId, tenantId, groupId);
    }

    /**
     * @param appId
     * @throws ImParamException
     */
    private void doInviteUserInDB(String appId, String tenantId, int groupId, Integer... inviteeIds)
            throws ImParamException {
        GroupUserMap[] maps = createGroupUserMaps(appId, tenantId, groupId, inviteeIds);

        QueryParameters param = new QueryParameters();
        param.addParam("groupId", groupId);
        param.addParam("userIds", inviteeIds);
        List<Integer> userIdsExisted = groupUserMapDao.getUserIdsExisted(param);
        if (userIdsExisted != null && userIdsExisted.size() > 0) {
            throw new ImParamException(String.format("被邀请人:%s 已经在存在于此群组中",userIdsExisted));
        }

        groupUserMapDao.addBatch(maps);

    }

    /**
     * @param groupId
     * @param inviteeIds
     * @return
     */
    private GroupUserMap[] createGroupUserMaps(String appId, String tenantId, int groupId, Integer... inviteeIds) {
        List<GroupUserMap> maps = new ArrayList<GroupUserMap>();
        for (int userId : inviteeIds) {
            GroupUserMap map = new GroupUserMap();
            map.setRole(GroupUserRole.USER);
            map.setGroupId(groupId);
            map.setUserId(userId);
            map.setAppId(appId);
            map.setTenantId(tenantId);
            maps.add(map);
        }
        GroupUserMap[] array = new GroupUserMap[maps.size()];
        return maps.toArray(array);
    }
}
