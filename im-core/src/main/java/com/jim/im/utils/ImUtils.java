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

import com.google.common.collect.Lists;
import com.jim.im.consts.Delimiters;
import com.jim.im.consts.TopicOwnerType;
import com.jim.im.consts.TopicType;
import com.jim.im.exception.ImParamException;
import com.jim.im.mongo.entity.msg.ImMessage;
import com.jim.im.mongo.entity.msg.MsgType;
import org.apache.commons.lang3.ArrayUtils;
import java.util.List;

/**
 * 
 * @version 1.0.0
 */
public class ImUtils {

    /**
     * 把参数中的字符串数组转换为int数组
     * 
     * @param sources
     * @throws ImParamException
     */
    public static Integer[] transferString2Int(String[] sources, String errorMsg) throws ImParamException {
        Integer[] result = ArrayUtils.isEmpty(sources) ? new Integer[0] : new Integer[sources.length];
        try {
            for (int index = 0; index < result.length; index++) {
                result[index] = Integer.valueOf(sources[index]);
            }
        } catch (NumberFormatException e) {
            ImParamExceptionAssert.check(false, errorMsg);
        }
        return result;
    }

    /**
     * @param message
     * @return
     */
    public static String geneCommContent(ImMessage message) {
        if (message.getMsgType() == MsgType.SYSTEM) {
            String content = StringUtil.replace(message.getContent(), "null", message.getId().toString());
            message.setContent(content);
            return content;
        }
        return JsonUtil.toJson(message.toCommMessage());
    }

    /**
     * 生成主题名称
     *
     * @param ownerId 主题所有者ID
     * @param appId 应用ID
     * @param tenantId 租户ID
     * @param topicOwnerType 主题所有者类型 {@link TopicOwnerType}
     * @param topicTypes 主题类型 {@link TopicType}
     * @return
     */
    public static List<String> generateTopicName(Integer ownerId,
                                                 String appId,
                                                 String tenantId,
                                                 TopicOwnerType topicOwnerType,
                                                 TopicType... topicTypes) {

        List<String> topicNameSet = Lists.newArrayList();
        if (ArrayUtils.isNotEmpty(topicTypes)) {
            for (TopicType topicType : topicTypes) {
                String topicName = Delimiters.DIVIDE
                        + StringUtil.join(appId, tenantId, topicOwnerType.name(), ownerId, topicType.name());
                topicNameSet.add(topicName);
            }
        }
        return topicNameSet;
    }
}
