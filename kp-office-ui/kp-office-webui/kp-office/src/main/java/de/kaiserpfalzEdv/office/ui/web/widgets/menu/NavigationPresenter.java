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

import com.vaadin.ui.Component;
import de.kaiserpfalzEdv.office.ui.menu.Menu;
import de.kaiserpfalzEdv.office.ui.web.widgets.menu.events.AddMenuEvent;
import de.kaiserpfalzEdv.office.ui.web.widgets.menu.events.RemoveMenuEvent;
import de.kaiserpfalzEdv.office.ui.web.widgets.menu.events.ReplaceMenuEvent;
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
import java.util.UUID;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 17.02.15 20:57
 */
@VaadinSessionScope
@VaadinPresenter(viewName = NavigationView.NAME)
public class NavigationPresenter extends Presenter<NavigationView> implements Component.Listener {
    private static final Logger LOG = LoggerFactory.getLogger(NavigationPresenter.class);


    public NavigationPresenter() {
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


    @SuppressWarnings("UnusedDeclaration") // called via BUS
    @EventBusListenerMethod(scope = EventScope.APPLICATION)
    public void addMenu(Event<AddMenuEvent> event) {
        LOG.debug("{} received: {}", this, event);

        Menu menu = event.getPayload().getMenu();
        menu.getComponent().addListener(this);

        getView().addEntry(menu);
    }

    @SuppressWarnings("UnusedDeclaration") // called via BUS
    @EventBusListenerMethod(scope = EventScope.APPLICATION)
    public void removeMenu(Event<RemoveMenuEvent> event) {
        LOG.debug("{} received: {}", this, event);

        UUID menuId = event.getPayload().getMenuId();

        Component removed = getView().getMenu(menuId).getComponent();
        removed.removeListener(this);

        getView().removeEntry(menuId);

    }

    @SuppressWarnings("UnusedDeclaration") // called via BUS
    @EventBusListenerMethod(scope = EventScope.APPLICATION)
    public void replaceMenu(Event<ReplaceMenuEvent> event) {
        LOG.debug("{} received: {}", this, event);

        Menu menu = event.getPayload().getMenu();
        menu.getComponent().addListener(this);

        getView().replaceEntry(menu);

    }

    @Override
    public void componentEvent(Component.Event event) {
        LOG.debug("{} received for {}: {}", this, event.getComponent(), event);

        getEventBus().publish(event.getComponent(), event);
    }
}
