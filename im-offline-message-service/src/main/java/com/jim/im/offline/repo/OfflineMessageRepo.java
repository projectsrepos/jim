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

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;
import com.jim.im.mongo.entity.msg.ImMessage;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Repository;
import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @version 1.0
 */
@Repository
public class OfflineMessageRepo {
    @Autowired
    private DB messageDb;

    private DBCollection messageCollection;
    private String collectionName;

    @PostConstruct
    public void init() {
        Class<ImMessage> messageClass = ImMessage.class;
        Document annotation = messageClass.getAnnotation(Document.class);
        collectionName = annotation == null ? messageClass.getSimpleName() : annotation.collection();
        messageCollection = messageDb.getCollection(collectionName);
    }

    public List<ImMessage> findByTopicNameIn(Collection<String> topics, ObjectId lastMsgId) {
        DBObject queryCondition = QueryBuilder.start("topicName")
                                                .in(topics)
                                                .and("_id")
                                                .greaterThanEquals(lastMsgId)
                                                .get();
        Iterator<DBObject> result = messageCollection.find(queryCondition).limit(100).iterator();
        return MessageConverter.convert(result);
    }
}
