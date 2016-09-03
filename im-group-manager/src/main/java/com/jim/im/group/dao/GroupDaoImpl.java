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
package com.jim.im.group.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.jim.im.db.base.MybatisEntityDao;
import com.jim.im.db.entity.group.Group;
import com.jim.im.db.entity.group.User;

/**
 * @version 1.0.0
 */
@Component
public class GroupDaoImpl extends MybatisEntityDao<Group> implements GroupDao {

    @Override
    protected String namespaceForSqlId() {
        return "mapper.group";
    }

    
    @Override
    public List<Group> findGroupEntered(String appId, String tenantId, int userId) {
        Map<String, Object> param = new HashMap<>();
        param.put("appId", appId);
        param.put("tenantId", tenantId);
        param.put("userId", userId);
        return doGenericSelect(param, "findGroupEntered");
    }


}
