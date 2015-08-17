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

package de.kaiserpfalzEdv.office.ui.core.license;

import com.google.common.eventbus.Subscribe;
import de.kaiserpfalzEdv.commons.jee.eventbus.EventBusHandler;
import de.kaiserpfalzEdv.office.clients.core.impl.LicenceClient;
import de.kaiserpfalzEdv.office.core.licence.OfficeLicence;
import de.kaiserpfalzEdv.office.ui.api.mvp.Presenter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 30.07.15 01:45
 */
@Named
public class LicensePresenter extends Presenter<LicenseView> {
    private static final Logger LOG = LoggerFactory.getLogger(LicensePresenter.class);

    private EventBusHandler bus;

    private LicenceClient licenseClient;
    private OfficeLicence license;


    @Inject
    public LicensePresenter(final EventBusHandler bus, final LicenceClient client) {
        setBus(bus);
        setLicenseClient(client);

        LOG.trace("***** Created: {}", this);
        LOG.trace("*   *   event bus: {}", this.bus);
        LOG.trace("*   *   license client: {}", this.licenseClient);
        LOG.debug("***** Initialized: {}", this);
    }

    @PreDestroy
    public void close() {
        bus.unregister(this);

        LOG.debug("***** Destroyed: {}", this);
    }


    public void setView(LicenseView view) {
        super.setView(view);

        getView().setLicense(getLicense());
    }


    public void setBus(@NotNull EventBusHandler bus) {
        if (this.bus != null) {
            this.bus.unregister(this);
        }

        this.bus = bus;
        this.bus.register(this);
    }

    @Subscribe
    public void setLicenseClient(@NotNull final LicenceClient licenseClient) {
        this.licenseClient = licenseClient;
        this.license = null;

        LicenseView view = getView();

        if (view != null)
            view.setLicense(licenseClient.getLicence());
    }


    private OfficeLicence getLicense() {
        if (license == null)
            license = licenseClient.getLicence();

        return license;
    }


    @Override
    public String toString() {
        ToStringBuilder result = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .append("bus", bus)
                .append("licenseClient", licenseClient);

        if (license != null)
            result.append("license", license);

        return result.toString();
    }
}
