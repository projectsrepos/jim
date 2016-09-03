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

import com.jim.im.consts.IMConstant;

/**
 * api error code excetion, only used in user-end classes 建议不包含异常链，异常链前置处理
 * 
 * @date 2015年11月26日
 * @version 1.0.0
 */
public class ApiErrorCodeException extends ImRuntimeException {


    private static final long serialVersionUID = -5766524658599917551L;

    ApiErrorCode errorCode;
    String errorMsg;
    String requestId;

    public ApiErrorCodeException(String requestId, ApiErrorCode errorCode, String errorMsg) {
        super(errorMsg);
        this.requestId = requestId;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public ApiErrorCodeException(String requestId, ApiErrorCode errorCode, String errorMsg,
            Throwable cause) {
        super(errorMsg, cause);
        this.requestId = requestId;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    @Deprecated
    public ApiErrorCodeException() {
        errorCode = ApiErrorCode.INNER_ERROR;
        errorMsg = IMConstant.MSG_INNER_ERROR;
    }

    /**
     * @param message
     */
    @Deprecated
    public ApiErrorCodeException(String message) {
        super(message);
        errorCode = ApiErrorCode.INNER_ERROR;
        errorMsg = message;
    }

    /**
     * @param cause
     */
    @Deprecated
    public ApiErrorCodeException(Throwable cause) {
        super(cause);
        errorCode = ApiErrorCode.INNER_ERROR;
        errorMsg = IMConstant.MSG_INNER_ERROR;
    }

    /**
     * @param message
     * @param cause
     */
    @Deprecated
    public ApiErrorCodeException(String message, Throwable cause) {
        super(message, cause);
        errorCode = ApiErrorCode.INNER_ERROR;
        errorMsg = message;
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    @Deprecated
    public ApiErrorCodeException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        errorCode = ApiErrorCode.INNER_ERROR;
        errorMsg = message;
    }

}
