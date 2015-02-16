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

package de.kaiserpfalzEdv.office.ui.web.presenter;

import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.VerticalLayout;
import de.kaiserpfalzEdv.office.ui.web.StartupFilter;
import de.kaiserpfalzEdv.office.ui.web.view.MainView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.spring.events.Event;
import org.vaadin.spring.events.EventBusListenerMethod;
import org.vaadin.spring.events.EventScope;
import org.vaadin.spring.navigator.Presenter;
import org.vaadin.spring.navigator.VaadinPresenter;

import javax.annotation.PreDestroy;
import javax.inject.Inject;


@VaadinPresenter(viewName = MainView.NAME)
public class MainPresenter extends Presenter<MainView> {
    private static final Logger LOG = LoggerFactory.getLogger(MainPresenter.class);

    @Inject 
    private NavigationPanelPresenter menu;
    
    @Inject 
    private TabPanelPresenter tabs;
    
    @Inject
    private BodyPresenter body;
    
    
    public MainPresenter() {
        super();
        
        LOG.trace("Created: {}", this);
    }
    
    @PreDestroy
    public void close() {
        LOG.trace("Destroyed: {}", this);
    }
    

    @EventBusListenerMethod(scope= EventScope.SESSION, filter=StartupFilter.class)
    public void onStartup(Event<Action> event) {
        LOG.trace("Initializing main view (received event: {}) ...", event.getPayload());

        getView().removeAllComponents();
        getView().setNavigation(menu.getView());
        getView().setBody(body.getView());
        getView().setTabs(tabs.getView());

        
        getView().setTitle(generateTitle());
        getView().setCopyright(generateCopyrightMessage());
    }

    private Component generateTitle() {
        VerticalLayout titleMessage = new VerticalLayout();
        titleMessage.addComponent(new Label("KP Office"));
        
        return titleMessage;
    }
    
    private Component generateCopyrightMessage() {
        HorizontalLayout copyrightMessage = new HorizontalLayout();
        copyrightMessage.addComponent(new Label("&copy; 2015 "));
        copyrightMessage.addComponent(new Link("Kaiserpfalz EDV-Service", new ExternalResource("http://www.kaiserpfalz-edv.de/")));

        return copyrightMessage;
    }
}