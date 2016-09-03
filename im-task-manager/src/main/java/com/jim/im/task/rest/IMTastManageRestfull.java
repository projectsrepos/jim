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
package com.jim.im.task.rest;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jim.im.exception.ImException;
import com.jim.im.exception.ImParamException;
import com.jim.im.rest.response.RestResult;
import com.jim.im.task.entity.TaskInfo;
import com.jim.im.task.service.TaskInfoService;
import com.jim.im.utils.ImParamExceptionAssert;
import com.jim.im.utils.RequestEnv;

/**  
 * TODO
 * @version 1.0.0   
 */
@Path("/")
@Component
public class IMTastManageRestfull {
    @Autowired
    private TaskInfoService service;
    
    
    /**
    * pushmsgTimer  rest api
    * @param body
    * @param head
    * @return
    * @throws Exception
     */
    @POST
    @Path("pushmsgTimer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response pushmsgTimer(TaskInfo info)
            throws Exception{
        validTaskInfo(info);
        
        service.save(info);
        
        return RestResult.success();
    }
    
    /**
    * valid TaskInfo  
    * @param TaskInfo
    * @throws ImException
     */
    private void validTaskInfo(TaskInfo info) 
            throws ImException{
    	ImParamExceptionAssert.isNotBlank(info.getAppId(),
                "定时消息推送接口参数appId不能为空");
    	ImParamExceptionAssert.isNotBlank(info.getTenantId(),
                "定时消息推送接口参数tenantId不能为空");
    	ImParamExceptionAssert.check(
    			info.getOperatorid()!=null 
    			&& info.getOperatorid()>0,
                "定时消息推送接口参数operatorid不能为空和少于0");
        ImParamExceptionAssert.isNotBlank(info.getPushType(),
                "定时消息推送接口参数pushType不能为空");
        ImParamExceptionAssert.isNotBlank(info.getPushIds(),
                "定时消息推送接口参数pushIds不能为空");
        ImParamExceptionAssert.isNotBlank(info.getTimerStrategy(),
                "定时消息推送接口参数timerStrategy不能为空");
        ImParamExceptionAssert.check(
                info.getPushType().equals("user")
                ||info.getPushType().equals("group"), 
                "定时消息推送接口参数PushType不合法");
        try {
        	new CronExpression(info.getTimerStrategy());
		} catch (Exception e) {
			throw new ImParamException("定时消息推送接口参数timerStrategy解析错误",e);
		}
        
    }
    
}
