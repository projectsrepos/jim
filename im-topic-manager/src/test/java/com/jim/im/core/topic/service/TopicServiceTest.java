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
package com.jim.im.core.topic.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jim.im.api.TopicRpcService;
import com.jim.im.consts.TopicOwnerType;
import com.jim.im.consts.TopicType;
import com.jim.im.db.entity.topic.TopicRequest;
import com.jim.im.exception.ImException;
import com.jim.im.exception.TopicRpcException;
import com.jim.im.topic.TopicServer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TopicServer.class)
@WebIntegrationTest
// @Transactional
public class TopicServiceTest {
    @Autowired
    private TopicRpcService topicRpcService;

    private RestTemplate restTemplate;
    private TopicRequest request;
    private Integer operatorId = 9000;

    @Value("${server.port}")
    private int port;
    @Value("${server.context-path}")
    private String contextPath;

    private String getUrl(String sourcePath) {
        return "http://localhost:" + port + contextPath + sourcePath;
    }

    @Before
    public void init() throws TopicRpcException {
        restTemplate = new TestRestTemplate();
        request = TopicRequest.build(operatorId, "sfa", "hengda", TopicOwnerType.GROUP, TopicType.values());

        // 自定义数据
        Map<String, List<String>> headers = Maps.newHashMap();
        headers.put("appId", Lists.newArrayList("sfa"));
        headers.put("tenantId", Lists.newArrayList("hengda"));
        headers.put("operatorId", Lists.newArrayList(operatorId.toString()));
        CommHeaderInterceptor headerInterceptor = new CommHeaderInterceptor(headers);
        restTemplate.getInterceptors().add(headerInterceptor);

        this.testDeleteTopics();
    }

    // 单元测试
    @Test
    public void testTopicInfo() throws ImException {
        topicRpcService.createTopics(request);
        topicRpcService.subscribeTopic(request, operatorId);
        System.out.println("subscribe after.===================");
        testGetSubTopics();
        System.out.println("unsubscribe after.=================");
        testUnSubscribe();
        testGetSubTopics();
        testDeleteTopics();
    }

    public void testUnSubscribe() throws ImException {
        topicRpcService.unSubscribeTopic(request, operatorId);
    }

    public void testSubscribe() throws TopicRpcException {
        topicRpcService.subscribeTopic(request, operatorId);
    }

    // restful 接口
    public void testGetSubTopics() {
        String result = restTemplate.getForObject(getUrl("/getSubTopics"), String.class);
        System.out.println(result);
        Assert.assertNotNull(result, result);
    }

    public void testDeleteTopics() throws TopicRpcException {
        topicRpcService.deleteTopic(request);
    }
}
