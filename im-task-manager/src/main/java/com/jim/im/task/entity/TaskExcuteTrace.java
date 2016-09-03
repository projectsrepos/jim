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
package com.jim.im.task.entity;
 
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.jim.im.db.base.Entity;

/**
 * @version 1.0.0
 */
public class TaskExcuteTrace implements Entity {

    private static final long serialVersionUID = 1L;
    
    private Integer id;
    
    private String groupName;
    
    private Integer taskId;
    
    private String quartzKey;
    
    private Date excuteTime;
    
    private String status;
    
    private String remark;
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    
    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }
    
    public String getQuartzKey() {
        return quartzKey;
    }

    public void setQuartzKey(String quartzKey) {
        this.quartzKey = quartzKey;
    }
    
    public Date getExcuteTime() {
        return excuteTime;
    }

    public void setExcuteTime(Date excuteTime) {
        this.excuteTime = excuteTime;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
