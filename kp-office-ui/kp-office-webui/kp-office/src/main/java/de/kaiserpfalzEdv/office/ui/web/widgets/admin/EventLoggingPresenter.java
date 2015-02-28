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

package de.kaiserpfalzEdv.office.ui.web.widgets.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.spring.events.Event;
import org.vaadin.spring.navigator.Presenter;
import org.vaadin.spring.navigator.annotation.VaadinPresenter;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 18.02.15 08:45
 */
@VaadinPresenter(viewName = EventLoggingView.NAME)
public class EventLoggingPresenter extends Presenter<EventLoggingView> {
    private static final Logger LOG = LoggerFactory.getLogger(EventLoggingPresenter.class);

    public EventLoggingPresenter() {
        LOG.trace("Created: {}", this);
    }

    @PostConstruct
    public void init() {
        super.init();
        LOG.trace("Initialized: {}", this);
    }

    @PreDestroy
    public void close() {
        LOG.trace("Destroyed: {}", this);
    }

    public void onStartup(Event<?> event) {
        LOG.debug("{} received: {}", this, event);

        getView().addEvent(event);
    }
}
