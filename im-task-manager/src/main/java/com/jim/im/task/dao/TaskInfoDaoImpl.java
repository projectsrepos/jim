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

import org.springframework.stereotype.Component;

import com.jim.im.db.base.MybatisEntityDao;
import com.jim.im.task.entity.TaskInfo;

/**  
 * TaskInfo Dao implement
 * @version 1.0.0   
 */
@Component
public class TaskInfoDaoImpl extends MybatisEntityDao<TaskInfo> implements TaskInfoDao {

    /* (non-Javadoc)
     * @see com.jim.im.db.base.MybatisEntityDao#namespaceForSqlId()
     */
    @Override
    protected String namespaceForSqlId() {
        return "mapper.taskInfo";
    }

}
