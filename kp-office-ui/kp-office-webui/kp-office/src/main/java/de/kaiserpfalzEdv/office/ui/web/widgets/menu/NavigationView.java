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
import de.kaiserpfalzEdv.office.ui.menu.Menu;
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
    static final         String NAME = "MAIN.NAVIGATION";
    private static final Logger LOG  = LoggerFactory.getLogger(NavigationView.class);

    final HashMap<UUID, Menu> entries = new HashMap<>(10);


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


    Menu getMenu(final UUID id) {
        return entries.get(id);
    }

    void addEntry(final Menu menu) {
        if (entries.containsKey(menu.getId())) {
            replaceEntry(menu);
            return;
        }

        entries.put(menu.getId(), menu);

        int index = Integer.MAX_VALUE;
        for (Menu e : entries.values()) {
            if (menu.getSortOrder() < e.getSortOrder()) {
                int newIndex = getTabPosition(getTab(e.getComponent()));
                index = newIndex < index ? newIndex : index;
            }
        }

        if (index != Integer.MAX_VALUE) {
            addTab(menu.getComponent(), menu.getTitle(), null, index);
        } else {
            addTab(menu.getComponent(), menu.getTitle());
        }

        LOG.debug("Added menu: {}", menu);
    }

    void replaceEntry(final Menu menu) {
        if (!entries.containsKey(menu.getId())) {
            addEntry(menu);
            return;
        }

        replaceComponent(entries.get(menu.getId()).getComponent(), menu.getComponent());
        entries.remove(menu.getId());
        entries.put(menu.getId(), menu);

        LOG.debug("Replaced menu {} with: {}", menu.getId(), menu);
    }

    void removeEntry(final UUID id) {
        if (!entries.containsKey(id)) {
            LOG.warn("Menu does not contain sub menu with id: {}", id);
            return;
        }

        removeComponent(entries.get(id).getComponent());
        entries.remove(id);

        LOG.debug("Removed menu {}", id);
    }
}
