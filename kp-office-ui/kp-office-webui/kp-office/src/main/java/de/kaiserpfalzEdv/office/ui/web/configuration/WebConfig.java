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

package de.kaiserpfalzEdv.office.ui.web.configuration;

import ch.qos.logback.classic.selector.servlet.LoggerContextFilter;
import com.vaadin.spring.server.SpringVaadinServlet;
import de.kaiserpfalzEdv.commons.jee.servlet.filter.ContextLogInjector;
import de.kaiserpfalzEdv.commons.jee.servlet.filter.HttpApplicationDataEnricher;
import de.kaiserpfalzEdv.commons.jee.servlet.model.ApplicationMetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 07.02.15 14:31
 */
@Configuration
public class WebConfig {
    private static final Logger LOG = LoggerFactory.getLogger(WebConfig.class);

    @Inject
    private ApplicationMetaData applicationData;


    public WebConfig() {
        LOG.trace("Created: {}", this);
    }

    @PostConstruct
    public void init() {
        LOG.trace("Initialized: {}", this);
        LOG.trace("  application data: {}", applicationData);
    }

    @PreDestroy
    public void close() {
        LOG.trace("Destroyed: {}", this);
    }


    @Bean
    public SpringVaadinServlet uiServlet() {
        return new SpringVaadinServlet();
    }


    @Bean
    @ConditionalOnMissingBean(RequestContextListener.class)
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }


    @Bean
    @ConditionalOnMissingBean(LoggerContextFilter.class)
    public LoggerContextFilter loggerContextFilter() {
        return new LoggerContextFilter();
    }

    @Bean
    @ConditionalOnMissingBean(HttpApplicationDataEnricher.class)
    public HttpApplicationDataEnricher httpApplicationDataEnricher() {
        HttpApplicationDataEnricher result = new HttpApplicationDataEnricher();

        result.setApplication(applicationData);

        return result;
    }

    @Bean
    @ConditionalOnMissingBean(ContextLogInjector.class)
    public ContextLogInjector contextLogInjector() {
        ContextLogInjector result = new ContextLogInjector();

        result.setApplication(applicationData);

        return result;
    }
}
