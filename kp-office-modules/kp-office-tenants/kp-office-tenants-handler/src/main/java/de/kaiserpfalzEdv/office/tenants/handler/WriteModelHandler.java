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

package de.kaiserpfalzEdv.office.tenants.handler;

import de.kaiserpfalzEdv.office.commands.OfficeCommand;
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
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
@Stateless
public class WriteModelHandler implements TenantCommandHandler {
    private static final Logger LOG = LoggerFactory.getLogger(WriteModelHandler.class);


    @PersistenceUnit(unitName = "KPO-TENANT-STORE")
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
        LOG.info("Created tenant: {}", command);
    }

    @Override
    public void handle(RenameTenantCommand command) throws TenantCommandException {
        LOG.info("Renamed tenant: {}", command);
    }

    @Override
    public void handle(RenumberTenantCommand command) throws TenantCommandException {
        LOG.info("Renumbered tenant: {}", command);
    }

    @Override
    public void handle(DeleteTenantCommand command) throws TenantCommandException {
        LOG.info("Deleted tenant: {}", command);
    }
}
