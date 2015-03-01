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

package de.kaiserpfalzEdv.office.core.tenants.impl;

import de.kaiserpfalzEdv.office.commons.commands.impl.FailureBuilder;
import de.kaiserpfalzEdv.office.commons.notifications.Notification;
import de.kaiserpfalzEdv.office.commons.notifications.impl.SuccessImpl;
import de.kaiserpfalzEdv.office.core.tenants.NoSuchTenantException;
import de.kaiserpfalzEdv.office.core.tenants.TenantAlreadyExistsException;
import de.kaiserpfalzEdv.office.core.tenants.TenantService;
import de.kaiserpfalzEdv.office.core.tenants.commands.ListAllTenantsCommand;
import de.kaiserpfalzEdv.office.core.tenants.commands.TenantCommandExecutor;
import de.kaiserpfalzEdv.office.core.tenants.commands.TenantCreateCommand;
import de.kaiserpfalzEdv.office.core.tenants.commands.TenantDeleteCommand;
import de.kaiserpfalzEdv.office.core.tenants.commands.TenantLoadByIdCommand;
import de.kaiserpfalzEdv.office.core.tenants.commands.TenantLoadByNameCommand;
import de.kaiserpfalzEdv.office.core.tenants.commands.TenantLoadByNumberCommand;
import de.kaiserpfalzEdv.office.core.tenants.commands.TenantUpdateCommand;
import de.kaiserpfalzEdv.office.core.tenants.notifications.TenantDataNotification;
import de.kaiserpfalzEdv.office.core.tenants.notifications.TenantListNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 01.03.15 21:49
 */
@Named
public class TenantApplication implements TenantCommandExecutor {
    private static final Logger LOG = LoggerFactory.getLogger(TenantApplication.class);


    private TenantService service;


    @Inject
    public TenantApplication(final TenantService service) {
        this.service = service;

        LOG.trace("Created: {}", this);
        LOG.trace("  tenant service: {}", this.service);
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
    public Notification execute(TenantCreateCommand command) {
        try {
            service.create(command.getTenant());

            return new SuccessImpl();
        } catch (TenantAlreadyExistsException e) {
            return new FailureBuilder().withException(e).build();
        }
    }

    @Override
    public Notification execute(TenantLoadByIdCommand command) {
        try {
            return new TenantDataNotification(service.retrieve(command.getId()));
        } catch (NoSuchTenantException e) {
            return new FailureBuilder().withException(e).build();
        }
    }

    @Override
    public Notification execute(TenantLoadByNumberCommand command) {
        try {
            return new TenantDataNotification(service.retrieve(command.getNumber()));
        } catch (NoSuchTenantException e) {
            return new FailureBuilder().withException(e).build();
        }
    }

    @Override
    public Notification execute(TenantLoadByNameCommand command) {
        try {
            return new TenantDataNotification(service.retrieve(command.getName()));
        } catch (NoSuchTenantException e) {
            return new FailureBuilder().withException(e).build();
        }
    }

    @Override
    public Notification execute(TenantUpdateCommand command) {
        try {
            service.update(command.getId(), command.getTenant());

            return new SuccessImpl();
        } catch (NoSuchTenantException e) {
            return new FailureBuilder().withException(e).build();
        }
    }

    @Override
    public Notification execute(TenantDeleteCommand command) {
        service.delete(command.getId());

        return new SuccessImpl();
    }

    @Override
    public Notification execute(ListAllTenantsCommand command) {
        return new TenantListNotification(service.listTenants(command.getPageable()));
    }
}
