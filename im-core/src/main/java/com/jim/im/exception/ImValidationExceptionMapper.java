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

import java.util.List;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.server.validation.ValidationError;
import org.glassfish.jersey.server.validation.internal.ValidationHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jim.im.consts.IMConstant;
import com.jim.im.rest.response.RestResult;
import com.jim.im.utils.RequestContext;

/**
 * 
 * @version 1.0.0
 */
public class ImValidationExceptionMapper implements ExceptionMapper<ValidationException>  {

    Logger logger = LoggerFactory.getLogger(ValidationException.class);
    
    @Override
    public Response toResponse(ValidationException e) {
        ApiErrorCode errorCode = ApiErrorCode.PARAM_ERROR;

        StringBuffer errorMsgBuf = new StringBuffer();
        
        final ConstraintViolationException cve = (ConstraintViolationException) e;
        List<ValidationError> errors = ValidationHelper.constraintViolationToValidationErrors(cve);
        for (ValidationError error : errors) {
            errorMsgBuf.append(error.getMessage()).append(";");
        }
        
        String errorMsg = errorMsgBuf.toString();
        if (StringUtils.isBlank(errorMsg)) {
            errorMsg = IMConstant.MSG_PARAM_ERROR;
        }        
        
        String requestId = RequestContext.get("requestId");
        
        ApiErrorCodeException errorCodeException = new ApiErrorCodeException(requestId, errorCode, errorMsg, e);
        logger.error(requestId, errorCodeException);

        return RestResult.failure(requestId, errorCode.errorCode, errorMsg);
    }

}
