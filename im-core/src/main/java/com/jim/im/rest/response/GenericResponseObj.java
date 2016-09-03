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
package com.jim.im.rest.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.jim.im.utils.Assert;


/**
 * 
 * @version 1.0.0
 */
@JsonInclude(value = Include.NON_NULL)
public abstract class GenericResponseObj {

    public String requestId;
    
    public int status;

    public ErrorObject error;

    public Object data;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 返回错误对象 Notice： 调用前请调用isSuccess()检查，请求failed时候方可以调用此方法
     * */
    public ErrorObject getError() {
        return error;
    }

    /**
     * 返回数据对象 Notice： 调用前请调用isSuccess()检查，请求success时候方可以调用此方法
     * */
    @SuppressWarnings("unchecked")
    public <T extends Serializable> T getData(Class<T> entityType) {
        if (isSuccess()) {
            return (T) data;
        } else {
            return null;
        }
    }

    /**
     * 请求是否正常返回
     * */
    @JsonIgnore
    public boolean isSuccess() {
        return status == 1;
    }


}
