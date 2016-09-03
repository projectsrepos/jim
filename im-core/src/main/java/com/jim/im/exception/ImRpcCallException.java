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

/**
 * 
 * @date 2015年11月26日
 * @version 1.0.0
 */
@ErrorCodeAttached(ApiErrorCode.NETWORK_ERROR)
public class ImRpcCallException extends ImException {

    /**
     * 
     */
    private static final long serialVersionUID = -5766524658599917551L;

    /**
     * 
     */
    public ImRpcCallException() {}

    /**
     * @param message
     */
    public ImRpcCallException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public ImRpcCallException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public ImRpcCallException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public ImRpcCallException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
