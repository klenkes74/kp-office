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
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.request.RequestContextListener;
import org.vaadin.spring.EnableVaadin;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
@EnableVaadin
@EnableAutoConfiguration
@ComponentScan(
        value = {"de.kaiserpfalzEdv.office"},
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Repository.class)}
)
@EnableJpaRepositories(
        value = {"de.kaiserpfalzEdv.office"},
        includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Repository.class)}
)
@EntityScan(
        basePackages = {"de.kaiserpfalzEdv.office", "de.kaiserpfalzEdv.commons.jee.db"}
)
public class Application {
    private static Logger LOG = LoggerFactory.getLogger(Application.class);
    

    @PostConstruct
    public void init() {
        LOG.trace("Created: {}", this);
    }
    
    @PreDestroy
    public void close() {
        LOG.trace("Destroyed: {}", this);
    }
    
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Bean
    @ConditionalOnMissingBean(RequestContextListener.class)
    public RequestContextListener requestContextListener() {
        LOG.debug("Loading RequestContextListener.");
        
        return new RequestContextListener();
    }
}