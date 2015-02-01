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

import de.kaiserpfalzEdv.office.ui.web.StartupFilter;
import de.kaiserpfalzEdv.office.ui.web.view.BodyView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.spring.events.Event;
import org.vaadin.spring.events.EventBusListenerMethod;
import org.vaadin.spring.events.EventScope;
import org.vaadin.spring.navigator.Presenter;
import org.vaadin.spring.navigator.VaadinPresenter;

import javax.inject.Inject;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
@VaadinPresenter(viewName = BodyView.NAME)
public class BodyPresenter extends Presenter<BodyView> {
    private static final Logger LOG = LoggerFactory.getLogger(BodyPresenter.class);

    @Inject
    NavigationPanelPresenter nav;

    @Inject
    TabPanelPresenter tab;

    @EventBusListenerMethod(scope= EventScope.SESSION, filter=StartupFilter.class)
    public void onStartup(Event<Action> event) {
        LOG.trace("Startup: {}", event);
        
        getView().setNavigationPanel(nav.getView());
        getView().setTabbedPanel(tab.getView());
    }
}