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
package com.jim.im.topic.rest;

import com.jim.im.consts.IMConstant;
import com.jim.im.exception.ImException;
import com.jim.im.rest.response.RestResult;
import com.jim.im.topic.service.TopicService;
import com.jim.im.utils.RequestContext;
import static com.jim.im.utils.RequestEnv.getAppId;
import static com.jim.im.utils.RequestEnv.getOperatorId;
import static com.jim.im.utils.RequestEnv.getTenantId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * topic restful 服务
 *
 * @version 1.0
 */
@Component
@Path("/")
public class TopicRestService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TopicRestService.class);

    @Autowired
    private TopicService topicService;

    /**
     * 获取用户关注过的topic列表
     * 
     * @return
     */
    @GET
    @Path("getSubTopics")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSubTopics() throws ImException {
        String requestId = RequestContext.get(IMConstant.REQUEST_ID);
        return RestResult.success(requestId, topicService.getSubTopics(getOperatorId(), getAppId(), getTenantId()));
    }
}
