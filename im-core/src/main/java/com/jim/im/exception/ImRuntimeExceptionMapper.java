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
package com.jim.im.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jim.im.consts.IMConstant;
import com.jim.im.rest.response.RestResult;
import com.jim.im.utils.RequestContext;


/**
 * 捕捉ImRuntimeException
 * 
 * @version 1.0.0
 */
public class ImRuntimeExceptionMapper implements ExceptionMapper<ImRuntimeException> {

    Logger logger = LoggerFactory.getLogger(ImRuntimeExceptionMapper.class);

    @Override
    public Response toResponse(ImRuntimeException e) {
        ApiErrorCode errorCode = ApiErrorCode.INNER_RUNTIME_ERROR;

        String errorMsg = IMConstant.MSG_INNER_RUNTIME_ERROR;
        
        String requestId = RequestContext.get("requestId");
        ApiErrorCodeException errorCodeException =
                new ApiErrorCodeException(requestId, errorCode, errorMsg, e);
        logger.error(requestId, errorCodeException);

        return RestResult.failure(requestId, errorCode.errorCode, errorMsg);
    }
}
