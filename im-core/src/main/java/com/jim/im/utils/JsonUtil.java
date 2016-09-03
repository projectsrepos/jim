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

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

/**
 * @Description JSON工具类
 * @Data 2015年3月31日
 * @Version 1.0.0
 */
public class JsonUtil {

	private static ObjectMapper mapper;

	static {
		mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
				false);
	}

	public static <T> T fromJson(String json, Class<T> t) {
		Assert.notNull(json, "Json string should not be null");
		try {
			return mapper.readValue(json, t);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String toJson(Object obj) {
		try {
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Map<String, Object> jsonToMap(String src) {
		try {
			return mapper.readValue(src, new TypeReference<Map<String, Object>>() {});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Map<String, String> toMap(String src) throws Exception {
		return mapper.readValue(src, new TypeReference<Map<String, String>>() {});
	}
	
	public static Map<String, Object> toMapValObj(String src) throws Exception {
		return mapper.readValue(src, new TypeReference<Map<String, Object>>() {});
	}
	
	public static String serialize(Object obj) throws Exception {
		return mapper.writeValueAsString(obj);
	}
	
	public static <T> T deserializeFromBytes(byte[] srcBytes, Class<T> t)
			throws Exception {
		if (srcBytes == null) {
			throw new IllegalArgumentException("srcBytes should not be null");
		}
		return mapper.readValue(srcBytes, 0, srcBytes.length, t);
	}

	public static <T> T deserialize(String src, Class<T> t) throws Exception {
		if (src == null) {
			throw new IllegalArgumentException("src should not be null");
		}
		return mapper.readValue(src, t);
	}

	public static String toJSON(int ret) {
		String msg = "Error data!";
		if (ret == 0) {
			msg = "ok";
		}
		return toJSON("null", msg, ret);
	}
	
	public static String toJSON(String msg, int ret) {
		return toJSON("null", msg, ret);
	}

	/**
	 * 
	 * @param data
	 *            json data
	 * @param msg
	 *            message
	 * @param ret
	 *            0--true, not 0--false
	 * @return
	 */
	public static String toJSON(String data, String msg, int ret) {
		StringBuilder sb = new StringBuilder();
		sb.append('{');
		sb.append("\"data\":");
		sb.append(data);
		sb.append(",\"msg\":\"");
		sb.append(msg);
		sb.append("\",\"ret\":");
		sb.append(ret);
		sb.append('}');
		return sb.toString();
	}
	/**
	 * @description str to json List
	 * @param jsonStr
	 * @return
	 */
	public static List<LinkedHashMap<String, Object>> readJson2List(String jsonStr) {
	    try {
	    	if (jsonStr == null) {
				throw new IllegalArgumentException(" jsonStr should not be null");
			}
	        return  mapper.readValue(jsonStr, List.class);
	    } catch (Exception e) {
	    	throw new RuntimeException(e);
	    }
	}
	
	
	/**
	 * 获取泛型的Collection Type
	 * 
	 * @param jsonStr
	 *            json字符串
	 * @param collectionClass
	 *            泛型的Collection
	 * @param elementClasses
	 *            元素类型
	 */
	@SuppressWarnings("rawtypes")
	public static <T> T fromJson(String jsonStr, Class<? extends Collection> collectionClass, Class<?> elementClasses)
			throws Exception {
		CollectionType collectionType = mapper.getTypeFactory()
				.constructCollectionType(collectionClass, elementClasses);
		return mapper.readValue(jsonStr, collectionType);
	}
	
	public static <T> List<T> jsonToList(String jsonStr, Class<T> t) {
		Assert.notNull(jsonStr, "Json string should not be null");
		try {
			return mapper.readValue(jsonStr, mapper.getTypeFactory().constructCollectionType(List.class, t));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
