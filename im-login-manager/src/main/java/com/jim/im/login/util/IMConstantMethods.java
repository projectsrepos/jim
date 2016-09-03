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
package com.jim.im.login.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @version 1.0.0
 */
public class IMConstantMethods {
	
	public static Map<String, String> objectToMap(Object o) throws IllegalArgumentException, IllegalAccessException {  
		Map<String, String> resMap = new HashMap<String, String>();  
        Field[] declaredFields = o.getClass().getDeclaredFields();  
        for (Field field : declaredFields) {  
            field.setAccessible(true);  
            //过滤内容为空的  
            if (field.get(o) == null) {  
                continue;  
            }  
            resMap.put(field.getName(), field.get(o).toString());  
        }  
        return resMap;
    }  
	
	/**
	 * 根据应用ID，租户ID，用户ID生成redis key
	 * @param appid
	 * @param tenentID
	 * @param userID
	 * @return
	 */
	public static String redisSessionKey(Object appid,Object tenentID,Object userID){
		return appid.toString()+"_"+tenentID.toString()+"_"+userID.toString();
	}
	
}
