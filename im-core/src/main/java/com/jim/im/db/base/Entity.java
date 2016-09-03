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

/**
 * 领域实体接口。所有实体类都要直接或间接实现这个接口。它主要起标记作用，以便于统一处理系统中的实体等。
 *
 */
public interface Entity extends Serializable {

    /**
     * 取得实体的Id。实体类的每个实例都必须有个唯一Id以标识自身。 实体Id必须是可序列化的。
     *
     * @return 实体实例的 Id.
     */
    Serializable getId();

}
