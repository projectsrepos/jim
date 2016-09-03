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
package com.jim.im.offline.config;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * @version 1.0
 */
@EnableConfigurationProperties({MongoProperties.class})
@Configuration
public class OfflineMongoConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(OfflineMongoConfig.class);

    @Autowired
    private MongoProperties properties;

    @Bean
    public MongoClient mongoClient() {
        ServerAddress serverAddress = null;
        try {
            serverAddress = new ServerAddress(properties.getHost(), properties.getPort());
        } catch (UnknownHostException e) {
            LOGGER.error("Init mongo client failure, cause in UnknownHostException!", e);
        }
        MongoCredential credential = MongoCredential.createScramSha1Credential(properties.getUsername(),
                                                                               properties.getAuthenticationDatabase(),
                                                                               properties.getPassword());
        return new MongoClient(serverAddress, Arrays.asList(credential));
    }

    @Bean
    public DB db() {
        return mongoClient().getDB(properties.getDatabase());
    }
}
