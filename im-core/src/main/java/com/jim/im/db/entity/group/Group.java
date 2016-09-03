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
 */package com.jim.im.db.entity.group;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

import com.jim.im.db.base.GenericEntity;
import com.jim.im.db.entity.group.GroupEnumTypes.EnterGroupStrategy;
import com.jim.im.db.entity.group.GroupEnumTypes.GroupType;
import com.jim.im.db.entity.group.GroupEnumTypes.PullUserStrategy;

/**
 * @author xiangshaoxu
 * @version 1.0.0
 */
public class Group extends GenericEntity {

    private static final long serialVersionUID = 1L;

    /** 群组名称 */
    @NotBlank(message="群组名称不能为空")
    private String name;

    /** 群组类型 */
    @NotNull(message="群组类型不能为空") 
    private GroupType groupType;

    /** 加群策略 */
    private EnterGroupStrategy enterGroupStrategy = EnterGroupStrategy.ENTER_DIRECTLY;

    /** 拉人入群策略 */
    private PullUserStrategy pullUserStrategy = PullUserStrategy.ENTER_DIRECTLY;

    /** 描述 */
    private String description;

    /** id */
    private Integer id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GroupType getGroupType() {
        return groupType;
    }

    public void setGroupType(GroupType groupType) {
        this.groupType = groupType;
    }

    public EnterGroupStrategy getEnterGroupStrategy() {
        return enterGroupStrategy;
    }

    public void setEnterGroupStrategy(EnterGroupStrategy enterGroupStrategy) {
        this.enterGroupStrategy = enterGroupStrategy;
    }

    public PullUserStrategy getPullUserStrategy() {
        return pullUserStrategy;
    }

    public void setPullUserStrategy(PullUserStrategy pullUserStrategy) {
        this.pullUserStrategy = pullUserStrategy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
