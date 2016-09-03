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
package com.jim.im.offline.rest;

import com.jim.im.consts.IMConstant;
import com.jim.im.exception.ImException;
import com.jim.im.offline.rest.entity.OfflineMessageHttpEntity;
import com.jim.im.offline.service.OfflineMessageService;
import com.jim.im.rest.response.RestResult;
import com.jim.im.utils.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * 离线消息 restful 服务
 *
 * @version 1.0
 */
@Controller
@Path("/")
public class OfflineMsRestService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OfflineMsRestService.class);

    @Autowired
    private OfflineMessageService messageService;

    /**
     * 获取用户离线消息列表
     * 
     * @return
     */
    @POST
    @Path("getOfflineMassages")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOfflineMassages(@Valid @NotNull OfflineMessageHttpEntity httpEntity) throws ImException {
        String requestId = RequestContext.get(IMConstant.REQUEST_ID);
        return RestResult.success(requestId,
                messageService.getOfflineMessages(httpEntity.topics, httpEntity.lastMsgId));
    }
}
