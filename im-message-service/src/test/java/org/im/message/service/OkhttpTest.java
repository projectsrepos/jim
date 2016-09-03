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
package org.im.message.service;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.junit.Test;

import com.jim.im.rest.request.group.ListGroupRequest;
import com.jim.im.utils.JsonUtil;

/**
 * 
 * @version 1.0.0
 */
public class OkhttpTest {

//    @Test
    public void test() throws IOException {
        OkHttpClient client = new OkHttpClient.Builder().build();
        String url = "http://127.0.0.1:8090/api/im/groupManager/listGroup";
        ListGroupRequest reqObj = new ListGroupRequest();
        reqObj.showAll = true;
        
        RequestBody body = RequestBody.create(MediaType.parse(javax.ws.rs.core.MediaType.APPLICATION_JSON), JsonUtil.toJson(reqObj));
        Request req = new Request.Builder().addHeader("appId", "sfa").addHeader("operatorId", "1").addHeader("tenantId", "hengda").addHeader("Content-Type", "application/json").url(url).post(body).build();
        Response resp = client.newCall(req).execute();
        resp.body().string();
    }

}
