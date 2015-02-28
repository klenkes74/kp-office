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

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.spring.annotation.VaadinSessionScope;
import org.vaadin.spring.navigator.annotation.VaadinView;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 18.02.15 08:41
 */
@VaadinSessionScope
@VaadinView(name = EventLoggingView.NAME)
public class EventLoggingView extends VerticalLayout implements View {
    static final         String NAME = "widget.admin.eventLogging";
    private static final Logger LOG  = LoggerFactory.getLogger(EventLoggingView.class);


    public EventLoggingView() {
        LOG.trace("Created: {}", this);
    }

    @PostConstruct
    public void init() {
        LOG.trace("Initialized: {}", this);
    }

    @PreDestroy
    public void close() {
        removeAllComponents();
        LOG.trace("Destroyed: {}", this);
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        LOG.trace("{} received: {}", this, event);
    }


    public void addEvent(org.vaadin.spring.events.Event<?> event) {
        StringBuilder text = new StringBuilder(event.getClass().getSimpleName())
                .append(" (")
                .append(OffsetDateTime.ofInstant(Instant.ofEpochMilli(event.getTimestamp()), ZoneId.systemDefault()))
                .append(", ")
                .append(event.getScope())
                .append("): ")
                .append(event.getPayload().toString());

        addComponent(new Label(text.toString()));
    }
}
