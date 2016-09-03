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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jim.im.api.TopicRpcService;
import com.jim.im.consts.TopicOwnerType;
import com.jim.im.consts.TopicType;
import com.jim.im.db.base.QueryParameters;
import com.jim.im.db.entity.group.GroupUserMap;
import com.jim.im.db.entity.group.User;
import com.jim.im.db.entity.topic.TopicRequest;
import com.jim.im.exception.ImException;
import com.jim.im.exception.ImParamException;
import com.jim.im.exception.TopicRpcException;
import com.jim.im.group.dao.GroupUserMapDao;
import com.jim.im.group.dao.UserDao;
import com.jim.im.utils.ImParamExceptionAssert;
import com.jim.im.utils.RequestEnv;

/**
 * 
 * @version 1.0.0
 */
@Component
@Transactional(rollbackFor = {ImException.class})
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;
    
    @Autowired
    GroupUserMapDao groupUserMapDao;
    
    @Autowired
    TopicRpcService topicRpcService;

    @Override
    public void createUser(User user) throws ImParamException, TopicRpcException {
        ImParamExceptionAssert.isNotNull(user,"用户参数不能为空");
        ImParamExceptionAssert.isNotNull(user.getGatewayUserId(), "用户网关ID不能为空");
        
        QueryParameters param = new QueryParameters();
        param.addParam("appId", user.getAppId());
        param.addParam("tenanetId", user.getTenantId());
        param.addParam("gatewayUserId", user.getGatewayUserId());
        int count = userDao.findResultCount(param);
        if (count > 0) {
            throw new ImParamException("此用户已经注册");
        }
     
        userDao.add(user);
        
        //创建用户时，自动注册topic并订阅自己
        TopicRequest topicReq = TopicRequest.build(user.getGatewayUserId(), RequestEnv.getAppId(), RequestEnv.getTenantId(), TopicOwnerType.USER, TopicType.values() );
        topicRpcService.createTopics(topicReq);
        topicRpcService.subscribeTopic(topicReq, user.getGatewayUserId());
    }

    @Override
    public void deleteUser(Integer userId) throws ImParamException, TopicRpcException {
        
        GroupUserMap example = new GroupUserMap();
        example.setUserId(userId);
        example.setAppId(RequestEnv.getAppId());
        example.setTenantId(RequestEnv.getTenantId());
        int count = groupUserMapDao.findByExampleCount(example);
        ImParamExceptionAssert.check(count == 0, "群组中存在此用户，退出群组后方可删除用户");
        
        User user = new User();
        user.setGatewayUserId(userId);
        user.setAppId(RequestEnv.getAppId());
        user.setTenantId(RequestEnv.getTenantId());
        count = userDao.remove(user);
        ImParamExceptionAssert.check(count > 0, "传入的用户不存在");
        
        TopicRequest topicReq = TopicRequest.build(user.getGatewayUserId(), RequestEnv.getAppId(), RequestEnv.getTenantId(), TopicOwnerType.USER, TopicType.values());
        topicRpcService.unSubscribeTopic(topicReq, user.getGatewayUserId());
        topicRpcService.deleteTopic(topicReq);
    }
}
