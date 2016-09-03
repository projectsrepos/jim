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
package com.jim.im.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.jim.im.exception.ImAllRuntimeExceptionMapper;
import com.jim.im.exception.ImExceptionMapper;
import com.jim.im.exception.ImJsonMappingExceptionMapper;
import com.jim.im.exception.ImJsonParseExceptionMapper;
import com.jim.im.exception.ImRuntimeExceptionMapper;
import com.jim.im.exception.ImValidationExceptionMapper;

/** 通用 jersey 配置 */
@Configuration
public abstract class GenericJerseyConfig extends ResourceConfig {

    public GenericJerseyConfig() {
        super.property(ServerProperties.BV_DISABLE_VALIDATE_ON_EXECUTABLE_OVERRIDE_CHECK, true);
        register(ValidationConfigurationContextResolver.class);
        register(ImAllRuntimeExceptionMapper.class);
        register(ImJsonMappingExceptionMapper.class);
        register(ImJsonParseExceptionMapper.class);
        register(ImValidationExceptionMapper.class);
        register(ImExceptionMapper.class);
        register(ImRuntimeExceptionMapper.class);
    }

    @Bean
    CharacterEncodingFilter characterEncodingFilter() {
        return new CharacterEncodingFilter("UTF8", true);
    }
}
