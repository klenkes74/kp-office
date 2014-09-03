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
import de.kaiserpfalzEdv.office.tenants.api.TenantCommandException;
import de.kaiserpfalzEdv.office.tenants.api.commands.TenantQueryById;
import de.kaiserpfalzEdv.office.tenants.api.commands.TenantQueryByName;
import de.kaiserpfalzEdv.office.tenants.api.commands.TenantQueryByNumber;
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
public class QueryExecutor {
    private static final Logger LOG = LoggerFactory.getLogger(QueryExecutor.class);


    private TenantRepository repository;

    @Inject
    public QueryExecutor(final TenantRepository repository) {
        this.repository = repository;

        LOG.trace("Created: {}", this);
    }

    @PreDestroy
    public void close() {
        LOG.trace("Destroyed: {}", this.toString());
    }


    public Tenant handle(final TenantQueryById command) throws TenantCommandException {
        LOG.info("Query received: {}", command);

        Tenant tenant = repository.findOne(command.getTenantId());

        LOG.info("Queried tenant: {}", tenant);
        return tenant;
    }

    public Tenant handle(final TenantQueryByNumber command) throws TenantCommandException {
        LOG.info("Query received: {}", command);

        Tenant tenant = repository.findByDisplayNumber(command.getDisplayNumber());

        LOG.info("Queried tenant: {}", tenant);
        return tenant;
    }

    public Tenant handle(final TenantQueryByName command) throws TenantCommandException {
        LOG.info("Query received: {}", command);

        Tenant tenant = repository.findByDisplayName(command.getDisplayName());

        LOG.info("Queried tenant: {}", tenant);
        return tenant;
    }
}
