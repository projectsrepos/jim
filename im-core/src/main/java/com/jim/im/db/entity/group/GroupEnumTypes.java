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
package com.jim.im.db.entity.group;

/**
 * @version 1.0.0
 * @date 2016年3月30日
 */
public interface GroupEnumTypes {

    /**
     * 群组类型
     */
    public enum GroupType {
        COMMON, // 普通群组
        ORGANIZATION;// 系统组织架构群组
    }

    /**
     * 加群策略
     */
    public enum EnterGroupStrategy {
        ENTER_DIRECTLY, // 直接进群，不需要验证
        NEED_AUDIT, // 需要群管理员审核
        PROHIBIT_ENTER;// 禁止进入
    }

    /**
     * 群组拉人（加人）策略
     */
    public enum PullUserStrategy {
        ENTER_DIRECTLY, // 直接加人进群，不需要验证
        NEED_AUDIT; // 仅仅发送系统通知，需要用户同意
    }
    
    /**
     * 群组成员角色
     */
    public enum GroupUserRole {
        CREATER, // 群组创建者
        ADMIN,// 群组管理员
        USER;//普通群成员
    }
}
