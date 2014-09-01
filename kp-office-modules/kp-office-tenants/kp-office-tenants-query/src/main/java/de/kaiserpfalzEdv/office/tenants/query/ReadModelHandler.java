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

import de.kaiserpfalzEdv.office.commands.OfficeCommand;
import de.kaiserpfalzEdv.office.tenant.TenantDTO;
import de.kaiserpfalzEdv.office.tenants.api.TenantCommandException;
import de.kaiserpfalzEdv.office.tenants.api.commands.CreateTenantCommand;
import de.kaiserpfalzEdv.office.tenants.api.commands.DeleteTenantCommand;
import de.kaiserpfalzEdv.office.tenants.api.commands.RenameTenantCommand;
import de.kaiserpfalzEdv.office.tenants.api.commands.RenumberTenantCommand;
import de.kaiserpfalzEdv.office.tenants.api.commands.TenantCommandHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
@Named
public class ReadModelHandler implements TenantCommandHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ReadModelHandler.class);


    @PersistenceUnit(unitName = "KPO-TENANT-QUERY")
    private EntityManager em;

    @PostConstruct
    public void init() {
        LOG.trace("***** Created: {}", this.toString());
    }

    @PreDestroy
    public void close() {
        LOG.trace("***** Destroyed: {}", this.toString());
    }


    @Override
    public void handle(OfficeCommand command) throws TenantCommandException {
        LOG.error("Could not handle command: {}", command);
    }


    @Override
    public void handle(CreateTenantCommand command) throws TenantCommandException {
        TenantDTO tenant = new TenantDTO(command.getTenantId(), command.getDisplayNumber(), command.getDisplayName());

        em.persist(tenant);

        LOG.info("Created tenant: {}", tenant);
    }

    @Override
    public void handle(RenameTenantCommand command) throws TenantCommandException {
        TenantDTO tenant = em
                .createNamedQuery("Tenant.ById", TenantDTO.class)
                .setParameter("id", command.getTenantId())
                .getSingleResult();

        tenant.setDisplayName(command.getDisplayName());

        LOG.info("Changed name of tenant: {}", tenant);
    }

    @Override
    public void handle(RenumberTenantCommand command) throws TenantCommandException {
        TenantDTO tenant = em
                .createNamedQuery("Tenant.ById", TenantDTO.class)
                .setParameter("id", command.getTenantId())
                .getSingleResult();

        tenant.setDisplayNumber(command.getDisplayNumber());

        LOG.info("Changed number of tenant: {}", tenant);
    }

    @Override
    public void handle(DeleteTenantCommand command) throws TenantCommandException {
        TenantDTO tenant = em
                .createNamedQuery("Tenant.ById", TenantDTO.class)
                .setParameter("id", command.getTenantId())
                .getSingleResult();

        em.remove(tenant);

        LOG.info("Deleted tenant: {}", tenant);
    }
}
