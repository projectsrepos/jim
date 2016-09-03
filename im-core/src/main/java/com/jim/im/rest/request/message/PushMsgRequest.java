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
package com.jim.im.rest.request.message;

import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class PushMsgRequest {
    @NotEmpty(message = "用户参数不能为空")
    @Size(max = 199, message = "接收者超限, 各个id间以逗号分隔, 少于100个")
    public String receiverIds;
    @NotEmpty(message = "聊天內容不能为空")
    public String content;
    public String shortContent;
}
