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
package com.jim.im.task.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.jim.im.db.base.QueryParameters;
import com.jim.im.task.entity.TaskExcuteTrace;
import com.jim.im.utils.Assert;

/**
 * TaskExcuteTrace Dao implement
 * @version 1.0.0
 */
@Component
public class TaskExcuteTraceDaoImpl implements TaskExcuteTraceDao{
	protected SqlSessionFactory sqlSessionFactory;

	/*
	 * (non-Javadoc)
	 * @see com.jim.im.task.dao.TaskExcuteTraceDao#namespaceForSqlId()
	 */
	@Override
	public String namespaceForSqlId(){
		return "mapper.taskExcuteTrace";
    }

	/*
	 * (non-Javadoc)
	 * @see com.jim.im.task.dao.TaskExcuteTraceDao#fullSqlId(java.lang.String)
	 */
	@Override
	public String fullSqlId(String sqlId) {
        return namespaceForSqlId() + "." + sqlId;
    }
    
	/*
	 * (non-Javadoc)
	 * @see com.jim.im.task.dao.TaskExcuteTraceDao#getById(java.io.Serializable)
	 */
    @Override
    public TaskExcuteTrace getById(Serializable id) {
        Assert.isNotNull(id);
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.selectOne(fullSqlId("getById"), id);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.jim.im.task.dao.TaskExcuteTraceDao#findResultCount(com.jim.im.db.base.QueryParameters)
     */
    @Override
    public int findResultCount(QueryParameters param) {
        Assert.isNotNull(param);
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.selectOne(fullSqlId("findResultCount"), param);
        }
    }
    
    /*
     * (non-Javadoc)
     * @see com.jim.im.task.dao.TaskExcuteTraceDao#findResults(com.jim.im.db.base.QueryParameters)
     */
    @Override
    public List<TaskExcuteTrace> findResults(QueryParameters param) {
        Assert.isNotNull(param);
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.selectList(fullSqlId("findResults"), param);
        }
    }
    
    /*
     * (non-Javadoc)
     * @see com.jim.im.task.dao.TaskExcuteTraceDao#findByExample(com.jim.im.task.entity.TaskExcuteTrace)
     */
    @Override
    public List<TaskExcuteTrace> findByExample(TaskExcuteTrace example) {
        Assert.isNotNull(example);
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.selectList(fullSqlId("findByExample"), example);
        }
    }
    
    /*
     * (non-Javadoc)
     * @see com.jim.im.task.dao.TaskExcuteTraceDao#save(com.jim.im.task.entity.TaskExcuteTrace)
     */
    @Override
    public int save(TaskExcuteTrace t) {
        Assert.isNotNull(t);
        if (t.getId() != null) {
            return update(t);
        } else {
           return add(t);
        }
    }
    
    /*
     * (non-Javadoc)
     * @see com.jim.im.task.dao.TaskExcuteTraceDao#add(com.jim.im.task.entity.TaskExcuteTrace)
     */
    @Override
    public int add(TaskExcuteTrace t) {
        Assert.isNotNull(t);
        try (SqlSession session = sqlSessionFactory.openSession()) {
            int ret = session.insert(fullSqlId("add"), t);
            session.commit(true);
            return ret;
        }
    }
    
    /*
     * (non-Javadoc)
     * @see com.jim.im.task.dao.TaskExcuteTraceDao#update(com.jim.im.task.entity.TaskExcuteTrace)
     */
    @Override
    public int update(TaskExcuteTrace t) {
        Assert.isNotNull(t);
        try (SqlSession session = sqlSessionFactory.openSession()) {
            int ret = session.update(fullSqlId("update"), t);
            session.commit(true);
            return ret;
        }
    }
    
    @Autowired
    @Qualifier("sqlSessionFactory")
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

}
