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

package de.kaiserpfalzEdv.office.security.query;

import de.kaiserpfalzEdv.office.tenants.Tenant;
import de.kaiserpfalzEdv.office.tenants.TenantNotificationException;
import de.kaiserpfalzEdv.office.tenants.notifications.CreateTenantNotification;
import de.kaiserpfalzEdv.office.tenants.notifications.DeleteTenantNotification;
import de.kaiserpfalzEdv.office.tenants.notifications.SyncTenantNotification;
import de.kaiserpfalzEdv.office.tenants.notifications.UpdateTenantNotification;
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
public class QueryModelModifierImpl implements QueryModelModifier {
    private static final Logger LOG = LoggerFactory.getLogger(QueryModelModifierImpl.class);


    private TenantRepository repository;

    @Inject
    public QueryModelModifierImpl(final TenantRepository repository) {
        this.repository = repository;

        LOG.trace("Created: {}", this);
    }

    @PreDestroy
    public void close() {
        LOG.trace("Destroyed: {}", this.toString());
    }


    @Override
    public void handle(CreateTenantNotification command) throws TenantNotificationException {
        Tenant.TenantDTO tenant = new Tenant.TenantDTO(command.getTenantId(), command.getDisplayNumber(), command.getDisplayName());

        tenant = repository.save(tenant);

        LOG.info("Created tenant: {}", tenant);
    }

    @Override
    public void handle(UpdateTenantNotification command) throws TenantNotificationException {
        Tenant.TenantDTO tenant = repository.findOne(command.getTenantId());

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
        Tenant.TenantDTO tenant = new Tenant.TenantDTO(notification.getTenantId(), notification.getDisplayNumber(), notification.getDisplayName());

        tenant = repository.save(tenant);

        LOG.info("Synchronized tenant: {}", tenant);
    }
}
