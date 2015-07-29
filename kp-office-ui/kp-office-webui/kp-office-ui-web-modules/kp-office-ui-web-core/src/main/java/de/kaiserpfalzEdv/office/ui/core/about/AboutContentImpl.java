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

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;
import de.kaiserpfalzEdv.commons.jee.servlet.model.ApplicationMetaData;
import de.kaiserpfalzEdv.office.core.licence.OfficeLicence;
import de.kaiserpfalzEdv.office.ui.api.Presenter;
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
@SpringView(name = AboutBoxImpl.VIEW_NAME)
public class AboutContentImpl extends VerticalLayout implements AboutContent, View, Component {
    private static final Logger LOG = LoggerFactory.getLogger(AboutContentImpl.class);

    private OfficeLicence       license;
    private ApplicationMetaData application;
    private Locale userLocale;

    @Inject
    private AboutContentPresenter presenter;


    public AboutContentImpl() {
        LOG.trace("Created: {}", this);
    }

    @PostConstruct
    public void init() {
        presenter.setView(this);
        LOG.trace("***** Initialized: {}", this);
    }

    @PreDestroy
    public void close() {
        removeAllComponents();

        LOG.trace("Destroyed: {}", this);
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
    public Locale getUserLocale() {
        return userLocale;
    }

    @Override
    public void setUserLocale(Locale userLocale) {
        this.userLocale = userLocale;
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        LOG.trace("{} received: {}", this, event);

        addComponent(constructVersionString());
        addComponent(constructLicenseInformation());
    }

    private Layout constructVersionString() {
        HorizontalLayout result = new HorizontalLayout();

        result.addComponent(new Label(application.get(ApplicationMetaData.APPLICATION_NAME)));
        result.addComponent(new Label("(v. " + application.get(ApplicationMetaData.APPLICATION_VERSION) + ")"));

        return result;
    }

    private Layout constructLicenseInformation() {
        HorizontalLayout result = new HorizontalLayout();

        result.addComponent(new Label("Lizenz-Nr.: " + license.getId().toString()));
        result.addComponent(
                new Label(
                        "gültig bis: " + license.getExpiry()
                                                .format(
                                                        DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
                                                                         .withLocale(userLocale)
                                                )
                )
        );

        return result;
    }

    @Override
    public void setPresenter(Presenter<?> presenter) {
        this.presenter = (AboutContentPresenter) presenter;
    }
}
