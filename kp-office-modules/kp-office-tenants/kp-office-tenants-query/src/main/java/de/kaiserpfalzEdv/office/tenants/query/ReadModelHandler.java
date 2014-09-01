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
import org.apache.commons.lang3.StringUtils;
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
public class ReadModelHandler implements TenantCommandHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ReadModelHandler.class);


    private TenantRepository repository;

    @Inject
    public ReadModelHandler(final TenantRepository repository) {
        this.repository = repository;

        LOG.trace("***** Created: {}", this);
        LOG.trace("* * *   tenant repository: {}", this.repository);
    }

    @PreDestroy
    public void close() {
        LOG.trace("***** Destroyed: {}", this.toString());
    }


    @Override
    public void handle(OfficeCommand command) throws TenantCommandException {
        LOG.info("Working on: {} as {}", command, command.getClass());
    }


    @Override
    public void handle(CreateTenantCommand command) throws TenantCommandException {
        if (StringUtils.isBlank(command.getDisplayNumber())) {
            command.setDisplayNumber(command.getTenantId().toString());
        }

        TenantDTO tenant = new TenantDTO(command.getTenantId(), command.getDisplayNumber(), command.getDisplayName());


        tenant = repository.save(tenant);

        LOG.info("Created tenant: {}", tenant);
    }

    @Override
    public void handle(RenameTenantCommand command) throws TenantCommandException {
        TenantDTO tenant = repository.findOne(command.getTenantId());

        tenant.setDisplayName(command.getDisplayName());

        LOG.info("Changed name of tenant: {}", tenant);
    }

    @Override
    public void handle(RenumberTenantCommand command) throws TenantCommandException {
        TenantDTO tenant = repository.findOne(command.getTenantId());

        tenant.setDisplayNumber(command.getDisplayNumber());

        LOG.info("Changed number of tenant: {}", tenant);
    }

    @Override
    public void handle(DeleteTenantCommand command) throws TenantCommandException {
        repository.delete(command.getTenantId());

        LOG.info("Deleted tenant: {}", command.getTenantId());
    }
}
