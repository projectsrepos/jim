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

import org.springframework.stereotype.Component;

import com.jim.im.db.base.QueryParameters;
import com.jim.im.task.entity.TaskExcuteTrace;

/**
 * TaskExcuteTrace Dao
 * @version 1.0.0
 */
@Component
public interface TaskExcuteTraceDao{

	/**
	 * @return
	 */
    String namespaceForSqlId();

    /**
     * @param sqlId
     * @return
     */
    String fullSqlId(String sqlId);
    
    /**
     * @param id
     * @return
     */
    public TaskExcuteTrace getById(Serializable id) ;

    /**
     * @param param
     * @return
     */
    public int findResultCount(QueryParameters param);
    

    /**
     * @param param
     * @return
     */
    public List<TaskExcuteTrace> findResults(QueryParameters param) ;
    
    /**
     * @param example
     * @return
     */
    public List<TaskExcuteTrace> findByExample(TaskExcuteTrace example) ;

    /**
     * @param t
     * @return
     */
    public int save(TaskExcuteTrace t) ;
    
    /**
     * @param t
     * @return
     */
    public int add(TaskExcuteTrace t) ;
    
    /**
     * @param t
     * @return
     */
    public int update(TaskExcuteTrace t) ;

}
