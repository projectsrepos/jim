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
package com.jim.im.offline.repo;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mongodb.DBObject;
import com.jim.im.consts.TopicOwnerType;
import com.jim.im.mongo.entity.msg.ImMessage;
import com.jim.im.mongo.entity.msg.MsgType;
import org.springframework.util.ReflectionUtils;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @version 1.0
 */
public class MessageConverter {
    private static final Map<String, Field> FIELD_MAP;
    private static final Set<String> FIELD_KEYS;
    private static final Class<org.springframework.data.mongodb.core.mapping.Field> ALIAS_NAME = org.springframework.data.mongodb.core.mapping.Field.class;

    static {
        FIELD_MAP = Maps.newHashMap();
        Field[] declaredFields = ImMessage.class.getDeclaredFields();
        for (Field field : declaredFields) {
            FIELD_MAP.put(field.getName(), field);
        }
        FIELD_KEYS = FIELD_MAP.keySet();
    }

    private static ImMessage convert(DBObject dbObject) {
        ImMessage imMessage = new ImMessage();
        imMessage.setCreateTime(0L);
        Map map = dbObject.toMap();
        for (String key : FIELD_KEYS) {
            Field field = FIELD_MAP.get(key);
            org.springframework.data.mongodb.core.mapping.Field annotation = field.getAnnotation(ALIAS_NAME);
            if (annotation != null) key = annotation.value();
            field.setAccessible(true);
            Object value = getValue(field, map.get(key));
            if (value == null) continue;
            ReflectionUtils.setField(field, imMessage, value);
        }

        return imMessage;
    }

    private static Object getValue(Field field, Object sourceValue) {
        if (sourceValue == null) return null;

        if (field.getType() == MsgType.class) {
            return MsgType.valueOf(sourceValue.toString());
        } else if (field.getType() == TopicOwnerType.class) {
            return TopicOwnerType.valueOf(sourceValue.toString());
        } else if (field.getType() == BigInteger.class) {
            return new BigInteger(sourceValue.toString(), 16);
        }
        return sourceValue;
    }

    public static List<ImMessage> convert(Iterator<DBObject> result) {
        List<ImMessage> messages = Lists.newArrayList();
        while (result.hasNext()) {
            messages.add(convert(result.next()));
        }
        return messages;
    }
}
