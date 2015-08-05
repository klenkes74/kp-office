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
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;
import de.kaiserpfalzEdv.commons.jee.eventbus.EventBusHandler;
import de.kaiserpfalzEdv.commons.jee.servlet.model.ApplicationMetaData;
import de.kaiserpfalzEdv.office.core.licence.OfficeLicence;
import de.kaiserpfalzEdv.office.ui.api.mvp.Presenter;
import de.kaiserpfalzEdv.office.ui.core.i18n.LocaleChangeEvent;
import de.kaiserpfalzEdv.office.ui.core.i18n.MessageProvider;
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

    private OfficeLicence       license;
    private ApplicationMetaData application;

    @Inject
    private AboutContentPresenter presenter;

    @Inject
    private MessageProvider i18n;

    @Inject
    private EventBusHandler bus;

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
    public OfficeLicence getLicense() {
        return license;
    }

    @Override
    public void setLicense(OfficeLicence license) {
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

    private Layout constructVersionString() {
        HorizontalLayout result = new HorizontalLayout();
        result.setSpacing(true);

        Label applicationName = new Label(i18n.resolveCode("office.core.application").format(null));
        result.addComponent(applicationName);

        Label applicationVersion = new Label("(v. " + application.get(ApplicationMetaData.APPLICATION_VERSION) + ")");
        result.addComponent(applicationVersion);

        return result;
    }

    private Layout constructLicenseInformation() {
        HorizontalLayout result = new HorizontalLayout();
        result.setSpacing(true);

        Label licenseNumber = new Label(
                i18n.resolveCode("office.licence.id").format(null) + license.getId()
                                                                            .toString()
        );
        result.addComponent(licenseNumber);

        Label validTill = new Label(
                i18n.resolveCode("office.licence.valid_till").format(null) + ": " + license.getExpiry()
                                                                                           .format(
                                                                                                   DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
                                                                                                                    .withLocale(locale)
                                                                                           )
        );
        result.addComponent(validTill);

        return result;
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
