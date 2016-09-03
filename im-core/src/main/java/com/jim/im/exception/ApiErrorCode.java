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
package com.jim.im.exception;

/**
 * 
 * @version 1.0.0
 */
public enum ApiErrorCode {

    /**
     * 其他不好分类的受控异常
     * */
    INNER_ERROR("9000"), 
    
    /**
     * rest请求中参数错误
     * */
    PARAM_ERROR("1000"), 
    
    /**
     * 系统资源限制，保留的，例如控制每个节点最多承载5W个用户，更多用户接入则报此异常，拒绝服务
     * */
    RESOURCE_LIMIT("2000"), 
    
    /**
     * 账户资源限制，保留的，例如某个用户限定发送5w条信息/每天，超过则报此异常
     * */
    ACCOUNT_LIMIT("3000"), 
    
    /**
     * 网络相关的异常，如rpc连接超时
     * */
    NETWORK_ERROR("4000"),
    
    /**
     * 统一捕捉系统运行时异常,区别于受控异常
     * */
    INNER_RUNTIME_ERROR("5000"),
    
    /**
     * 用户没有权限访问时报异常
     * */
    ACCESS_FORBID_ERROR("6000");

    String errorCode;

    private ApiErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

}
