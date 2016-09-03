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
package com.jim.im.group.rest;

import com.jim.im.rest.request.group.InviteUserRequest;
import com.jim.im.rest.request.group.ListGroupRequest;
import com.jim.im.rest.response.GenericResponseObj;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

/**
 * @version 1.0
 * @since 2016-04-15
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = GroupManagerServer.class)
//@WebIntegrationTest
public class GroupRestServiceTest {


    @Value("${server.port}")
    private int port;
    
    @Value("${server.contextPath}")
    private String contextPath;

    private static MultivaluedMap<String, Object> fixedHeader = new MultivaluedHashMap<String, Object>();

    static {
        fixedHeader.add("appId", "sfa");
        fixedHeader.add("tenantId", "hengda");
        fixedHeader.add("operatorId", 1);
    }

    private String getUrl(String sourcePath) {
        return "http://localhost:" + port + contextPath +"/" + sourcePath;
    }


    // 单元测试
//    @Test
    public void testInviteUser() {
        String uri = getUrl("inviteUser");
        Client client= JerseyClientBuilder.newBuilder().build();
        
        InviteUserRequest req = new InviteUserRequest();
        req.groupId = 94;
        req.inviteeIds = "1,2";
        
        Response rsp = client.target(uri).request().accept(MediaType.APPLICATION_JSON).headers(fixedHeader).buildPost(Entity.entity(req, MediaType.APPLICATION_JSON)).invoke();
        GenericResponseObj ret = rsp.readEntity(GenericResponseObj.class);
        if (!ret.isSuccess()) {
            System.out.println(ret.getError());
        }
    }
    
    public void testListGroup() {
        String uri = getUrl("listGroup");
        Client client= JerseyClientBuilder.newBuilder().build();
        
        ListGroupRequest req = new ListGroupRequest();
        req.showAll = true;
        
        Response rsp = client.target(uri).request().accept(MediaType.APPLICATION_JSON).headers(fixedHeader).buildPost(Entity.entity(req, MediaType.APPLICATION_JSON)).invoke();
        GenericResponseObj ret = rsp.readEntity(GenericResponseObj.class);
        if (!ret.isSuccess()) {
            System.out.println(ret.getError());
        } else {
//           List<Group> groups = ret.getData(List<Group>.class);
            System.out.println();
        }
    }
}
