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
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import de.kaiserpfalzEdv.commons.jee.servlet.model.ApplicationMetaData;
import de.kaiserpfalzEdv.office.core.licence.OfficeLicence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.spring.annotation.VaadinUIScope;
import org.vaadin.spring.navigator.annotation.VaadinView;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 18.02.15 08:23
 */
@VaadinView(name = AboutPanel.NAME)
@VaadinUIScope
public class AboutPanel extends VerticalLayout implements View {
    public static final  String NAME = "core.about.panel";
    private static final Logger LOG  = LoggerFactory.getLogger(AboutPanel.class);
    @Inject
    private OfficeLicence license;

    @Inject
    private ApplicationMetaData application;


    public AboutPanel() {
        LOG.trace("Created: {}", this);
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

        LOG.trace("Initialized: {}", this);
        LOG.trace("  application: {}", application);
        LOG.trace("  licence: {}", license);
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
}
