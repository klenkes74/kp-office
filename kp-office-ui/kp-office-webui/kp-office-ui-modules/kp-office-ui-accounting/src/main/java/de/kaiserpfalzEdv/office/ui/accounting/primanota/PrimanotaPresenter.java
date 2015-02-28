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

package de.kaiserpfalzEdv.office.ui.accounting.primanota;

import com.vaadin.ui.Table;
import de.kaiserpfalzEdv.office.ui.content.ContentTabBuilder;
import de.kaiserpfalzEdv.office.ui.web.widgets.content.events.AddMainTabEvent;
import de.kaiserpfalzEdv.office.ui.web.widgets.content.events.RemoveMainTabEvent;
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
 * @since 18.02.15 16:12
 */
@VaadinSessionScope
@VaadinPresenter(viewName = PrimanotaView.NAME)
public class PrimanotaPresenter extends Presenter<PrimanotaView> {
    private static final Logger LOG = LoggerFactory.getLogger(PrimanotaPresenter.class);

    @Inject
    private PrimanotaTableBuilder dataLoader;

    private UUID journalTabId = UUID.randomUUID();

    private UUID journalId = UUID.fromString("400b4f5d-216e-4457-9dce-79859d8396af");
    private UUID mappingId = UUID.fromString("6c4a5120-088a-4321-a8e1-5d0c22a4b436");

    private boolean open = false;


    public PrimanotaPresenter() {
        LOG.trace("Created: {}", this);
    }

    @PostConstruct
    public void init() {
        super.init();


        LOG.trace("Initialized: {}", this);
        LOG.trace("  data loader: {}", this.dataLoader);
    }

    @PreDestroy
    public void close() {
        LOG.trace("Destroyed: {}", this);
    }


    @EventBusListenerMethod
    public void openJournal(Event<OpenPrimanotaEvent> event) {
        LOG.trace("{} received: {}", event);

        if (open) {
            LOG.info("Closing primaNota tab!");
            RemoveMainTabEvent closeTab = new ContentTabBuilder()
                    .withId(journalTabId)
                    .removeMainTabEvent();
            getEventBus().publish(EventScope.SESSION, this, closeTab);
        } else {
            LOG.info("Opening primaNota tab!");

            Table table = dataLoader.withJournalId(journalId).withMappingId(mappingId).build();

            getView().replaceJournalTable(table);

            AddMainTabEvent openTab = new ContentTabBuilder()
                    .withId(journalTabId)
                    .withTitle("Primanota")
                    .withComponent(getView())
                    .addMainTabEvent();

            getEventBus().publish(EventScope.SESSION, this, openTab);
        }

        open = !open; // toggle flag ...
    }

}
