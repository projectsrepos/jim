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

import org.apache.commons.lang3.StringUtils;

import com.jim.im.exception.ImParamException;

/**
 * 
 * @version 1.0.0
 */
public class ImParamExceptionAssert {

    /**
     * @param message
     * @throws ImParamException
     */
    private static void throwException(String message) throws ImParamException {
        throw new ImParamException(message);
    }
        
    public static void isNull(Object object, String message) throws ImParamException {
        if (object != null) {
            throwException(message);
        }
    }
    
    public static void isNotNull(Object object, String message) throws ImParamException {
        if (object == null) {
            throwException(message);
        }
    }
    
    public static void check(boolean expression, String message) throws ImParamException {
        if (!expression) {
            throwException(message);
        }
    }

    public static void isNotBlank(CharSequence text, String message) throws ImParamException {
        if (!StringUtils.isNotBlank(text)) {
            throwException(message);
        }
    }
}
