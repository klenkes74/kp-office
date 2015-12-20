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

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import de.kaiserpfalzEdv.office.clients.core.license.impl.LicensePresenter;
import de.kaiserpfalzEdv.office.commons.client.mvp.Presenter;
import de.kaiserpfalzEdv.office.core.license.OfficeLicense;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import javax.validation.constraints.NotNull;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 30.07.15 12:53
 */
@UIScope
@SpringView(name = LicenseView.VIEW_NAME)
public class LicenseView extends CssLayout implements de.kaiserpfalzEdv.office.clients.core.license.impl.LicenseView, View, Component {
    public static final  String VIEW_NAME = "License";
    private static final Logger LOG       = LoggerFactory.getLogger(LicenseView.class);
    private OfficeLicense license;
    private LicensePresenter presenter;


    public LicenseView(final LicensePresenter presenter) {
        LOG.trace("***** Created: {}", this);

        setPresenter(presenter);
        LOG.debug("***** Initialized: {}", this);
    }

    @PreDestroy
    public void close() {
        LOG.trace("***** Destroyed: {}", this);
    }


    private void init() {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);

        presenter.setView(this);

        Label softwareName = new Label(
                license.getSoftware() + " (v. " + license.getVersionRange()
                                                         .getStart()
                                                         .toString() + " - " + license.getVersionRange()
                                                                                      .getEnd()
                                                                                      .toString() + ")"
        );
        Label licenseId = new Label("License: " + license.getId().toString());
        Label licenseValidityDate = new Label(
                "Valid: " + license.getStart()
                                   .format(formatter) + " - " + license.getExpiry()
                                                                       .format(formatter)
        );
        Label issuer = new Label("Issued by: " + license.getIssuer());
        Label licensee = new Label("Licensed to: " + license.getLicensee());


        addComponent(softwareName);
        addComponent(licenseId);
        addComponent(licenseValidityDate);
        addComponent(issuer);
        addComponent(licensee);
    }


    @Override
    public void setLicense(@NotNull final OfficeLicense license) {
        LOG.trace("Changing license: {} -> {}", this.license, license);
        this.license = license;
    }

    @Override
    public void setPresenter(@NotNull final Presenter<?> presenter) {
        LOG.trace("Changing presenter: {} -> {}", this.presenter, presenter);

        this.presenter = (LicensePresenter) presenter;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        LOG.trace("Entering {} due to: {}", this, event);

        removeAllComponents();
        init();
    }
}