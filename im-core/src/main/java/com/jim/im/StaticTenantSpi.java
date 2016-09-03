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
package com.jim.im;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @version 1.0.0
 */
@Component
public class StaticTenantSpi implements TenantSpi{
    protected static List<String> APP_IDS = new ArrayList<String>();
    protected static List<String> TENANT_IDS = new ArrayList<String>();
    protected static Map<String, List<String>> MAP = new HashMap<String, List<String>>();

    static {
        //APP_IDS
        String sfa = "sfa";
        String crm = "crm";

        //TENANT_IDS
        String hengda = "hengda";
        String wahaha = "wahaha";

        //init
        APP_IDS.add(sfa);
        APP_IDS.add(crm);

        TENANT_IDS.add(hengda);
        TENANT_IDS.add(wahaha);

        MAP.put(sfa, TENANT_IDS);
        MAP.put(wahaha, TENANT_IDS);
    }

    @Override
    public List<String> getAllAppId() {
        return APP_IDS;
    }

    @Override
    public Integer getAppIdCount() {
        return APP_IDS.size();
    }

    @Override
    public List<String> getAllTenantId(String appId) {
        return MAP.get(appId);
    }

    @Override
    public Integer getTenantIdCount(String appId) {
        return MAP.get(appId).size();
    }
}
