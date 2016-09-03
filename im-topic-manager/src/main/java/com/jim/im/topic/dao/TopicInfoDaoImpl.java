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

import com.jim.im.db.base.MybatisEntityDao;
import com.jim.im.db.entity.topic.TopicInfo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import java.util.Collection;
import java.util.List;

/**
 * topic 数据库操作实现类
 * 
 * @version 1.0
 */
@Repository
public class TopicInfoDaoImpl extends MybatisEntityDao<TopicInfo> implements TopicInfoDao {

    @Override
    protected String namespaceForSqlId() {
        return "com.jim.im.topic.dao.TopicInfoDao";
    }

    @Override
    public void removeByNames(Collection<String> topicNames) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            session.delete(fullSqlId("removeByNames"), topicNames);
            session.commit(true);
        }
    }

    @Override
    public List<TopicInfo> findByTopicNames(Collection<String> topicNames) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.selectList(fullSqlId("findByTopicNames"), topicNames);
        }
    }
}
