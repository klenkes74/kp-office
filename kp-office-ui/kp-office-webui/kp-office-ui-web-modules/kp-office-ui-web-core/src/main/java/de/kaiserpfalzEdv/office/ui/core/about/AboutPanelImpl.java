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
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.VaadinSessionScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import de.kaiserpfalzEdv.commons.jee.servlet.model.ApplicationMetaData;
import de.kaiserpfalzEdv.office.core.licence.OfficeLicence;
import de.kaiserpfalzEdv.office.ui.api.mvp.Presenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import static de.kaiserpfalzEdv.office.ui.core.about.AboutPanelImpl.VIEW_NAME;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 18.02.15 08:23
 */
@VaadinSessionScope
@SpringView(name = VIEW_NAME)
public class AboutPanelImpl extends VerticalLayout implements AboutPanel, View, Component {
    public static final String VIEW_NAME = "About Panel";
    private static final Logger LOG = LoggerFactory.getLogger(AboutPanelImpl.class);
    private OfficeLicence       license;
    private ApplicationMetaData application;

    @Inject
    private AboutContentPresenter presenter;


    public AboutPanelImpl(final ApplicationMetaData applicationData, final OfficeLicence license) {
        this.application = applicationData;
        this.license = license;

        LOG.trace("***** Created: {}", this);
    }

    public AboutPanelImpl() {
        LOG.trace("***** Created: {}", this);
    }


    @Override
    public void setLicense(OfficeLicence license) {
        this.license = license;
    }

    @Override
    public void setApplicationData(ApplicationMetaData data) {
        this.application = data;
    }


    @PostConstruct
    public void init() {
        Image logo = new Image(null, new ThemeResource("../images/lichti-wappen.png"));
        logo.setId("about-logo");
        logo.setAlternateText("Logo: Kaiserpfalz EDV-Service");
        logo.setWidth(150f, Unit.PIXELS);
        logo.setHeight(170f, Unit.PIXELS);
        logo.setCaption("Kaiserpfalz EDV-Service");

        addComponent(logo);
        setComponentAlignment(logo, Alignment.MIDDLE_CENTER);

        Label name = new Label(
                "<div style='text-align: center;'><b>" + application.get(ApplicationMetaData.APPLICATION_NAME) + "</b>"
                        + "<br/>"
                        + "<i><small>Version " + application.get(ApplicationMetaData.APPLICATION_VERSION) + "</small></i></div>",
                ContentMode.HTML
        );
        name.setWidth(200f, Unit.PIXELS);
        addComponent(name);
        setComponentAlignment(name, Alignment.MIDDLE_CENTER);


        addComponent(new Label(""));
        addComponent(new Label("License: " + license.getId()));
        addComponent(new Label("Licensee: " + license.getLicensee()));
        addComponent(new Label(""));
        addComponent(new Label("Issued: " + license.getIssueDate()));
        addComponent(new Label("Issuer: " + license.getIssuer()));
        addComponent(new Label(""));
        addComponent(new Label("Valid (Time): " + license.getStart() + " - " + license.getExpiry()));
        addComponent(
                new Label(
                        "Valid (Version): " + license.getVersionRange()
                                                     .getStart() + " - " + license.getVersionRange().getEnd()
                )
        );

        LOG.trace("  application: {}", application);
        LOG.trace("  licence: {}", license);
        LOG.debug("***** Initialized: {}", this);
    }

    @PreDestroy
    public void close() {
        removeAllComponents();

        LOG.trace("Destroyed: {}", this);
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        LOG.trace("{} received: {}", this, event);
    }

    @Override
    public void setPresenter(Presenter<?> presenter) {
        this.presenter = (AboutContentPresenter) presenter;
    }
}
