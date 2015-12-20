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

package de.kaiserpfalzEdv.office.clients.core.about;

import de.kaiserpfalzEdv.commons.jee.servlet.model.ApplicationMetaData;
import de.kaiserpfalzEdv.office.clients.core.license.impl.LicenseClient;
import de.kaiserpfalzEdv.office.commons.KPO;
import de.kaiserpfalzEdv.office.commons.client.mvp.Presenter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;

import static de.kaiserpfalzEdv.office.commons.KPO.Type.Client;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 24.07.15 00:53
 */
@Named
public class AboutContentPresenter extends Presenter<AboutContent> {
    private static final Logger LOG = LoggerFactory.getLogger(AboutContentPresenter.class);

    private LicenseClient licenseClient;
    private ApplicationMetaData applicationData;

    @Inject
    public AboutContentPresenter(
            @KPO(Client) final LicenseClient licenseClient,
            final ApplicationMetaData applicationData
    ) {
        LOG.trace("***** Created: {}", this);

        this.licenseClient = licenseClient;
        LOG.trace("*   *   license client: {}", this.licenseClient);

        this.applicationData = applicationData;
        LOG.trace("*   *   application data: {}", this.applicationData);

        LOG.debug("***** Created: {}", this);
    }

    @PreDestroy
    public void close() {
        LOG.debug("***** Destroyed: {}", this);
    }


    @Override
    public void setView(AboutContent view) {
        super.setView(view);

        view.setLicense(licenseClient.getLicense());
        view.setApplication(applicationData);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
                .appendSuper(super.toString())
                .append("view", getView())
                .append("license client", licenseClient)
                .append("application data", applicationData)
                .toString();
    }
}
