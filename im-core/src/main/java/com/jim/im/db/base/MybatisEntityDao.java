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
package com.jim.im.db.base;

import com.jim.im.utils.Assert;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 基于MyBatis的基本仓储实现
 *
 * @version 1.0
 */
public abstract class MybatisEntityDao<T extends Entity> implements EntityDao<T> {
    @Autowired
    @Qualifier("sqlSessionFactory")
    protected SqlSessionFactory sqlSessionFactory;

    protected abstract String namespaceForSqlId();

    protected String fullSqlId(String sqlId) {
        return namespaceForSqlId() + "." + sqlId;
    }

    @Override
    public T getById(Serializable id) {
        Assert.isNotNull(id);
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.selectOne(fullSqlId("getById"), id);
        }
    }

    @Override
    public int findResultCount(QueryParameters param) {
        Assert.isNotNull(param);
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.selectOne(fullSqlId("findResultCount"), param);
        }
    }
    
    @Override
    public int findResultCount() {
        QueryParameters param = QueryParameters.newInstance();
        return findResultCount(param);
    }

    @Override
    public List<T> findResults(QueryParameters param) {
        Assert.isNotNull(param);
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.selectList(fullSqlId("findResults"), param);
        }
    }
    
    @Override
    public List<T> findByExample(T example) {
        Assert.isNotNull(example);
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.selectList(fullSqlId("findByExample"), example);
        }
    }
    
    @Override
    public int findByExampleCount(T example) {
        Assert.isNotNull(example);
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.selectOne(fullSqlId("findByExampleCount"), example);
        }
    }

    @Override
    public int save(T t) {
        Assert.isNotNull(t);
        if (t.getId() != null) {
            return update(t);
        } else {
           return add(t);
        }
    }
    
    @Override
    public int add(T t) {
        Assert.isNotNull(t);
        try (SqlSession session = sqlSessionFactory.openSession()) {
            int ret = session.insert(fullSqlId("add"), t);
            session.commit(true);
            return ret;
        }
    }
    
    
    @Override
    public int update(T t) {
        Assert.isNotNull(t);
        try (SqlSession session = sqlSessionFactory.openSession()) {
            int ret = session.update(fullSqlId("update"), t);
            session.commit(true);
            return ret;
        }
    }
    
    

    @Override
    @SuppressWarnings("unchecked")
    public int addBatch(T... t) {
        Assert.check(t.length >= 1 , "param can't be null");
        try (SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH)) {
            int ret = session.insert(fullSqlId("addBatch"), t);
            session.commit(true);
            return ret;
        }
    }
    
    @Override
    public int updateSpecify(T t) {
        Assert.isNotNull(t);
        int count = 0;
        try (SqlSession session = sqlSessionFactory.openSession()) {
            count = session.update(fullSqlId("updateSpecify"), t);
            session.commit(true);
        }
        return count;
    }

    @Override
    public int removeById(Serializable id) {
        Assert.isNotNull(id);
        try (SqlSession session = sqlSessionFactory.openSession()) {
            int ret = session.delete(fullSqlId("removeById"), id);
            session.commit(true);
            return ret;
        }
    }

    @Override
    public int remove(T t) {
        Assert.isNotNull(t);
        try (SqlSession session = sqlSessionFactory.openSession()) {
            int ret = session.delete(fullSqlId("remove"), t);
            session.commit(true);
            return ret;
        }
    }

    protected <E> List<E> doGenericSelect(Map<String, Object> param, String sqlId) {
        Assert.isNotNull(param);
        Assert.isNotNull(sqlId);
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.selectList(fullSqlId(sqlId), param);
        }
    }
}
