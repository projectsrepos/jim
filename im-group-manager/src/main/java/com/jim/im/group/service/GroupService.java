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

import java.util.List;

import com.jim.im.db.entity.group.Group;
import com.jim.im.db.entity.group.User;
import com.jim.im.exception.ImException;
import com.jim.im.exception.ImParamException;
import com.jim.im.exception.ImRpcCallException;

/**
 * 
 * @version 1.0.0
 */
public interface GroupService {

	/**
	 * @param group
	 * @throws ImParamException
	 * @throws ImRpcCallException
	 */
	void createGroup(Group group) throws ImException;


    /**
     * @param appId
     * @param tenantId
     * @param groupId
     * @param message
     * @param inviteeId
     * @throws ImParamException
     */
    void inviteUsers(String appId, String tenantId, int groupId, String message, Integer... inviteeId)
            throws ImException;


    /**
     * @param groupId
     * @param appId
     * @param tenantId
     * @param userIds
     * @throws ImParamException
     */
    void removeUsersFromGroup(int groupId, String appId, String tenantId, Integer... userIds)
            throws ImException;


    /**
     * @param groupId
     * @throws ImParamException
     */
    void deleteGroup(int groupId) throws ImException;


    List<Group> listGroup(String appId, String tenantId, int operatorId, Boolean showAll) throws ImException;


    /**
     * @param appId
     * @param tenantId
     * @param groupId
     * @return
     */
    List<User> listGroupUser(String appId, String tenantId, Integer groupId);

	
}
