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

package de.kaiserpfalzEdv.office.ui.web.mainScreen;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import de.kaiserpfalzEdv.commons.service.Versionable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.spring.annotation.VaadinSessionScope;
import org.vaadin.spring.navigator.annotation.VaadinView;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 17.02.15 21:02
 */
@VaadinSessionScope
@VaadinView(name = NavBarLogoView.NAME)
public class NavBarLogoView extends VerticalLayout implements View {
    private static final Logger LOG = LoggerFactory.getLogger(NavBarLogoView.class);
    static final String NAME = "MAIN.LOGO";
    
    public NavBarLogoView() {
        LOG.trace("Created: {}", this);
    }
    
    @PostConstruct
    public void init() {
        setHeight(2f, Unit.CM);
        setWidthUndefined();
        
        LOG.trace("Initialized: {}", this);
    }
    
    @PreDestroy
    public void close() {
        removeAllComponents();
        LOG.trace("Destroyed: {}", this);
    }
    
    
    void setApplicationName(final String name, final Versionable version) {
        LOG.debug("Setting application name to: {} (v. {})", name, version.getBuildDescriptor());
        
        removeAllComponents();
        addComponent(new Label(name));
        addComponent(new Label(version.getVersionString()));
    }
    

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        LOG.trace("{} received: {}", this, event);
    }
}
