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

package de.kaiserpfalzEdv.office.ui.core.about;

import com.google.common.eventbus.Subscribe;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import de.kaiserpfalzEdv.commons.jee.eventbus.EventBusHandler;
import de.kaiserpfalzEdv.commons.jee.servlet.model.ApplicationMetaData;
import de.kaiserpfalzEdv.office.clients.core.about.AboutContent;
import de.kaiserpfalzEdv.office.clients.core.about.AboutContentPresenter;
import de.kaiserpfalzEdv.office.commons.client.mvp.Presenter;
import de.kaiserpfalzEdv.office.core.license.OfficeLicense;
import de.kaiserpfalzEdv.office.ui.core.i18n.LocaleChangeEvent;
import de.kaiserpfalzEdv.office.ui.core.i18n.LocalizedStringProvider;
import de.kaiserpfalzEdv.office.ui.web.api.menu.MenuEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 18.02.15 08:23
 */
@UIScope
@SpringView(name = AboutContentImpl.VIEW_NAME)
public class AboutContentImpl extends VerticalLayout implements AboutContent, View, Component, MenuEntry {
    public static final  String VIEW_NAME = "About";
    private static final Logger LOG       = LoggerFactory.getLogger(AboutContentImpl.class);
    @Inject
    LocalizedStringProvider i18n;
    private OfficeLicense license;
    private ApplicationMetaData   application;
    @Inject
    private AboutContentPresenter presenter;
    @Inject
    private EventBusHandler       bus;

    private Locale locale;


    public AboutContentImpl() {
        LOG.trace("***** Created: {}", this);
    }

    @PostConstruct
    public void init() {
        presenter.setView(this);

        setCaption(VIEW_NAME);
        setIcon(FontAwesome.INFO);

        LOG.trace("*   *   presenter: {}", this.presenter);
        LOG.trace("*   *   i18n: {}", this.i18n);

        bus.register(this);
        LOG.trace("*   *   event bus: {}", this.bus);

        this.locale = Locale.getDefault();
        LOG.trace("*   *   locale: {}", this.locale);

        LOG.debug("***** Initialized: {}", this);
    }

    @PreDestroy
    public void close() {
        removeAllComponents();
        bus.unregister(this);

        LOG.trace("***** Destroyed: {}", this);
    }


    @Override
    public OfficeLicense getLicense() {
        return license;
    }

    @Override
    public void setLicense(OfficeLicense license) {
        this.license = license;
    }

    @Override
    public ApplicationMetaData getApplication() {
        return application;
    }

    @Override
    public void setApplication(ApplicationMetaData application) {
        this.application = application;
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        LOG.trace("{} received: {}", this, event);

        if (getComponentCount() == 0) {
            addComponent(constructVersionString());
            addComponent(constructLicenseInformation());
        }
    }

    private Component constructVersionString() {
        return new Label(
                i18n.getLocalized("office.core.application").getValue()
                        + " (v. " + application.get(ApplicationMetaData.APPLICATION_VERSION) + ")"
        );
    }

    private Component constructLicenseInformation() {
        return new Label(
                i18n.getLocalized("office.license.id").getValue() + ": " + license.getId().toString()
                        + i18n.getLocalized("office.license.valid_till").getValue() + ": "
                        + license.getExpiry().format(
                        DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
                                         .withLocale(locale)
                )
        );
    }

    @Override
    public void setPresenter(Presenter<?> presenter) {
        this.presenter = (AboutContentPresenter) presenter;
    }

    @Override
    public String getViewName() {
        return VIEW_NAME;
    }

    @Override
    public int getSortOrder() {
        return MenuEntry.LOWEST;
    }


    @Subscribe
    public void setLocale(final LocaleChangeEvent locale) {
        LOG.trace("Change locale: {} -> {}", this.locale, locale.getLocale());

        this.locale = locale.getLocale();
    }
}
