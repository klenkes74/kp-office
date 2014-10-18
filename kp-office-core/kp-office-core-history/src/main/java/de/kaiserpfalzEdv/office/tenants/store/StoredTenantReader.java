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

package de.kaiserpfalzEdv.office.tenants.store;

import de.kaiserpfalzEdv.office.tenants.NoSuchTenantException;
import de.kaiserpfalzEdv.office.tenants.OnlyInvalidTenantFoundException;
import de.kaiserpfalzEdv.office.tenants.Tenant;
import de.kaiserpfalzEdv.office.tenants.commands.TenantStoreCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * @author klenkes
 * @since 2014Q
 */
@Named
public class StoredTenantReader {
    private static final Logger LOG = LoggerFactory.getLogger(StoredTenantReader.class);

    private TenantStoreCommandRepository repository;

    @Inject
    public StoredTenantReader(
            final TenantStoreCommandRepository repository
    ) {
        this.repository = repository;

        LOG.trace("***** Created: {}", this);
        LOG.trace("* * *    tenant store repository: {}", this.repository);
    }

    @PreDestroy
    public void close() {
        LOG.trace("***** Destroyed: {}", this);
    }


    public Tenant retrieveCurrentInformation(final TenantStoreCommand command) throws NoSuchTenantException {
        List<TenantStoreCommand> result = repository.findByTenantIdOrderByHandledTimestampAsc(command.getTenantId());
        Tenant tenant = null;

        boolean valid = false;

        for (TenantStoreCommand cmd : result) {
            LOG.trace("Working on command '{}': {} ({})", cmd.getClass().getSimpleName(), tenant, valid ? "valid" : "invalid");

            tenant = cmd.updateTenant(tenant);
            valid = cmd.validTenant(valid);
        }

        if (tenant == null) {
            throw new NoSuchTenantException(command.getTenantId());
        }

        if (!valid)
            throw new OnlyInvalidTenantFoundException(tenant);

        LOG.debug("Read tenant from data store: {}", tenant);
        return tenant;
    }
}
