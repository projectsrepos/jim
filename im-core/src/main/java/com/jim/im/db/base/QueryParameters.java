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

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.jim.im.utils.Assert;
import com.jim.im.utils.ImParamExceptionAssert;

/**
 * 查询参数
 *
 * @version 1.0
 */
public class QueryParameters {

    private Map<String, Object> params = new HashMap<String, Object>();

    private Map<String, String> sorts = new HashMap<String, String>();

    public QueryParameters() {
    }
    
    public static QueryParameters newInstance() {
        return new QueryParameters();
    }

    private PageInfo page;

    /**
     * 添加一个命名参数
     *
     * @param key 参数名称
     * @param value 参数值
     * @return 当前对象本身
     */
    public QueryParameters addParam(String key, Object value) {
        Assert.notBlank(key);
        if (value == null || "".equals(value)) {
            return this;
        }
        params.put(key, value);
        return this;
    }

    /**
     * 添加一组命名参数
     *
     * @param params 其它参数MAP
     * @return 当前对象本身
     */
    public QueryParameters addParams(Map<String, Object> params) {
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                addParam(entry.getKey(), entry.getValue());
            }
        }
        return this;
    }

    /**
     * 添加一个排序参数
     *
     * @param key 参数名称
     * @param ascDesc 升序或降序
     * @return 当前对象本身
     */
    public QueryParameters addSort(String key, String ascDesc) {
        Assert.notBlank(key);
        if (StringUtils.isBlank(ascDesc)) {
            return this;
        }
        if ("asc".equalsIgnoreCase(ascDesc)) {
            sorts.put(key, "asc");
        } else if ("desc".equalsIgnoreCase(ascDesc)) {
            sorts.put(key, "desc");
        }
        return this;
    }

    /**
     * 添加一组排序参数
     * 
     * @param sorts
     * @return
     */
    public QueryParameters addSorts(Map<String, String> sorts) {
        if (sorts != null && !sorts.isEmpty()) {
            for (Map.Entry<String, String> entry : sorts.entrySet()) {
                addSort(entry.getKey(), entry.getValue());
            }
        }
        return this;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public Map<String, String> getSorts() {
        return sorts;
    }

    public PageInfo getPage() {
        return page;
    }

    public void setPage(PageInfo page) {
        this.page = page;
    }

}
