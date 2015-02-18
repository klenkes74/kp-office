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

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.spring.annotation.VaadinSessionScope;
import org.vaadin.spring.navigator.annotation.VaadinView;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 17.02.15 21:02
 */
@VaadinSessionScope
@VaadinView(name = NavigationView.NAME)
public class NavigationView extends Accordion implements View {
    static final         String                   NAME    = "MAIN.NAVIGATION";
    private static final Logger                   LOG     = LoggerFactory.getLogger(NavigationView.class);
    final                HashMap<UUID, Component> entries = new HashMap<>(10);


    public NavigationView() {
        LOG.trace("Created: {}", this);
    }

    @PostConstruct
    public void init() {
        setSizeFull();
        setWidth(100f, Unit.PERCENTAGE);
        setHeight(100f, Unit.PERCENTAGE);

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


    void addEntry(final UUID id, final String title, final Component menu) {
        if (entries.containsKey(id)) {
            replaceEntry(id, menu);
            return;
        }

        entries.put(id, menu);
        addTab(menu, title);

        LOG.debug("Added menu {} ({}): {}", title, id, menu);
    }

    void replaceEntry(final UUID id, final Component menu) {
        if (!entries.containsKey(id)) {
            LOG.warn("Menu does not contain sub menu with id: {}", id);
            return;
        }

        replaceComponent(entries.get(id), menu);
        entries.remove(id);
        entries.put(id, menu);

        LOG.debug("Replaces menu {} with: {}", id, menu);
    }

    void removeEntry(final UUID id) {
        if (!entries.containsKey(id)) {
            LOG.warn("Menu doesn ot contain sub menu with id: {}", id);
            return;
        }

        removeComponent(entries.get(id));
        entries.remove(id);

        LOG.debug("Removed menu {}", id);
    }
}
