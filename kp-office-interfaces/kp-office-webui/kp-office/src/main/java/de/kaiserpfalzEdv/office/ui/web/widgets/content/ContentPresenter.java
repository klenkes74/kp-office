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

package de.kaiserpfalzEdv.office.ui.web.widgets.content;

import de.kaiserpfalzEdv.office.ui.web.Action;
import de.kaiserpfalzEdv.office.ui.web.mainScreen.StartupFilter;
import de.kaiserpfalzEdv.office.ui.web.widgets.about.AboutContent;
import de.kaiserpfalzEdv.office.ui.web.widgets.admin.EventLoggingPresenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.spring.annotation.VaadinSessionScope;
import org.vaadin.spring.events.Event;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.events.EventScope;
import org.vaadin.spring.events.annotation.EventBusListenerMethod;
import org.vaadin.spring.navigator.Presenter;
import org.vaadin.spring.navigator.annotation.VaadinPresenter;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import java.util.UUID;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 17.02.15 20:38
 */
@VaadinSessionScope
@VaadinPresenter(viewName = ContentView.NAME)
public class ContentPresenter extends Presenter<ContentView> {
    private static final Logger LOG = LoggerFactory.getLogger(ContentPresenter.class);

    @Inject
    private EventBus eventBus;

    @Inject
    private AboutContent about;

    @Inject
    private EventLoggingPresenter eventLogger;


    private UUID splashScreenTabId = UUID.randomUUID();
    private UUID loggingTabId = UUID.randomUUID();

    public ContentPresenter() {
        LOG.trace("Created: {}", this);
    }

    @PostConstruct
    public void init() {
        super.init();
        LOG.trace("Initialized: {}", this);
        LOG.trace("  View: {}", getView());
        LOG.trace("  splash screen tab id: {}", splashScreenTabId);
        LOG.trace("  logging tab id: {}", loggingTabId);
    }


    @PreDestroy
    public void close() {
        RemoveMainTabEvent tabEvent = new RemoveMainTabEvent(splashScreenTabId);
        eventBus.publish(EventScope.SESSION, this, tabEvent);
        
        LOG.trace("Destroyed: {}", this);
    }


    @SuppressWarnings("UnusedDeclaration") // called via BUS
    @EventBusListenerMethod(scope= EventScope.SESSION, filter=StartupFilter.class)
    public void onStartup(org.vaadin.spring.events.Event<Action> event) {
        LOG.debug("{} received: {}", this, event);

        getView().addTab(splashScreenTabId, "Info", about);
        getView().addTab(loggingTabId, "Events", eventLogger.getView());
    }


    @SuppressWarnings("UnusedDeclaration") // called via BUS
    @EventBusListenerMethod(scope = EventScope.SESSION)
    public void addTab(Event<AddMainTabEvent> event) {
        LOG.debug("{} received: {}", this, event);

        AddMainTabEvent tab = event.getPayload();

        getView().addTab(tab.getTabId(), tab.getTitle(), tab.getComponent());
    }

    @SuppressWarnings("UnusedDeclaration") // called via BUS
    @EventBusListenerMethod(scope = EventScope.SESSION)
    public void removeTab(Event<RemoveMainTabEvent> event) {
        LOG.debug("{} received: {}", this, event);

        RemoveMainTabEvent tab = event.getPayload();

        getView().removeTab(tab.getTabId());

    }
}
