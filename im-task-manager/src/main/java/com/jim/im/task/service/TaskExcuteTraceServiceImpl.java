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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jim.im.task.dao.TaskExcuteTraceDaoImpl;
import com.jim.im.task.entity.TaskExcuteTrace;

/**
 * 
 * TaskExcuteTrace Service Impl
 * @version 1.0.0
 */
@Component
public class TaskExcuteTraceServiceImpl implements TaskExcuteTraceService{
	
	@Autowired
	private TaskExcuteTraceDaoImpl dao;

	/*
	 * (non-Javadoc)
	 * @see com.jim.im.task.service.TaskExcuteTraceService#sava(com.jim.im.task.entity.TaskExcuteTrace)
	 */
	@Override
	public void sava(TaskExcuteTrace taskExcuteTrace) {
		dao.save(taskExcuteTrace);
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.jim.im.task.service.TaskExcuteTraceService#findByBean(com.jim.im.task.entity.TaskExcuteTrace)
	 */
	@Override
	public List<TaskExcuteTrace> findByBean(TaskExcuteTrace taskExcuteTrace) {
		return dao.findByExample(taskExcuteTrace);
	}

	/*
	 * (non-Javadoc)
	 * @see com.jim.im.task.service.TaskExcuteTraceService#update(com.jim.im.task.entity.TaskExcuteTrace)
	 */
	@Override
	public void update(TaskExcuteTrace taskExcuteTrace) {
		dao.update(taskExcuteTrace);
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.jim.im.task.service.TaskExcuteTraceService#findById(int)
	 */
	@Override
	public TaskExcuteTrace findById(int id) {
		return dao.getById(id);
	}
}
