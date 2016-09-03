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
package com.jim.im.group.rest;

import com.jim.im.consts.Delimiters;
import com.jim.im.db.entity.group.Group;
import com.jim.im.db.entity.group.User;
import com.jim.im.exception.ImException;
import com.jim.im.exception.ImParamException;
import com.jim.im.group.service.GroupService;
import com.jim.im.rest.request.group.DeleteGroupRequest;
import com.jim.im.rest.request.group.InviteUserRequest;
import com.jim.im.rest.request.group.ListGroupRequest;
import com.jim.im.rest.request.group.ListGroupUserRequest;
import com.jim.im.rest.request.group.RemoveUserRequest;
import com.jim.im.rest.response.RestResult;
import com.jim.im.utils.ImParamExceptionAssert;
import com.jim.im.utils.ImUtils;
import com.jim.im.utils.RequestEnv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class GroupRestService {


    static Logger logger = LoggerFactory.getLogger(GroupRestService.class);

    @Autowired
    private GroupService groupService;

    @Autowired
    private GroupService userService;

    @POST
    @Path("createGroup")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createGroup(@Valid @NotNull Group group) throws ImException {
        RequestEnv.wrapEntity(group);
        groupService.createGroup(group);
        return RestResult.success(group);
    }

    @POST
    @Path("inviteUser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response inviteUser(@Valid @NotNull InviteUserRequest req) throws ImException {

        String[] userIds = req.inviteeIds.split(Delimiters.COMMA);
        ImParamExceptionAssert.check(userIds.length < 100, "用户参数不能为空, 各个id间以逗号分隔, 小于100个");
        Integer[] userIdInts = ImUtils.transferString2Int(userIds, "用户参数包含非数字字符");

        groupService.inviteUsers(RequestEnv.getAppId(), RequestEnv.getTenantId(), req.groupId, req.message, userIdInts);

        return RestResult.success();
    }

    @POST
    @Path("removeUser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeUser(@Valid @NotNull RemoveUserRequest req) throws ImException {

        String[] userIdArray = req.userIds.split(Delimiters.COMMA);
        ImParamExceptionAssert.check(userIdArray.length < 100, "userIds参数不能为空, 各个id间以逗号分隔, 小于100个");
        Integer[] userIdInts = ImUtils.transferString2Int(userIdArray, "用户参数包含非数字字符");

        groupService.removeUsersFromGroup(req.groupId, RequestEnv.getAppId(), RequestEnv.getTenantId(), userIdInts);

        return RestResult.success();
    }

    @POST
    @Path("deleteGroup")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteGroup(@Valid @NotNull DeleteGroupRequest req) throws ImException {

        groupService.deleteGroup(req.groupId);

        return RestResult.success();
    }

    /**
     * 显示群组，默认显示自己所在的群组，showAll = true时，显示所有群组
     */
    @POST
    @Path("listGroup")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response listGroup(@Valid @NotNull ListGroupRequest req) throws ImException {

        // TODO 目前没有安全控制，为避免用户看到其他群组，目前只显示自己加入的群组
        List<Group> groups = groupService.listGroup(RequestEnv.getAppId(), RequestEnv.getTenantId(),
                RequestEnv.getOperatorId(), false);
        // List<Group> groups = groupService.listGroup(RequestEnv.getAppId(),
        // RequestEnv.getTenantId(), RequestEnv.getOperatorId(), req.showAll);
        return RestResult.success(groups);

    }



    @POST
    @Path("listGroupUser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response listGroupUser(@Valid @NotNull ListGroupUserRequest req) throws ImParamException {
        List<User> users = userService.listGroupUser(RequestEnv.getAppId(), RequestEnv.getTenantId(), req.groupId);
        return RestResult.success(users);
    }


}
