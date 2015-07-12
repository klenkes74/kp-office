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

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;
import de.kaiserpfalzEdv.commons.jee.servlet.model.ApplicationMetaData;
import de.kaiserpfalzEdv.office.core.licence.OfficeLicence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 18.02.15 08:23
 */
public class AboutContentImpl extends VerticalLayout implements AboutContent {
    private static final Logger LOG = LoggerFactory.getLogger(AboutContentImpl.class);

    private OfficeLicence       license;
    private ApplicationMetaData application;


    public AboutContentImpl() {
        LOG.trace("Created: {}", this);
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
    public void init() {
        addComponent(constructVersionString());
        addComponent(constructLicenseInformation());

        LOG.trace("Initialized: {}", this);
        LOG.trace("  application: {}", application);
        LOG.trace("  licence: {}", license);
    }

    public Layout constructVersionString() {
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
                                                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.GERMANY))
                )
        );

        return result;
    }

    @Override
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
