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

package de.kaiserpfalzEdv.office.ui.web;

import com.google.common.eventbus.Subscribe;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Viewport;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import de.kaiserpfalzEdv.commons.jee.eventbus.EventBusHandler;
import de.kaiserpfalzEdv.office.ui.core.i18n.LocaleChangeEvent;
import de.kaiserpfalzEdv.office.ui.web.api.menu.MenuEntry;
import de.kaiserpfalzEdv.office.ui.web.authentication.AccessControl;
import de.kaiserpfalzEdv.office.ui.web.authentication.BasicAccessControl;
import de.kaiserpfalzEdv.office.ui.web.authentication.LoginScreen;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import java.util.List;

/**
 * The main UI of the application
 */
@SpringUI
@Push
@Viewport("user-scalable=no,initial-scale=1.0")
@Theme("mytheme")
@Widgetset("KPOfficeWidgetset")
@Configuration
public class KPOfficeUI extends UI {
    private static final long   serialVersionUID = -6086448022988650873L;
    private static final Logger LOG              = LoggerFactory.getLogger(KPOfficeUI.class);


    @Inject // DatabaseAccessControl will use database to create the user.
    private BasicAccessControl accessControl;

    @Inject
    private SpringViewProvider viewProvider;

    @Inject
    private List<MenuEntry> menuEntries;


    @Inject
    private EventBusHandler eventBus;

    /**
     * root window. Since the main content may not be changed this is the main content. Then everything may be changed
     * within this layout.
     */
    private CssLayout mainLayout;


    public KPOfficeUI() {
        LOG.trace("***** Created: {}", this);
    }

    public static KPOfficeUI get() {
        return (KPOfficeUI) UI.getCurrent();
    }

    @PostConstruct
    public void init() {
        eventBus.register(this);

        LOG.trace("***** Initialized: {}", this);
    }

    @PreDestroy
    public void close() {
        eventBus.unregister(this);

        LOG.trace("***** Destroyed: {}", this);
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Responsive.makeResponsive(this);

        eventBus.post(new LocaleChangeEvent(this, vaadinRequest.getLocale()));

        getPage().setTitle("KP Office");
        if (!accessControl.isUserSignedIn()) {
            setContent(
                    new LoginScreen(
                            accessControl, new LoginScreen.LoginListener() {
                        @Override
                        public void loginSuccessful() {
                            showMainView();
                        }
                    }
                    )
            );
        } else {
            showMainView();
        }
    }

    protected void showMainView() {
        HorizontalLayout screen = new HorizontalLayout();

        Menu menu = new Menu();
        menu.addEntries(menuEntries);


        CssLayout viewContainer = new CssLayout();
        viewContainer.addStyleName("valo-content");
        viewContainer.setSizeFull();


        screen.setStyleName("main-screen");
        screen.addComponent(menu);
        screen.addComponent(viewContainer);
        screen.setExpandRatio(viewContainer, 1);
        screen.setSizeFull();


        Navigator navigator = new Navigator(this, viewContainer);
        navigator.addProvider(viewProvider);
        navigator.setErrorView(new ErrorView());

        addStyleName(ValoTheme.UI_WITH_MENU);
        setContent(screen);

        if (StringUtils.isNotBlank(getNavigator().getState())) {
            getNavigator().navigateTo(getNavigator().getState());
        }
    }

    public AccessControl getAccessControl() {
        return accessControl;
    }


    @Subscribe
    public void setLocale(final LocaleChangeEvent event) {
        LOG.trace("Changing locale: {} -> {}", getLocale(), event.getLocale());

        setLocale(event.getLocale());
    }
}