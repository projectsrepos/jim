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
 * 系统通用受控异常
 *
 * @version 1.0.0
 * @date 2015年11月17日
 */
public class ImException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 4674406882622433515L;

    /**
     *
     */
    public ImException() {}

    /**
     * @param message
     */
    public ImException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public ImException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public ImException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public ImException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
