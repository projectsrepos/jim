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
package com.jim.im.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import com.jim.im.db.base.EntityInterceptor;
import com.jim.im.db.base.GenericEntity;
import com.jim.im.exception.ImParamException;
import com.jim.im.exception.ImRuntimeException;
import com.jim.im.utils.ImParamExceptionAssert;
import com.jim.im.utils.RequestEnv;

/**
 * 
 * @date 2016年4月26日
 * @version 1.0.0
 */
public abstract class GenericAopConfig {

    @Bean
    EntityInterceptor entityInterceptor() {
        return new EntityInterceptor();
    }
    
    @Autowired
    EntityInterceptor entityInterceptor;
    
    @Pointcut("execution(* com.jim.im.db.base.MybatisEntityDao.update(..)) || execution(* com.jim.im.db.base.MybatisEntityDao.updateSpecify(..)) ")  
    protected void updatePointCut() {
    }
    
    @Pointcut("execution(* com.jim.im.db.base.MybatisEntityDao.add(..))")  
    protected void addPointCut() {
    }
    
    @Pointcut("execution(* com.jim.im.db.base.MybatisEntityDao.addBatch(..))")  
    protected void addBatchPointCut() {
    }
    
    @Pointcut("execution(public * com.jim.im.*.rest.*Rest*.*(..))")  
    protected void restApiPointCut() {
    }
    
    
    @Around("updatePointCut()")  
    protected Object doAroundUpdate(ProceedingJoinPoint pjp) throws Throwable {  
        Object arg = pjp.getArgs()[0];
        if (arg instanceof GenericEntity) {
            entityInterceptor.beforeUpdate((GenericEntity)arg);
        }
        Object o = pjp.proceed();  
        return o;  
    }  
    
    @Around("addPointCut()")  
    protected Object doAroundAdd(ProceedingJoinPoint pjp) throws Throwable {  
        Object arg = pjp.getArgs()[0];
        if (arg instanceof GenericEntity) {
            entityInterceptor.beforeAdd((GenericEntity)arg);
        }
        Object o = pjp.proceed();  
        return o;  
    }  
    
    @Around("addBatchPointCut()")  
    protected Object doAroundAddBatch(ProceedingJoinPoint pjp) throws Throwable {  
        Object arr = pjp.getArgs()[0];
        if (arr.getClass().isArray()) {
            for (Object obj : (Object[])arr) {
                if (obj instanceof GenericEntity) {
                    entityInterceptor.beforeAdd((GenericEntity)obj);
                }
            }
        }
        Object o = pjp.proceed();  
        return o;  
    }  
    
    @Before("restApiPointCut()")
    protected void beforeRestApi(JoinPoint jp) throws ImParamException {
        String appId = RequestEnv.getAppId();
        ImParamExceptionAssert.isNotBlank(appId, "appId不能为空");
        
        String tenanatId = RequestEnv.getTenantId();
        ImParamExceptionAssert.isNotBlank(tenanatId, "tenanatId不能为空");
        
        Integer operatorId = RequestEnv.getOperatorId();
        ImParamExceptionAssert.isNotNull(operatorId, "operatorId不能为空且必须为数字");
    }
    
    @AfterThrowing(value = "restApiPointCut()" , throwing = "e")
    protected void afterThrowingRestApi(JoinPoint jp, RuntimeException e) throws ImParamException {
        throw new ImRuntimeException(e);
    }
    
}
