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

import de.kaiserpfalzEdv.office.ui.web.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.spring.annotation.VaadinSessionScope;
import org.vaadin.spring.events.EventScope;
import org.vaadin.spring.events.annotation.EventBusListenerMethod;
import org.vaadin.spring.navigator.Presenter;
import org.vaadin.spring.navigator.annotation.VaadinPresenter;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 17.02.15 20:38
 */
@VaadinSessionScope
@VaadinPresenter(viewName = NavigationBarView.NAME)
public class NavigationBarPresenter extends Presenter<NavigationBarView> {
    private static final Logger LOG = LoggerFactory.getLogger(NavigationBarPresenter.class);

    
    @Inject
    private NavBarLogoPresenter logo;
    
    @Inject
    private NavigationPresenter navigation;
    
    @Inject
    private NavBarFooterPresenter footer;
    
    
    
    public NavigationBarPresenter() {
        LOG.trace("Created: {}", this);
    }
    
    
    @PostConstruct
    public void init() {
        LOG.trace("Initialized: {}", this);
        LOG.trace("  logo: {}", logo);
        LOG.trace("  navigation: {}", navigation);
        LOG.trace("  footer: {}", footer);
    }
    
    
    @PreDestroy
    public void close() {
        LOG.trace("Destroyed: {}", this);
    }
    
    
    @SuppressWarnings("UnusedDeclaration") // called via BUS
    @EventBusListenerMethod(scope= EventScope.SESSION, filter=StartupFilter.class)
    public void onStartup(org.vaadin.spring.events.Event<Action> event) {
        LOG.debug("{} received: {}", this, event);

        getView().addComponent(logo.getView(), 0, 0);
        getView().addComponent(navigation.getView(), 1, 0);
        getView().addComponent(footer.getView(), 2, 0);
    }

}
