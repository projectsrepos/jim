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
package com.jim.im.rest.filter;

import com.jim.im.consts.IMConstant;
import com.jim.im.utils.RequestContext;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import java.io.IOException;
import java.util.UUID;

/**
 * 
 * @version 1.0.0
 */
public class RequestEnvFilter implements ContainerRequestFilter {


    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        RequestContext.remove(IMConstant.REQUEST_ID);
        String requestId = UUID.randomUUID().toString();
        RequestContext.put(IMConstant.REQUEST_ID, requestId);

        RequestContext.remove(IMConstant.APP_ID);
        String appId = requestContext.getHeaderString(IMConstant.APP_ID);
        RequestContext.put(IMConstant.APP_ID, appId);

        RequestContext.remove(IMConstant.TENANT_ID);
        String tenantId = requestContext.getHeaderString(IMConstant.TENANT_ID);
        RequestContext.put(IMConstant.TENANT_ID, tenantId);

        RequestContext.remove(IMConstant.OPERATOR_ID);
        String operatorId = requestContext.getHeaderString(IMConstant.OPERATOR_ID);

        try {
            Integer operatorIdInt = Integer.valueOf(operatorId);
            RequestContext.put(IMConstant.OPERATOR_ID, operatorIdInt);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("The format of operatorId property is invalidate. Expect it is a number,but here is a " + operatorId);
        }
    }
}
