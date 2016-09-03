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
 
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.jim.im.db.base.Entity;
import com.jim.im.db.base.GenericEntity;
import com.jim.im.db.entity.group.GroupEnumTypes.GroupUserRole;
 
 /**
* @author xiangshaoxu
* @date 2016-04-13 16:33:42
 * @version 1.0.0
 */
public class GroupUserMap extends GenericEntity implements Entity {

    private static final long serialVersionUID = 1L;
    
    /**  id  */
    private Integer id;
    
    /**  群组ID  */
    private Integer groupId;
    
    /**  用户ID  */
    private Integer userId;
    
    /**  角色  */
    private GroupUserRole role;
    
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }
    
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    public GroupUserRole getRole() {
        return role;
    }

    public void setRole(GroupUserRole role) {
        this.role = role;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
