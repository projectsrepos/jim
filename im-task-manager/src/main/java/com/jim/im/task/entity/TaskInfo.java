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

import com.jim.im.db.base.GenericEntity;
 /**
  * TaskInfo
 * @version 1.0.0   
 */
public class TaskInfo extends GenericEntity{

    private static final long serialVersionUID = 1L;
    
    /**  id  */
    private Integer id;
    
    
    /**  operaterid  */
    private Integer operatorid;
    
    /**  pushType  */
    private String pushType;
    
    /**  ids  */
    private String pushIds;
    
    /**  content  */
    private String content;
    
    /**  pushContent  */
    private String pushContent;
    
    /**  startTime  */
    private Date startTime;
    
    /**  endTime  */
    private Date endTime;
    
    /**  timerStrategy  */
    private String timerStrategy;
    
    /**  TimerStatus  */
    private String TimerStatus;
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getOperatorid() {
        return operatorid;
    }

    public void setOperatorid(Integer operatorid) {
        this.operatorid = operatorid;
    }

    public String getPushType() {
        return pushType;
    }

    public void setPushType(String pushType) {
        this.pushType = pushType;
    }
    
    public String getPushIds() {
        return pushIds;
    }

    public void setPushIds(String pushIds) {
        this.pushIds = pushIds;
    }
    
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    public String getPushContent() {
        return pushContent;
    }

    public void setPushContent(String pushContent) {
        this.pushContent = pushContent;
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
    
    public String getTimerStrategy() {
        return timerStrategy;
    }

    public void setTimerStrategy(String timerStrategy) {
        this.timerStrategy = timerStrategy;
    }
    
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
    
    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }
    
    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
    
    public String getTimerStatus() {
        return TimerStatus;
    }

    public void setTimerStatus(String TimerStatus) {
        this.TimerStatus = TimerStatus;
    }
    
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
