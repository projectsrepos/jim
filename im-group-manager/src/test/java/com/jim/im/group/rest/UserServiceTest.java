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

import com.jim.im.exception.ImException;
import org.glassfish.jersey.client.JerseyClientBuilder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 */
public class UserServiceTest {
    //@Test
    public void initUsers() throws ImException {
        Client client= JerseyClientBuilder.newBuilder().build();
        Map<String ,Object> header = new HashMap<String, Object>();
        header.put("appId", "sfa");
        header.put("tenantId", "hengda");
        header.put("operatorId", "1");

        for (Integer index = 0; index < 1; index++) {
            Map<String, Object> body = new HashMap<String, Object>();
            body.put("gatewayUserId", index);
            Response rsp = client.target("http://172.16.0.22:8090/api/im/groupManager/createUser")
                                 .request()
                                 .accept(MediaType.APPLICATION_JSON)
                                 .headers(new MultivaluedHashMap<String, Object>(header))
                                 .buildPost(Entity.entity(body, MediaType.APPLICATION_JSON))
                                 .invoke();
            HashMap<String, Object> ret = rsp.readEntity(HashMap.class);
            if("1".equals(ret.get("status").toString())){
                System.out.println("Create user success --> " + index);
            }
        }
    }
}
