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

package de.kaiserpfalzEdv.office.ui.accounting;

import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button;
import de.kaiserpfalzEdv.commons.service.Versionable;
import de.kaiserpfalzEdv.office.core.license.ModuleInformation;
import de.kaiserpfalzEdv.office.ui.OfficeModule;
import de.kaiserpfalzEdv.office.ui.accounting.journal.JournalPresenter;
import de.kaiserpfalzEdv.office.ui.accounting.journal.OpenJournalEvent;
import de.kaiserpfalzEdv.office.ui.menu.MenuBuilder;
import de.kaiserpfalzEdv.office.ui.web.widgets.menu.events.AddMenuEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.spring.events.EventScope;
import org.vaadin.spring.navigator.Presenter;
import org.vaadin.spring.navigator.annotation.VaadinPresenter;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.UUID;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 18.02.15 22:11
 */
@Named
@ModuleInformation(
        name = AccountingModuleImpl.LONG_NAME,
        id = AccountingModuleImpl.ID,
        needsLicence = true,
        featureName = AccountingModuleImpl.SHORT_NAME
)
@VaadinPresenter(viewName = AccountingMenu.NAME)
public class AccountingModuleImpl extends Presenter<AccountingMenu> implements OfficeModule, Button.ClickListener {
    static final         String      ID                         = "f2615e34-bc94-43e4-afac-05b657b5820b";
    static final         String      CANONICAL_NAME             = "KPO::accounting";
    static final         String      LONG_NAME                  = "Financial Accounting";
    static final         String      SHORT_NAME                 = "accounting";
    static final         Versionable VERSION                    = new Versionable.Builder()
            .withMajor(0).withMinor(2).withPatchlevel(0).withReleaseState(Versionable.ReleaseState.alpha)
            .build();
    private static final Logger      LOG                        = LoggerFactory.getLogger(AccountingModuleImpl.class);
    private static final UUID        ACCOUNTING_MENU_ID         = UUID.fromString(ID);
    private static final String      ACCOUNTING_MENU_TITLE      = "Financial Accounting";
    private static final int         ACCOUNTING_MENU_SORT_ORDER = 20;


    @Inject
    private JournalPresenter journalPresenter;


    public AccountingModuleImpl() {
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


    @Override
    public void startModule() {
        LOG.info("Adding module: {}", this);

        Button openJournal = new Button("Journal", this);
        openJournal.setWidth(100f, Sizeable.Unit.PERCENTAGE);
        openJournal.setId("journal");
        openJournal.setDescription("Opens/Closes a journal");

        getView().addButton(openJournal);

        AddMenuEvent menuEvent = new MenuBuilder()
                .withId(ACCOUNTING_MENU_ID)
                .withTitle(ACCOUNTING_MENU_TITLE)
                .withSortOrder(ACCOUNTING_MENU_SORT_ORDER)
                .withComponent(getView())
                .addMenuEvent();

        getEventBus().publish(EventScope.APPLICATION, this, menuEvent);
    }


    @Override
    public void buttonClick(Button.ClickEvent event) {
        LOG.debug("Clicked on: {}", event.getButton().getId());

        switch (event.getButton().getId()) {
            case "journal":
                getEventBus().publish(EventScope.SESSION, this, new OpenJournalEvent());
                break;
        }
    }


    @Override
    public Versionable getVersion() {
        return VERSION;
    }

    @Override
    public String getShortName() {
        return SHORT_NAME;
    }

    @Override
    public String getCanonicalName() {
        return CANONICAL_NAME;
    }

    @Override
    public String getDisplayName() {
        return LONG_NAME;
    }
}