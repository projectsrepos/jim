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
package com.jim.im.task.job;

import java.util.Date;
import java.util.Map;

/**
 * @version 1.0.0
 */
public class JobParamer {
    private String groupName;
    
    private String id;
    
    private AbstractJobHandle handle;
    
    private String cron;
    
    private Date startTime;
    
    private Date endTime;
    
    private Object param;
    
    /**
     * @param groupName
     * @param name
     * @param id
     * @param handle
     * @param cron
     * @param startTime
     * @param endTime
     * @param param
     */
    public JobParamer(String groupName, String id, AbstractJobHandle handle,
            String cron, Date startTime, Date endTime, Map<String, Object> param) {
        super();
        this.groupName = groupName;
        this.id = id;
        this.handle = handle;
        this.cron = cron;
        this.startTime = startTime;
        this.endTime = endTime;
        this.param = param;
    }
    

    /**
     * @param groupName
     * @param name
     * @param handle
     * @param cron
     * @param param
     */
    public JobParamer(String groupName, String id, AbstractJobHandle handle, String cron,
            Map<String, Object> param) {
        this.groupName = groupName;
        this.id = id;
        this.handle = handle;
        this.cron = cron;
        this.param = param;
        this.startTime= new Date();
    }
    
    



    /**
     * @param groupName
     * @param id
     * @param handle
     */
    public JobParamer(String groupName,String id, AbstractJobHandle handle) {
		this.groupName = groupName;
		this.handle = handle;
		this.id = id;

	}


	public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    
    public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public AbstractJobHandle getHandle() {
        return handle;
    }

    public void setHandle(AbstractJobHandle handle) {
        this.handle = handle;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }


	public Object getParam() {
		return param;
	}


	public void setParam(Object param) {
		this.param = param;
	}

}
