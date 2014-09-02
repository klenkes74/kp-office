/*
 * Copyright (c) 2014 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzEdv.office.tenants.query;

import de.kaiserpfalzEdv.office.tenant.Tenant;
import de.kaiserpfalzEdv.office.tenant.TenantDTO;
import de.kaiserpfalzEdv.office.tenants.api.TenantCommandException;
import de.kaiserpfalzEdv.office.tenants.api.TenantNotificationException;
import de.kaiserpfalzEdv.office.tenants.api.commands.TenantQueryById;
import de.kaiserpfalzEdv.office.tenants.api.commands.TenantQueryByName;
import de.kaiserpfalzEdv.office.tenants.api.commands.TenantQueryByNumber;
import de.kaiserpfalzEdv.office.tenants.api.notifications.CreateTenantNotification;
import de.kaiserpfalzEdv.office.tenants.api.notifications.DeleteTenantNotification;
import de.kaiserpfalzEdv.office.tenants.api.notifications.SyncTenantNotification;
import de.kaiserpfalzEdv.office.tenants.api.notifications.TenantNotificationHandler;
import de.kaiserpfalzEdv.office.tenants.api.notifications.UpdateTenantNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
@Named
public class QueryModelHandler implements TenantNotificationHandler {
    private static final Logger LOG = LoggerFactory.getLogger(QueryModelHandler.class);


    private TenantRepository repository;

    @Inject
    public QueryModelHandler(final TenantRepository repository) {
        this.repository = repository;

        LOG.trace("***** Created: {}", this);
        LOG.trace("* * *   tenant repository: {}", this.repository);
    }

    @PreDestroy
    public void close() {
        LOG.trace("***** Destroyed: {}", this.toString());
    }


    @Override
    public void handle(CreateTenantNotification command) throws TenantNotificationException {
        TenantDTO tenant = new TenantDTO(command.getTenantId(), command.getDisplayNumber(), command.getDisplayName());

        tenant = repository.save(tenant);

        LOG.info("Created tenant: {}", tenant);
    }

    @Override
    public void handle(UpdateTenantNotification command) throws TenantNotificationException {
        TenantDTO tenant = repository.findOne(command.getTenantId());

        tenant.setDisplayName(command.getDisplayName());

        LOG.info("Changed name of tenant: {}", tenant);
    }

    @Override
    public void handle(DeleteTenantNotification command) throws TenantNotificationException {
        repository.delete(command.getTenantId());

        LOG.info("Deleted tenant: {}", command.getTenantId());
    }

    @Override
    public void handle(SyncTenantNotification notification) throws TenantNotificationException {
        TenantDTO tenant = new TenantDTO(notification.getTenantId(), notification.getDisplayNumber(), notification.getDisplayName());

        tenant = repository.save(tenant);

        LOG.info("Synchronized tenant: {}", tenant);
    }


    public Tenant handle(final TenantQueryById command) throws TenantCommandException {
        return repository.findOne(command.getTenantId());
    }

    public Tenant handle(final TenantQueryByNumber command) throws TenantCommandException {
        return repository.findByDisplayNumber(command.getDisplayNumber());
    }

    public Tenant handle(final TenantQueryByName command) throws TenantCommandException {
        return repository.findByDisplayName(command.getDisplayName());
    }
}
