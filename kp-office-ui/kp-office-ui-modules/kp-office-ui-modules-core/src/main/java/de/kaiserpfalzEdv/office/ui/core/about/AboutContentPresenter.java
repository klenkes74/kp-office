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

import de.kaiserpfalzEdv.commons.jee.servlet.model.ApplicationMetaData;
import de.kaiserpfalzEdv.office.clients.core.LicenceClient;
import de.kaiserpfalzEdv.office.ui.api.mvp.Presenter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 24.07.15 00:53
 */
@Named
public class AboutContentPresenter extends Presenter<AboutContent> {
    private static final Logger LOG = LoggerFactory.getLogger(AboutContentPresenter.class);

    @Inject
    private LicenceClient licenseClient;

    @Inject
    private ApplicationMetaData applicationData;

    public AboutContentPresenter() {
        LOG.trace("***** Created: {}", this);
    }

    @PostConstruct
    public void init() {
        LOG.trace("*   *   license client: {}", licenseClient);
        LOG.trace("*   *   application data: {}", applicationData);
        LOG.debug("***** Initialized: {}", this);
    }

    @PreDestroy
    public void close() {
        LOG.debug("***** Destroyed: {}", this);
    }


    @Override
    public void setView(AboutContent view) {
        super.setView(view);

        view.setLicense(licenseClient.getLicence());
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
