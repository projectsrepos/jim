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

import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.jim.im.utils.RequestEnv;

/**
 * 
 * @version 1.0.0
 */
public class EntityInterceptor {
    
    public void beforeAdd(GenericEntity entity) {
        entity.setCreated(new Date());
        entity.setCreatedBy(RequestEnv.getOperatorId());
    }
    
    public void beforeUpdate(GenericEntity entity) {
        entity.setUpdated(new Date());
        entity.setUpdatedBy(RequestEnv.getOperatorId());
    }
    
}
