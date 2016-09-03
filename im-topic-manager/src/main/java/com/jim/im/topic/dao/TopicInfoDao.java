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
package com.jim.im.topic.dao;

import com.jim.im.db.base.EntityDao;
import com.jim.im.db.entity.topic.TopicInfo;
import java.util.Collection;
import java.util.List;

/**
 * topic 数据库接口
 * 
 * @version 1.0
 */
public interface TopicInfoDao extends EntityDao<TopicInfo> {
    void removeByNames(Collection<String> topicNames);

    List<TopicInfo> findByTopicNames(Collection<String> topicNames);
}
