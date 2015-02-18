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

package de.kaiserpfalzEdv.office.ui.web.widgets.about;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import de.kaiserpfalzEdv.commons.jee.servlet.model.ApplicationMetaData;
import de.kaiserpfalzEdv.office.core.license.OfficeLicense;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.spring.annotation.VaadinUIScope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 18.02.15 08:23
 */
@Named
@VaadinUIScope
public class AboutPanel extends VerticalLayout implements View {
    private static final Logger LOG = LoggerFactory.getLogger(AboutPanel.class);

    @Inject
    private OfficeLicense license;

    @Inject
    private ApplicationMetaData application;


    public AboutPanel() {
        LOG.trace("Created: {}", this);
    }

    @PostConstruct
    public void init() {
        addComponent(new Label(application.get(ApplicationMetaData.APPLICATION_NAME)));
        addComponent(new Label("Version " + application.get(ApplicationMetaData.APPLICATION_VERSION)));
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
        LOG.trace("  license: {}", license);
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
