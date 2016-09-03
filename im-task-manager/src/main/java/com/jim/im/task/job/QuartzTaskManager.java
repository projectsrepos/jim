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

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerKey;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**  
 * Quartz Task Manager
 * @version 1.0.0   
 */
@Component
public class QuartzTaskManager {
	
    @Autowired
    private SchedulerFactory schedulerFactory;
	 
    @Autowired
    private IMTaskJobListener jobListener;
    
    static Log logger = LogFactory.getLog(QuartzTaskManager.class);
    
	/**
    * add Quartz Job  
    * @param jobParamer
    * @throws ParseException
    * @throws SchedulerException
     */
    
    public void addJob(JobParamer jobParamer) throws ParseException, SchedulerException {  
            JobDetail jobDetail = getJobDetail(jobParamer);
            CronTrigger trigger = getCronTrigger(jobParamer);
            addJob(jobDetail, trigger);
    }  
    
    /**
     * modify JobDetail  
     * @param jobParamer
     * @throws SchedulerException
      */
     public void modifyJobDetail(JobParamer jobParamer) 
                     throws SchedulerException {  
         
             Scheduler sched = schedulerFactory.getScheduler();
             CronTriggerImpl trigger = (CronTriggerImpl) sched.getTrigger(
                     createTriggerKey(jobParamer));  
             
             JobDetail jobDetail = (JobDetail) sched.getJobDetail(
                     createJobKey(jobParamer));  
             sched.deleteJob(createJobKey(jobParamer));
             jobDetail = getJobDetail(jobParamer);
             
             addJob(jobDetail, trigger);
     }  
     
     /**
     * modify JobTrigger  
     * @param jobParamer
     * @throws SchedulerException
     * @throws ParseException
      */
     public void modifyJobTrigger(JobParamer jobParamer) throws SchedulerException, ParseException {  
         Scheduler sched = schedulerFactory.getScheduler();
         CronTrigger trigger = (CronTrigger) sched.getTrigger(
                 createTriggerKey(jobParamer));  
         
         JobDetail jobDetail = (JobDetail) sched.getJobDetail(
                 createJobKey(jobParamer)); 
         
         sched.deleteJob(createJobKey(jobParamer));
         trigger = getCronTrigger(jobParamer);
         
         addJob(jobDetail, trigger); 
     }  
   
     /**
     * remove Job  
     * @param jobParamer
     * @throws SchedulerException
      */
     public void removeJob(JobParamer jobParamer) throws SchedulerException {  
         Scheduler sched = schedulerFactory.getScheduler();  
         sched.deleteJob(createJobKey(jobParamer));
     }  
   
    /**
    * start all Jobs
     */
     public void startJobs() {  
         try {  
             Scheduler sched = schedulerFactory.getScheduler();  
             sched.start();  
         } catch (Exception e) {  
             throw new RuntimeException(e);  
         }  
     }  
   
     /**
     * 关闭所有定时任务
      */
     public void shutdownJobs() {  
         try {  
             Scheduler sched = schedulerFactory.getScheduler();  
             if (!sched.isShutdown()) {  
                 sched.shutdown();  
             }  
         } catch (Exception e) {  
             throw new RuntimeException(e);  
         }  
     }  
    
     /**
     * get JobDetail  
     * @param jobParamer
     * @return
      */
    private JobDetail getJobDetail(JobParamer jobParamer){
        JobDetailImpl jobDetail = new JobDetailImpl();// 任务名，任务组，任务执行类  
        
        jobDetail.setGroup(jobParamer.getGroupName());
        jobDetail.setName(jobParamer.getId());
        jobDetail.setJobClass(CommonJob.class);
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("jobParamer", jobParamer);
        jobDetail.setJobDataMap(jobDataMap);
        return jobDetail;
    } 
    
    /**
    * getCronTrigger  触发器  
    * @param jobParamer
    * @return
    * @throws ParseException
     */
    private CronTrigger getCronTrigger(JobParamer jobParamer) 
            throws ParseException{
        if(jobParamer.getStartTime()==null){
            jobParamer.setStartTime(new Date());
        }
        CronTriggerImpl trigger = new CronTriggerImpl();
        trigger.setGroup(jobParamer.getGroupName());
        trigger.setName(jobParamer.getId());
        trigger.setCronExpression(jobParamer.getCron());
        trigger.setStartTime(jobParamer.getStartTime());
        trigger.setEndTime(jobParamer.getEndTime());
        return trigger;
    }
    
    /**
    * add Job  
    * @param jobDetail
    * @param trigger
    * @throws SchedulerException
     */
    private void addJob(JobDetail jobDetail, CronTrigger trigger) throws SchedulerException{
        Scheduler sched = schedulerFactory.getScheduler();  
        sched.getListenerManager().addJobListener(jobListener);
        sched.scheduleJob(jobDetail, trigger);  
        // 启动  
        if (!sched.isShutdown()) {  
            sched.start();  
        }  
        logger.debug("addJob(JobDetail,CronTrigger): \njobkey:"+jobDetail.getKey());
    }
  
    /**
    * create TriggerKey  
    * @param jobParamer
    * @return
     */
    public static TriggerKey createTriggerKey(JobParamer jobParamer){
        return createTriggerKey(jobParamer.getGroupName(), 
                jobParamer.getId());
    }
    
    /**
    * create JobKey  
    * @param jobParamer
    * @return
     */
    public static JobKey createJobKey(JobParamer jobParamer){
        return createJobKey(jobParamer.getGroupName(), 
                jobParamer.getId());
    }
    
    /**
    * create TriggerKey  
    * @param groupName
    * @param name
    * @param id
    * @return
     */
    private static TriggerKey createTriggerKey(String groupName,String name){
        return new TriggerKey(groupName+"."+name);
    }
    
    /**
    * create JobKey  
    * @param groupName
    * @param name
    * @param id
    * @return
     */
    private static JobKey createJobKey(String groupName,String name){
        return new JobKey(groupName+"."+name);
    }
}  
