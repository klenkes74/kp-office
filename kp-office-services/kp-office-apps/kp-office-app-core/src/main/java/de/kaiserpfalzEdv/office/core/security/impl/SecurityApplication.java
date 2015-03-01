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

package de.kaiserpfalzEdv.office.core.security.impl;

import de.kaiserpfalzEdv.office.commons.commands.impl.FailureBuilder;
import de.kaiserpfalzEdv.office.commons.notifications.Notification;
import de.kaiserpfalzEdv.office.core.security.InvalidLoginException;
import de.kaiserpfalzEdv.office.core.security.InvalidTicketException;
import de.kaiserpfalzEdv.office.core.security.NoSuchAccountException;
import de.kaiserpfalzEdv.office.core.security.NoSuchTicketException;
import de.kaiserpfalzEdv.office.core.security.SecurityService;
import de.kaiserpfalzEdv.office.core.security.commands.SecurityCheckCommand;
import de.kaiserpfalzEdv.office.core.security.commands.SecurityCommandExecutor;
import de.kaiserpfalzEdv.office.core.security.commands.SecurityLoginCommand;
import de.kaiserpfalzEdv.office.core.security.commands.SecurityLogoutCommand;
import de.kaiserpfalzEdv.office.core.security.notifications.OfficeLoginTicketNotification;
import de.kaiserpfalzEdv.office.core.security.notifications.OfficeLogoutNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 01.03.15 20:17
 */
@Named
public class SecurityApplication implements SecurityCommandExecutor {
    private static final Logger LOG = LoggerFactory.getLogger(SecurityApplication.class);


    private SecurityService service;


    @Inject
    public SecurityApplication(final SecurityService service) {
        this.service = service;

        LOG.trace("Created: {}", this);
        LOG.trace("  security service: {}", this.service);
    }

    @PreDestroy
    public void close() {
        LOG.trace("Destroyed: {}", this);
    }


    @Override
    public Notification execute(final SecurityLoginCommand command) {
        Notification result;

        try {
            result = new OfficeLoginTicketNotification(service.login(command.getAccount(), command.getPassword()));
        } catch (InvalidLoginException | NoSuchAccountException e) {
            result = new FailureBuilder().withException(e).build();

            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);
        }

        return result;
    }

    @Override
    public Notification execute(final SecurityCheckCommand command) {
        Notification result;

        try {
            result = new OfficeLoginTicketNotification((service.check(command.getTicket())));
        } catch (InvalidTicketException | NoSuchTicketException e) {
            result = new FailureBuilder().withException(e).build();

            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);
        }

        return result;
    }

    @Override
    public Notification execute(final SecurityLogoutCommand command) {
        Notification result;

        service.logout(command.getTicket());

        return new OfficeLogoutNotification();
    }
}
