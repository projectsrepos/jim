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
 * @version 1.0.0
 * @date 2015年11月26日
 */
@ErrorCodeAttached(ApiErrorCode.PARAM_ERROR)
public class ImParamException extends ImException {

    /**
     *
     */
    private static final long serialVersionUID = -5766524658599917551L;

    /**
     *
     */
    public ImParamException() {
        super(IMConstant.MSG_INNER_ERROR);
    }

    /**
     * @param message
     */
    public ImParamException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public ImParamException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public ImParamException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public ImParamException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
