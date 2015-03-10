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

package de.kaiserpfalzEdv.office.ui.core;

import de.kaiserpfalzEdv.commons.service.Versionable;
import de.kaiserpfalzEdv.office.commons.ModuleInformation;
import de.kaiserpfalzEdv.office.ui.OfficeModule;
import de.kaiserpfalzEdv.office.ui.core.about.AboutPanel;
import de.kaiserpfalzEdv.office.ui.menu.MenuBuilder;
import de.kaiserpfalzEdv.office.ui.web.widgets.menu.events.AddMenuEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.spring.events.EventScope;
import org.vaadin.spring.navigator.Presenter;
import org.vaadin.spring.navigator.annotation.VaadinPresenter;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Named;
import java.util.UUID;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 19.02.15 17:52
 */
@Named
@ModuleInformation(
        name = CoreModuleImpl.LONG_NAME,
        id = CoreModuleImpl.ID,
        needsLicence = true,
        featureName = CoreModuleImpl.SHORT_NAME
)
@VaadinPresenter(viewName = AboutPanel.NAME)
public class CoreModuleImpl extends Presenter<AboutPanel> implements OfficeModule {
    static final         String      ID                    = "84e03cdc-b833-11e4-a71e-12e3f512a338";
    static final         String      CANONICAL_NAME        = "KPO::core";
    static final         String      LONG_NAME             = "KP Office";
    static final         String      SHORT_NAME            = "core";
    static final         Versionable VERSION               = new Versionable.Builder()
            .withMajor(0).withMinor(2).withPatchlevel(0).withReleaseState(Versionable.ReleaseState.alpha)
            .build();
    private static final Logger      LOG                   = LoggerFactory.getLogger(CoreModuleImpl.class);
    private static final UUID        ABOUT_MENU_ID         = UUID.fromString(ID);
    private static final String      ABOUT_MENU_TITLE      = "About";
    private static final int         ABOUT_MENU_SORT_ORDER = 100;


    public CoreModuleImpl() {
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

        AddMenuEvent menuEvent = new MenuBuilder()
                .withId(ABOUT_MENU_ID)
                .withTitle(ABOUT_MENU_TITLE)
                .withSortOrder(ABOUT_MENU_SORT_ORDER)
                .withComponent(getView())
                .addMenuEvent();

        getEventBus().publish(EventScope.APPLICATION, this, menuEvent);
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
