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

package com.jim.im.rest.response;

import javax.ws.rs.core.Response;

import com.jim.im.utils.RequestEnv;

/**
 * @Description
 * @author <a href="mailto:wangpeiwen@139130.net">Peiwen.Wang</a>
 * @Data 2015年1月12日
 * @Version 1.0.0
 */
public class RestResult {
    
    public static Response success(String uuid, Object data) {
        ResponseSuccessObj result = new ResponseSuccessObj(uuid);
        result.setData(data);
        return Response.ok().type("application/json; charset=UTF-8").entity(result).build();
    }
    
    public static Response success(Object data) {
        ResponseSuccessObj result = new ResponseSuccessObj(RequestEnv.getRequestId());
        result.setData(data);
        return Response.ok().type("application/json; charset=UTF-8").entity(result).build();
    }

    public static Response success(String uuid) {
        ResponseSuccessObj result = new ResponseSuccessObj(uuid);
        return Response.ok().type("application/json; charset=UTF-8").entity(result).build();
    }
    
    public static Response success() {
        return success(RequestEnv.getRequestId());
    }

    public static Response failure(String uuid, String errorCode, String errorMsg) {
        ResponseFailedObj result = new ResponseFailedObj(uuid, errorCode, errorMsg);
        return Response.ok().type("application/json; charset=UTF-8").entity(result).build();
    }
    
    public static Response failure(String errorCode, String errorMsg) {
        return failure(RequestEnv.getRequestId(), errorCode, errorMsg);
    }

}
