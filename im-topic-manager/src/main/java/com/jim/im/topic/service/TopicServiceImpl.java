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
package com.jim.im.topic.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.jim.im.consts.TopicOwnerType;
import com.jim.im.consts.TopicType;
import com.jim.im.db.entity.topic.TopicInfo;
import com.jim.im.db.entity.topic.TopicSubMap;
import com.jim.im.exception.ImParamException;
import com.jim.im.topic.dao.TopicInfoDao;
import com.jim.im.topic.dao.TopicSubMapDao;
import com.jim.im.topic.rpc.TopicRpcServiceImpl;
import static com.jim.im.utils.ImUtils.generateTopicName;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * topic 服务实现类
 *
 * @version 1.0
 */
@Service
@Transactional
public class TopicServiceImpl implements TopicService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TopicRpcServiceImpl.class);

    @Autowired
    private TopicInfoDao topicDao;
    @Autowired
    private TopicSubMapDao topicSubMapDao;

    @Override
    public Map<TopicType, List<String>> getSubTopics(Integer userId, String appId, String tenantId) {
        TopicSubMap topicSubMap = TopicSubMap.build(userId, appId, tenantId, null, null, null);
        List<TopicSubMap> topicSubMaps = topicSubMapDao.findByExample(topicSubMap);

        Map<TopicType, List<String>> topicMap = Maps.newHashMap();
        for (TopicSubMap subMap : topicSubMaps) {
            List<String> topics = topicMap.get(subMap.getTopicType());
            if (topics == null) {
                topics = Lists.newArrayList();
                topicMap.put(subMap.getTopicType(), topics);
            }

            topics.add(subMap.getTopicName());
        }

        return topicMap;
    }

    @Override
    public Collection<TopicInfo> createTopic(Integer ownerId,
                                             String appId,
                                             String tenantId,
                                             TopicOwnerType topicOwnerType,
                                             TopicType[] topicTypes) throws ImParamException {

        List<String> topicNames = generateTopicName(ownerId, appId, tenantId, topicOwnerType, topicTypes);
        if (CollectionUtils.isEmpty(topicNames))
            throw new ImParamException("The set of topic type can be not empty.");
        if (!CollectionUtils.isEmpty(topicDao.findByTopicNames(topicNames))) {
            String errorMsg = String.format("The owner(%s - %s - %s) already had some topics in system.", appId, tenantId, ownerId);
            throw new ImParamException(errorMsg);
        }

        Set<TopicInfo> topicInfoSet = Sets.newHashSet();
        for (int index = 0; index < topicTypes.length; index++) {
            TopicInfo temple =
                    TopicInfo.build(ownerId, appId, tenantId, topicOwnerType, topicTypes[index], topicNames.get(index));
            temple.setCreated(new Date());
            topicInfoSet.add(temple);
            topicDao.save(temple);
        }
        return topicInfoSet;
    }

    @Override
    public List<TopicInfo> deleteTopicInfo(Integer ownerId,
                                String appId,
                                String tenantId,
                                TopicOwnerType topicOwnerType,
                                TopicType... topicTypes) throws ImParamException {

        List<String> topicNames = generateTopicName(ownerId, appId, tenantId, topicOwnerType, topicTypes);
        if (topicNames.isEmpty())
            throw new ImParamException("The set of topic type can be not empty.");
        List<TopicInfo> topicInfoList = topicDao.findByTopicNames(topicNames);
        topicDao.removeByNames(topicNames);
        topicSubMapDao.removeByTopicNames(topicNames);
        return topicInfoList;
    }

    @Override
    public void subscribe(Integer ownerId,
                          String appId,
                          String tenantId,
                          TopicOwnerType topicOwnerType,
                          TopicType[] topicTypes,
                          Integer... subscriberIds) throws ImParamException {

        if (ArrayUtils.isEmpty(subscriberIds))
            throw new ImParamException("The subIds can be not empty.");
        for (TopicType type : topicTypes) {
            doSubscribe(subscriberIds, ownerId, appId, tenantId, topicOwnerType, type);
        }
    }

    public void doSubscribe(Integer[] subscriberIds,
                            Integer ownerId,
                            String appId,
                            String tenantId,
                            TopicOwnerType topicOwnerType,
                            TopicType topicType) throws ImParamException {

        TopicInfo topicInfo = getSingleTopicInfo(ownerId, appId, tenantId, topicOwnerType, topicType);
        if (topicInfo == null)
            throw new ImParamException(String.format("The subscribed topic(%s - %s - %s) was not exist.", ownerId, appId, tenantId));

        Set<TopicSubMap> topicSubMaps = Sets.newHashSet();
        for (Integer subscriberId : subscriberIds) {
            topicSubMaps.add(TopicSubMap.build(subscriberId, appId, tenantId, topicInfo.getName(), topicInfo.getId(), topicType));
        }
        topicSubMapDao.addBatch(topicSubMaps.toArray(new TopicSubMap[0]));
    }

    @Override
    public void unSubscribe(Integer ownerId,
                            String appId,
                            String tenantId,
                            TopicOwnerType topicOwnerType,
                            TopicType[] topicTypes,
                            Integer... unSubscriberIds) throws ImParamException {

        if (ArrayUtils.isEmpty(unSubscriberIds))
            throw new ImParamException("The unSubscriberIds is not null.");

        for (TopicType topicType : topicTypes) {
            doUnSubscribe(unSubscriberIds, ownerId, appId, tenantId, topicOwnerType, topicType);
        }
    }

    private void doUnSubscribe(Integer[] unSubscriberIds,
                               Integer ownerId,
                               String appId,
                               String tenantId,
                               TopicOwnerType topicOwnerType,
                               TopicType topicType) throws ImParamException {

        TopicInfo topicInfo = getSingleTopicInfo(ownerId, appId, tenantId, topicOwnerType, topicType);
        if (topicInfo == null)
            throw new ImParamException(String.format("[%s] - No has topic in [%s-%s]", ownerId, appId, tenantId));

        for (Integer unSubscriberId : unSubscriberIds) {
            TopicSubMap subMap =
                    TopicSubMap.build(unSubscriberId, appId, tenantId, topicInfo.getName(), topicInfo.getId(), topicType);
            topicSubMapDao.remove(subMap);
        }
    }

    @Override
    public List<TopicInfo> getTopicInfo(List<String> topicNames) {
        return topicDao.findByTopicNames(topicNames);
    }

    @Override
    public List<String> invalidTopicNames(Integer ownerId,
                                          String appId,
                                          String tenantId,
                                          TopicOwnerType topicOwnerType,
                                          TopicType... topicTypes) {
        return generateTopicName(ownerId, appId, tenantId, topicOwnerType, topicTypes);
    }

    private TopicInfo getSingleTopicInfo(Integer ownerId,
                                         String appId,
                                         String tenantId,
                                         TopicOwnerType topicOwnerType,
                                         TopicType topicType) throws ImParamException{

        List<String> topicNames = generateTopicName(ownerId, appId, tenantId, topicOwnerType, topicType);
        List<TopicInfo> topicInfoList = getTopicInfo(topicNames);
        if (CollectionUtils.isEmpty(topicInfoList) || topicInfoList.size() != 1)
            throw new ImParamException("Duplicate topic info error, because there expect get one topic info, but here has two.");
        return topicInfoList.get(0);
    }
}
