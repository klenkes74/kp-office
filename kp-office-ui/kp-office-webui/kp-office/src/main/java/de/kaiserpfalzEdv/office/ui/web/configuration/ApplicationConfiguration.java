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

import com.google.common.eventbus.EventBus;
import com.vaadin.spring.annotation.UIScope;
import de.kaiserpfalzEdv.commons.jee.eventbus.EventBusHandler;
import de.kaiserpfalzEdv.commons.jee.eventbus.SimpleEventBusHandler;
import de.kaiserpfalzEdv.office.ui.web.api.menu.MenuEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
@Configuration
public class ApplicationConfiguration implements ApplicationContextAware {
    private static Logger LOG = LoggerFactory.getLogger(ApplicationConfiguration.class);
    ThreadLocal<EventBusHandler> eventBus = new ThreadLocal<EventBusHandler>() {
        @Override
        protected EventBusHandler initialValue() {
            String busName = "EventBus-" + Thread.currentThread().getName();

            SimpleEventBusHandler result = new SimpleEventBusHandler();
            result.setBus(new EventBus(busName));

            LOG.debug("Created event bus: {}", busName);
            return result;
        }
    };
    /**
     * Application context to read beans from.
     */
    private ApplicationContext context;


    public ApplicationConfiguration() {
        LOG.trace("***** Created: {}", this);
    }

    @PostConstruct
    public void init() {
        LOG.debug("***** Initialized: {}", this);
    }

    @PreDestroy
    public void close() {
        LOG.trace("***** Destroyed: {}", this);
    }


    @Bean
    @Scope("prototype")
    public EventBusHandler guavaEventBus() {
        return eventBus.get();
    }


    @UIScope
    @Bean
    public List<MenuEntry> menuEntries() {
        Map<String, MenuEntry> entries = context.getBeansOfType(MenuEntry.class);

        LOG.trace("Found menu entries: {}", entries.keySet());
        ArrayList<MenuEntry> result = new ArrayList<>(entries.size());

        result.addAll(entries.values());

        return result;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        LOG.debug("Spring application context change: {} -> {}", context, applicationContext);

        this.context = applicationContext;
    }
}