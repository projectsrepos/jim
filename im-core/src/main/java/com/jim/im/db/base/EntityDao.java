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

import java.io.Serializable;
import java.util.List;

/**
 * 仓储访问接口, 提供通用仓储方法
 *
 * @version 1.0
 */
public interface EntityDao<T extends Entity> {

    /**
     * 根据实体ID，查找实体
     *
     * @param id
     * @return
     */
    public T getById(Serializable id);

    /**
     * 添加一个实体
     *
     * @param t
     */
    public int save(T t);

    /**
     * 批量添加实体
     *
     * @param t
     */
    @SuppressWarnings("unchecked")
    public int addBatch(T... t);

    /**
     * 移除一个实体
     *
     * @param t
     * @return TODO
     */
    public int remove(T t);

    /**
     * 根据实体ID，删除实体
     *
     * @param id
     */
    public int removeById(Serializable id);

    /**
     * 查询符合查询参数的实体结果集数量
     *
     * @param param
     * @return
     */
    public int findResultCount(QueryParameters param);

    /**
     * 查询符合查询参数的实体结果集
     *
     * @param param
     * @return
     */
    public List<T> findResults(QueryParameters param);

    /**
     * @param t
     * @return int
     * @Title: updateSpecify
     * @Description: 指定更新
     */
    public int updateSpecify(T t);

    /**
     * @param t
     * @return TODO
     */
    int add(T t);

    /**
     * @param t
     */
    int update(T t);

    /**
     * @return
     */
    int findResultCount();

    /**
     * @param example
     * @return
     */
    List<T> findByExample(T example);

    /**
     * @param example
     * @return
     */
    int findByExampleCount(T example);

}
