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

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import de.kaiserpfalzEdv.office.ui.web.presenter.Action;
import de.kaiserpfalzEdv.office.ui.web.presenter.MainPresenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.spring.VaadinUI;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.events.EventScope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

/**
 * The main UI of the application
 */
@Theme("valo")
@Widgetset("KPOfficeWidgetset")
@VaadinUI
public class KPOfficeUI extends UI {
    private static final long serialVersionUID = -8687618756473432618L;
    private static final Logger LOG = LoggerFactory.getLogger(KPOfficeUI.class);


    @Inject
    EventBus eventBus;

    @Inject
    MainPresenter presenter;


    @PostConstruct
    public void init() {
        LOG.trace("Created: {}", this);
    }
    
    @PreDestroy
    public void close() {
        LOG.trace("Destroyed: {}", this);
    }

    
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        eventBus.publish(EventScope.SESSION, this, Action.START);
        setContent(presenter.getView());
    }
}
