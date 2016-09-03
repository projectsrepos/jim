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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @version 1.0
 */
public class RequestContext {

    private static ThreadLocal<Map<String, Object>> context =
            new ThreadLocal<Map<String, Object>>() {
                protected synchronized Map<String, Object> initialValue() {
                    return new HashMap<String, Object>();
                }
            };

    public static Map<String, Object> map() {
        return Collections.unmodifiableMap(context.get());
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(String key) {
        return (T) context.get().get(key);
    }


    public static void put(String key, Object value) {
        context.get().put(key, value);
    }
    
    public static void remove(String key) {
        context.get().remove(key);
    }

    public static void clear() {
        context.get().clear();
    }
}
