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

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import com.jim.im.db.base.MybatisEntityDao;
import com.jim.im.db.base.QueryParameters;
import com.jim.im.db.entity.group.User;

/**
 * 
 * @version 1.0.0
 */
@Component
public class UserDaoImpl extends MybatisEntityDao<User>  implements UserDao {

    
    @Override
    protected String namespaceForSqlId() {
        return "mapper.user";
    }

    @Override
    public int findUsersCountInUserIds(String appId, String tenantId, Integer... userIds) {
        QueryParameters param = QueryParameters.newInstance();
        param.addParam("appId", appId).addParam("tenantId", tenantId).addParam("userIds", userIds);
        try(SqlSession session = sqlSessionFactory.openSession()) {
            return session.selectOne(fullSqlId("findUsersCountInUserIds"), param);
        }
    }
    
    @Override
    public List<User> listGroupUser(String appId, String tenantId, Integer groupId) {
        Map<String, Object> param = new HashMap<>();
        param.put("appId", appId);
        param.put("tenantId", tenantId);
        param.put("groupId", groupId);
        return doGenericSelect(param, "listGroupUser");
    }

}
