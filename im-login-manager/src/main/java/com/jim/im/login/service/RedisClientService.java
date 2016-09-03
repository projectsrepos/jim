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
package com.jim.im.login.service;

import java.util.Map;

import com.jim.im.exception.ImParamException;

/**
* Redis Client Service interface
* @version V1.0.0   
 */
public interface RedisClientService {
	
	/**
	 * redis键找值
	 * @param key
	 * @return
	 */
	public String getValue(String key);
	
	/**
	 * 分配连接服务器IP
	 * @param id
	 * @return
	 */
	public String getServerIpString(String id);
	
	/**
	 * 保存Map到redis key
	 * @param key
	 * @param map
	 */
	void savaMap(String key,Map<String, String> map);
	
	/**
	 * redis key 获得值Map
	 * @param key
	 * @return
	 * @throws ImParamException 
	 */
	Map<Object, Object> getKeyValueMap(String key);
	
	/**
	 * redis key ,Map key 获得值
	 * @param key
	 * @param mapKey
	 * @return
	 * @throws ImParamException 
	 */
	String getMapValue(String key,String mapKey);
	
	/**
	 * 跟新redis key的值为map
	 * @param key
	 * @param map
	 * @throws ImParamException 
	 */
	void updateMap(String key,Map<String, String> map);
	
	/**
	 * 跟新redis key,map key 的值为mapValue
	 * @param key
	 * @param mapKey
	 * @param mapValue
	 * @throws ImParamException 
	 */
	void updateMapValue(String key,String mapKey,String mapValue);
	
	/**
	 * 删除redis key的map key
	 * @param key
	 * @param mapKey
	 * @throws ImParamException 
	 */
	void deleteMapKey(String key,String mapKey);
	
	/**
	 * 删除redis key
	 * @param key
	 */
	void deleteMap(String key);
	
	/**
	 * 检测key和mapkey存在
	 * @param key
	 * @param mapKey
	 * @return
	 */
	boolean hasKey(String key,String mapKey);
	
}
