/*
 * Copyright 2016 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzedv.office.tenant.mock;

import java.util.Collection;
import java.util.HashMap;
import java.util.Properties;
import java.util.UUID;

import de.kaiserpfalzedv.office.common.cdi.Mock;
import de.kaiserpfalzedv.office.common.init.InitializationException;
import de.kaiserpfalzedv.office.tenant.Tenant;
import de.kaiserpfalzedv.office.tenant.TenantDoesNotExistException;
import de.kaiserpfalzedv.office.tenant.TenantExistsException;
import de.kaiserpfalzedv.office.tenant.TenantService;
import de.kaiserpfalzedv.office.tenant.impl.NullTenant;
import de.kaiserpfalzedv.office.tenant.impl.TenantBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The mock implementation of the TenantService. Can be used as client during the development or testing of other
 * services depending on the TenantService.
 *
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2016-09-20
 */
@Mock
public class TenantMock implements TenantService {
    private static final Logger LOG = LoggerFactory.getLogger(TenantMock.class);


    private static final int DEFAULT_HASHMAPSIZE = 50;
    private final HashMap<UUID, Tenant> tenants = new HashMap<>(DEFAULT_HASHMAPSIZE);
    private final HashMap<String, UUID> displayNames = new HashMap<>(DEFAULT_HASHMAPSIZE);
    private final HashMap<String, UUID> fullNames = new HashMap<>(DEFAULT_HASHMAPSIZE);

    public TenantMock() {
        try {
            init();
        } catch (InitializationException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);
        }
    }

    @Override
    public void init() throws InitializationException {
        close();

        NullTenant nullTenant = new NullTenant();

        tenants.put(nullTenant.getId(), nullTenant);
        displayNames.put(nullTenant.getDisplayName(), nullTenant.getId());
        fullNames.put(nullTenant.getFullName(), nullTenant.getId());
    }

    @Override
    public void init(Properties properties) throws InitializationException {
        init();
    }

    @Override
    public void close() {
        tenants.clear();
        displayNames.clear();
        fullNames.clear();
    }

    public Tenant create(final Tenant tenant) throws TenantExistsException {
        checkDuplicateId(tenant);
        checkDuplicateDisplayName(tenant);
        checkDuplicateFullName(tenant);

        tenants.put(tenant.getId(), new TenantBuilder().withTenant(tenant).build());
        displayNames.put(tenant.getDisplayName(), tenant.getId());
        fullNames.put(tenant.getFullName(), tenant.getId());
        LOG.info("Saved new tenant with id #{}: {}", tenant.getId(), tenants.get(tenant.getId()));

        return tenants.get(tenant.getId());
    }

    private void checkDuplicateId(Tenant tenant) throws TenantExistsException {
        if (tenants.containsKey(tenant.getId())) {
            LOG.info("Tenant with id #{} already exists: {}", tenant.getId(), tenants.get(tenant.getId()));

            throw new TenantExistsException(tenants.get(tenant.getId()));
        }
    }

    private void checkDuplicateDisplayName(Tenant tenant) throws TenantExistsException {
        if (displayNames.containsKey(tenant.getDisplayName())) {
            Tenant existingTenant = tenants.get(displayNames.get(tenant.getDisplayName()));

            if (! tenant.getId().equals(existingTenant.getId())) {
                LOG.info("Tenant with display name '{}' already exists: {}",
                        tenant.getDisplayName(), existingTenant);
                throw new TenantExistsException(existingTenant);
            }
        }
    }

    private void checkDuplicateFullName(Tenant tenant) throws TenantExistsException {
        if (fullNames.containsKey(tenant.getFullName())) {
            Tenant existingTenant = tenants.get(fullNames.get(tenant.getFullName()));

            if (! tenant.getId().equals(existingTenant.getId())) {
                LOG.info("Tenant with full name '{}' already exists: {}",
                        tenant.getDisplayName(), existingTenant);

                throw new TenantExistsException(existingTenant);
            }
        }
    }

    public Tenant retrieve(final UUID id) throws TenantDoesNotExistException {
        if (!tenants.containsKey(id)) {
            throw new TenantDoesNotExistException(id);
        }

        return tenants.get(id);
    }

    @Override
    public Collection<Tenant> retrieve() {
        return tenants.values();
    }

    public Tenant update(final Tenant tenant) throws TenantDoesNotExistException, TenantExistsException {
        checkForNonexistingTenant(tenant);
        checkDuplicateDisplayName(tenant);
        checkDuplicateFullName(tenant);

        Tenant oldTenant = tenants.get(tenant.getId());
        displayNames.remove(oldTenant.getDisplayName());
        fullNames.remove(oldTenant.getFullName());
        tenants.remove(tenant.getId());

        tenants.put(tenant.getId(), new TenantBuilder().withTenant(tenant).build());
        displayNames.put(tenant.getDisplayName(), tenant.getId());
        fullNames.put(tenant.getFullName(), tenant.getId());


        LOG.info("Updated tenant with id #{}: {}", tenant.getId(), tenants.get(tenant.getId()));
        return tenants.get(tenant.getId());
    }

    private void checkForNonexistingTenant(Tenant tenant) throws TenantDoesNotExistException {
        if (! tenants.containsKey(tenant.getId())) {
            LOG.info("Tried updating not existing tenant with id #{}.", tenant.getId());
            throw new TenantDoesNotExistException(tenant.getId());
        }
    }

    public void delete(final UUID id) {
        if (tenants.containsKey(id)) {
            Tenant oldTenant = tenants.get(id);

            displayNames.remove(oldTenant.getDisplayName());
            fullNames.remove(oldTenant.getFullName());
        }

        tenants.remove(id);

        LOG.info("Deleted tenant with id #{}.", id);
    }
}
