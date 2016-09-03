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
package com.jim.im.group.rpc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jim.im.api.GroupRpcService;
import com.jim.im.db.entity.group.User;
import com.jim.im.exception.ImException;
import com.jim.im.group.dao.UserDao;

/**
 * 
 * @version 1.0.0
 */
@Component
@Transactional(rollbackFor = {ImException.class})
public class GroupRpcServiceImpl implements GroupRpcService {

    @Autowired
    UserDao userDao;

    @Override
    public List<Integer> getUserIds(String appId, String tenantId, int groupId) {
        List<Integer> userIds = new ArrayList<Integer>();
        List<User> users = userDao.listGroupUser(appId, tenantId, groupId);
        for (User user : users) {
            userIds.add(user.getGatewayUserId());
        }
        return userIds;
    }

}
