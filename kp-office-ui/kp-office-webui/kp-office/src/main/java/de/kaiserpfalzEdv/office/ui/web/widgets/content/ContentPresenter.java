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

import com.google.common.eventbus.Subscribe;
import de.kaiserpfalzEdv.office.ui.content.ContentTab;
import de.kaiserpfalzEdv.office.ui.presenter.Presenter;
import de.kaiserpfalzEdv.office.ui.web.widgets.content.events.AddMainTabEvent;
import de.kaiserpfalzEdv.office.ui.web.widgets.content.events.RemoveMainTabEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.UUID;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 17.02.15 20:38
 */
@Named
public class ContentPresenter extends Presenter<ContentView> {
    private static final Logger LOG = LoggerFactory.getLogger(ContentPresenter.class);

    @Inject
    private ContentView view;


    private UUID splashScreenTabId = UUID.randomUUID();
    private UUID loggingTabId = UUID.randomUUID();

    public ContentPresenter() {
        LOG.trace("Created: {}", this);
    }

    @PostConstruct
    public void init() {
        super.setView(view);
        super.init();

        getEventBus().register(this);

        LOG.trace("Initialized: {}", this);
        LOG.trace("  View: {}", getView());
        LOG.trace("  splash screen tab id: {}", splashScreenTabId);
        LOG.trace("  logging tab id: {}", loggingTabId);
    }


    @PreDestroy
    public void close() {
        RemoveMainTabEvent tabEvent = new RemoveMainTabEvent(this, splashScreenTabId);
        getEventBus().post(tabEvent);

        getView().close();
        LOG.trace("Destroyed: {}", this);
    }


    @SuppressWarnings("UnusedDeclaration") // called via BUS
    @Subscribe
    public void addTab(AddMainTabEvent event) {
        LOG.debug("{} received: {}", this, event);

        ContentTab tab = event.getTab();

        getView().addTab(tab);
    }

    @SuppressWarnings("UnusedDeclaration") // called via BUS
    @Subscribe
    public void removeTab(RemoveMainTabEvent event) {
        LOG.debug("{} received: {}", this, event);

        getView().removeTab(event.getTabId());

    }
}
