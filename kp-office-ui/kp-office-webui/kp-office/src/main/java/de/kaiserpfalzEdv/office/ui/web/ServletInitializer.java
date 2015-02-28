/*
 * Copyright 2015 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzEdv.office.ui.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 07.02.15 14:31
 */
public class ServletInitializer extends SpringBootServletInitializer {
    private static final Logger LOG = LoggerFactory.getLogger(ServletInitializer.class);

    public ServletInitializer() {
        LOG.trace("Created: {}", this);
    }
    
    @PostConstruct
    public void init() {
        LOG.trace("Initialized: {}", this);
    }
    
    @PreDestroy
    public void close() {
        LOG.trace("Destroyed: {}", this);
    }


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
}
