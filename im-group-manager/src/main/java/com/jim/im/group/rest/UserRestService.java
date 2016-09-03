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

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jim.im.db.entity.group.User;
import com.jim.im.exception.ImException;
import com.jim.im.group.service.UserService;
import com.jim.im.rest.request.group.DeleteUserRequest;
import com.jim.im.rest.response.RestResult;
import com.jim.im.utils.RequestEnv;

/**
 * 
 * @version 1.0.0
 */
@Component
@Path("/")
public class UserRestService {
    
    Logger logger = LoggerFactory.getLogger(UserRestService.class);
    
    @Autowired
    UserService userService;
    
    @POST
    @Path("createUser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(@Valid @NotNull User user) throws ImException {
        RequestEnv.wrapEntity(user);
        userService.createUser(user);
        return RestResult.success();
    }
    
    @POST
    @Path("deleteUser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@Valid @NotNull DeleteUserRequest req) throws ImException {
        
        userService.deleteUser(req.userId);
        
        return RestResult.success();
    }


}
