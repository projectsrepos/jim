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
package com.jim.im.utils;

import com.jim.im.consts.IMConstant;
import com.jim.im.db.base.Tenantable;

/**
 * 工具类：用于处理restfull请求requestContext中的参数
 * 
 * @version 1.0.0
 */
public class RequestEnv {
    
    public static String getRequestId() {
        return RequestContext.get(IMConstant.REQUEST_ID);
    }
    
    public static String getAppId() {
        return RequestContext.get(IMConstant.APP_ID);
    }
    
    public static String getTenantId() {
        return RequestContext.get(IMConstant.TENANT_ID);
    }
    
    public static Integer getOperatorId() {
        return RequestContext.get(IMConstant.OPERATOR_ID);
    }

    /**
     * 在restfull请求中，注入当前appId,tenantId
     * */
    public static void wrapEntity(Tenantable entity) {
        entity.setAppId(RequestEnv.getAppId());
        entity.setTenantId(RequestEnv.getTenantId());
    }
    
}
