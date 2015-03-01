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

package de.kaiserpfalzEdv.office.core.licence.impl;

import de.kaiserpfalzEdv.office.commons.notifications.Notification;
import de.kaiserpfalzEdv.office.core.licence.LicenceService;
import de.kaiserpfalzEdv.office.core.licence.commands.GetLicenceCommand;
import de.kaiserpfalzEdv.office.core.licence.commands.LicenceCommandExecutor;
import de.kaiserpfalzEdv.office.core.licence.notifications.LicenceDataNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 01.03.15 22:26
 */
@Named
public class LicenceApplication implements LicenceCommandExecutor {
    private static final Logger LOG = LoggerFactory.getLogger(LicenceApplication.class);


    private LicenceService service;


    @Inject
    public LicenceApplication(final LicenceService service) {
        this.service = service;

        LOG.trace("Created: {}", this);
        LOG.trace("  licence service: {}", this.service);
    }

    @PostConstruct
    public void init() {
        LOG.trace("Initialized: {}", this);
    }

    @PreDestroy
    public void close() {
        LOG.trace("Destroyed: {}", this);
    }


    @Override
    public Notification execute(GetLicenceCommand command) {
        return new LicenceDataNotification(service.getLicence());
    }
}