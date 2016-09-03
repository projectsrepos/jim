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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.jim.im.exception.ImParamException;
import com.jim.im.login.util.HashAlgorithms;

/**
 * Redis Client Service implements
 * 
 * @version 1.0.0
 */
@Component
public class RedisClientServiceImpl implements RedisClientService {
	@Autowired
	private StringRedisTemplate redisTemplate;

	@Value("${mqttServerAddressList}")
	private String[] ipaddress;

	@Override
	public String getValue(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	@Override
	public String getServerIpString(String id) {
		return ipaddress[HashAlgorithms.additiveHash(id, ipaddress.length)];
	}

	@Override
	public void savaMap(String key, Map<String, String> map) {
		redisTemplate.opsForHash().putAll(key, map);
	}

	@Override
	public Map<Object, Object> getKeyValueMap(String key){
		return redisTemplate.opsForHash().entries(key);
	}

	@Override
	public String getMapValue(String key, String mapKey){
		return redisTemplate.opsForHash().get(key, mapKey).toString();
	}

	@Override
	public void updateMap(String key, Map<String, String> map){
		redisTemplate.opsForHash().putAll(key, map);
	}

	@Override
	public void updateMapValue(String key, String mapKey, String mapValue){
		redisTemplate.opsForHash().put(key, mapKey, mapValue);
	}

	@Override
	public void deleteMapKey(String key, String mapKey){
		redisTemplate.opsForHash().delete(key, mapKey);
	}

	@Override
	public void deleteMap(String key) {
		redisTemplate.boundHashOps(key).putAll(null);
	}

	@Override
	public boolean hasKey(String key, String mapKey) {
		return redisTemplate.opsForHash().hasKey(key, mapKey);
	}

}
