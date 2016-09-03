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
package com.jim.im.task.service;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jim.im.db.base.QueryParameters;
import com.jim.im.exception.ImException;
import com.jim.im.task.dao.TaskInfoDao;
import com.jim.im.task.entity.TaskInfo;
import com.jim.im.task.job.IMPushMsgJobHandle;
import com.jim.im.task.job.JobParamer;
import com.jim.im.task.job.QuartzTaskManager;

/**  
 * TODO
 * @version 1.0.0   
 */
@Component
@Transactional(rollbackFor={ImException.class})
public class TaskInfoServiceImpl implements TaskInfoService {
    
    @Autowired
    private TaskInfoDao dao;
    
    @Autowired
    private QuartzTaskManager taskManage;
    
    @Autowired
    private IMPushMsgJobHandle handle;
    
    static Log logger = LogFactory.getLog(TaskInfoServiceImpl.class);
    
    /* (non-Javadoc)
     * @see com.jim.im.task.service.TaskInfoService#save(com.jim.im.task.entity.TaskInfo)
     */
    @Override
    public void save(TaskInfo taskInfo) throws Exception{
    	//保存
        dao.save(taskInfo);
        
        //开始注册调度任务
        initTask(taskInfo);
    }

	/**
	 * init db Stored Job 
	 * @throws ImException 
	 */
	@PostConstruct
    public void initStoredJob() throws ImException {
    	logger.debug("TaskInfoServiceImpl.initStoredJob start:"+new Date());
    	List<TaskInfo> infos = dao.findResults(new QueryParameters());
    	for(TaskInfo info : infos){
    		initTask(info);
    	}
    	logger.debug("TaskInfoServiceImpl.initStoredJob end:"+new Date());
	}
    
    private void initTask(TaskInfo taskInfo) throws ImException{
    	
    	try {
    		//生成任务参数对象
        	JobParamer jobParamer = new JobParamer("im.pushmsg",
        			String.valueOf(taskInfo.getId()), 
                    handle);
        	jobParamer.setCron(taskInfo.getTimerStrategy());
        	jobParamer.setStartTime(taskInfo.getStartTime());
        	jobParamer.setEndTime(taskInfo.getEndTime());
        	jobParamer.setParam(taskInfo);
        	
            taskManage.addJob(jobParamer);
        } catch (Exception e) {
            logger.error("TaskInfoServiceImpl.initTask error:\n \tid:"
    				+taskInfo.getId()+";date:"+new Date());
            throw new ImException("Add Quartz job failed",e);
        }
    }
    

}
