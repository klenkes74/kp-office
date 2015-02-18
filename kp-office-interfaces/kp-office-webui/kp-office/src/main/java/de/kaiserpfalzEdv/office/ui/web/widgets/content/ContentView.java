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

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;
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
 * @since 17.02.15 20:34
 */
@VaadinSessionScope
@VaadinView(name = ContentView.NAME)
public class ContentView extends TabSheet implements View {
    static final String NAME = "MAIN.CONTENT";
    private static final long   serialVersionUID = 1722734766184991100L;
    private static final Logger LOG              = LoggerFactory.getLogger(ContentView.class);
    /**
     * Contains the currently displayed components.
     */
    private final HashMap<UUID, Component> tabs = new HashMap<>(10);


    public ContentView() {
        LOG.trace("Created: {}", this);
    }

    @PostConstruct
    public void init() {
        LOG.trace("Initialized: {}", this);
    }

    @PreDestroy
    public void close() {
        LOG.trace("Destroyed: {}", this);
    }


    public void addTab(UUID id, final String title, final Component component) {
        if (tabs.containsKey(id)) {
            replaceComponent(tabs.get(id), component);
            
            tabs.remove(id);
            tabs.put(id, component);
        } else {
            addTab(component, title);

            tabs.put(id, component);
        }
    }
    
    public void removeTab(UUID id) {
        if (tabs.containsKey(id)) {
            removeComponent(tabs.get(id));
            tabs.remove(id);
            
            LOG.debug("Removed content tab: {}", id);
        } else {
            LOG.warn("Content tab '{}' could not be removed: no such tab existed.", id);
        }
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        LOG.debug("{} received: {}", this, event);
    }
}
