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

package de.kaiserpfalzEdv.office.core.license.impl;

import de.kaiserpfalzEdv.office.commons.KPO;
import de.kaiserpfalzEdv.office.core.license.LicenseService;
import de.kaiserpfalzEdv.office.core.license.commands.GetLicenseCommand;
import de.kaiserpfalzEdv.office.core.license.commands.LicenseCommandExecutor;
import de.kaiserpfalzEdv.office.core.license.notifications.LicenseDataNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;

import static de.kaiserpfalzEdv.office.commons.KPO.Type.Implementation;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 01.03.15 22:26
 */
@Named
public class LicenseCommandExecutorImpl implements LicenseCommandExecutor {
    private static final Logger LOG = LoggerFactory.getLogger(LicenseCommandExecutorImpl.class);


    private LicenseService service;


    @Inject
    public LicenseCommandExecutorImpl(
            @KPO(Implementation) final LicenseService service
    ) {
        LOG.trace("***** Created: {}", this);

        this.service = service;
        LOG.trace("*   *   license service: {}", this.service);

        LOG.debug("***** Initialized: {}", this);
    }

    @PreDestroy
    public void close() {
        LOG.trace("***** Destroyed: {}", this);
    }


    @Override
    public LicenseDataNotification execute(GetLicenseCommand command) {
        LOG.info("Working on: {}", command);

        return new LicenseDataNotification(service.getLicense());
    }
}