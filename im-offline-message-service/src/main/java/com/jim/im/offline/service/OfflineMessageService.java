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
package com.jim.im.offline.service;

import com.jim.im.exception.ImParamException;
import com.jim.im.mongo.entity.msg.ImMessage;
import java.math.BigInteger;
import java.util.Collection;

/**
 * 离线消息服务接口
 * 
 * @version 1.0
 */
public interface OfflineMessageService {
    Collection<ImMessage> getOfflineMessages(Collection<String> topics, BigInteger lastMessageId) throws ImParamException;
}
