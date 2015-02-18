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

package de.kaiserpfalzEdv.office.ui.web.widgets.menu;

import com.vaadin.ui.Label;
import de.kaiserpfalzEdv.office.ui.web.widgets.about.AboutPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.spring.annotation.VaadinSessionScope;
import org.vaadin.spring.events.Event;
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
 * @since 17.02.15 20:57
 */
@VaadinSessionScope
@VaadinPresenter(viewName = NavigationView.NAME)
public class NavigationPresenter extends Presenter<NavigationView> {
    private static final Logger LOG = LoggerFactory.getLogger(NavigationPresenter.class);

    @Inject
    private AboutPanel about;

    private UUID adminId = UUID.randomUUID();
    private UUID aboutId = UUID.randomUUID();
    
    
    public NavigationPresenter() {
        LOG.trace("Created: {}", this);
    }

    @PostConstruct
    public void init() {
        super.init();

        getView().addEntry(adminId, "Admin", new Label("Just for fun"));
        getView().addEntry(aboutId, "About", about);
        
        LOG.trace("Initialized: {}", this);
    }


    @PreDestroy
    public void close() {
        LOG.trace("Destroyed: {}", this);
    }


    @SuppressWarnings("UnusedDeclaration") // called via BUS
    @EventBusListenerMethod(scope = EventScope.SESSION)
    public void addMenu(Event<AddMenuEvent> event) {
        LOG.debug("{} received: {}", this, event);

        AddMenuEvent menu = event.getPayload();

        getView().addEntry(menu.getMenuId(), menu.getTitle(), menu.getMenu());
    }

    @SuppressWarnings("UnusedDeclaration") // called via BUS
    @EventBusListenerMethod(scope = EventScope.SESSION)
    public void removeMenu(Event<RemoveMenuEvent> event) {
        LOG.debug("{} received: {}", this, event);

        RemoveMenuEvent menu = event.getPayload();

        getView().removeEntry(menu.getMenuId());

    }

    @SuppressWarnings("UnusedDeclaration") // called via BUS
    @EventBusListenerMethod(scope = EventScope.SESSION)
    public void replaceMenu(Event<ReplaceMenuEvent> event) {
        LOG.debug("{} received: {}", this, event);

        ReplaceMenuEvent menu = event.getPayload();

        getView().replaceEntry(menu.getMenuId(), menu.getMenu());

    }
}
