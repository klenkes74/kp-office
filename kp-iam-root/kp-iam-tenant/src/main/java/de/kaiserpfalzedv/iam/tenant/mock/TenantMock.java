/*
 * Copyright 2017 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzedv.iam.tenant.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;

import de.kaiserpfalzedv.commons.api.cdi.Mock;
import de.kaiserpfalzedv.commons.api.data.paging.Pageable;
import de.kaiserpfalzedv.commons.api.data.paging.PageableBuilder;
import de.kaiserpfalzedv.commons.api.data.paging.PagedListBuilder;
import de.kaiserpfalzedv.commons.api.data.paging.PagedListable;
import de.kaiserpfalzedv.commons.api.data.query.Predicate;
import de.kaiserpfalzedv.commons.api.data.query.PredicateParameterGenerator;
import de.kaiserpfalzedv.commons.api.data.query.QueryCollectionParameter;
import de.kaiserpfalzedv.commons.api.data.query.QueryParameter;
import de.kaiserpfalzedv.commons.api.data.query.QuerySingleValueParameter;
import de.kaiserpfalzedv.commons.api.init.InitializationException;
import de.kaiserpfalzedv.commons.impl.data.query.PredicateToParameterParser;
import de.kaiserpfalzedv.iam.tenant.api.NullTenant;
import de.kaiserpfalzedv.iam.tenant.api.Tenant;
import de.kaiserpfalzedv.iam.tenant.api.TenantBuilder;
import de.kaiserpfalzedv.iam.tenant.api.TenantDoesNotExistException;
import de.kaiserpfalzedv.iam.tenant.api.TenantExistsException;
import de.kaiserpfalzedv.iam.tenant.client.TenantClient;
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
@ApplicationScoped
public class TenantMock implements TenantClient {
    private static final Logger LOG = LoggerFactory.getLogger(TenantMock.class);


    private static final int DEFAULT_HASHMAPSIZE = 50;
    private final HashMap<UUID, Tenant> tenants = new HashMap<>(DEFAULT_HASHMAPSIZE);
    private final HashMap<String, Tenant> tenantsByKey = new HashMap<>(DEFAULT_HASHMAPSIZE);
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
        tenantsByKey.put(nullTenant.getKey(), nullTenant);
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
        tenantsByKey.clear();
        displayNames.clear();
        fullNames.clear();
    }

    public Tenant create(final Tenant tenant) throws TenantExistsException {
        checkDuplicateId(tenant);
        checkDuplicateKey(tenant);
        checkDuplicateDisplayName(tenant);
        checkDuplicateFullName(tenant);

        tenants.put(tenant.getId(), new TenantBuilder().withTenant(tenant).build());
        tenantsByKey.put(tenant.getKey(), tenants.get(tenant.getId()));

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

    private void checkDuplicateKey(Tenant tenant) throws TenantExistsException {
        if (tenantsByKey.containsKey(tenant.getKey()) && !tenant.equals(tenantsByKey.get(tenant.getKey()))) {
            LOG.info("Tenant with key '{}' already exists: {}", tenant.getKey(), tenant);

            throw new TenantExistsException(tenantsByKey.get(tenant.getKey()));
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
    public PagedListable<Tenant> retrieve(Predicate<Tenant> predicate, Pageable page) {
        ArrayList<Tenant> data = new ArrayList<>(tenants.size());
        PredicateParameterGenerator<Tenant> parameterParser = new PredicateToParameterParser<>();
        List<QueryParameter<Tenant>> parameters = parameterParser.generateParameters(predicate);

        for (QueryParameter<Tenant> p : parameters) {
            if (QuerySingleValueParameter.class.isAssignableFrom(p.getClass())) {
                switch (p.getName()) {
                    case "id":
                        data.add(tenants.get(((QuerySingleValueParameter) p).getValue()));
                        break;
                    case "key":
                        data.add(tenantsByKey.get(((QuerySingleValueParameter) p).getValue()));
                        break;
                    case "displayName":
                        tenants.values().forEach(t -> {
                            if (t.getDisplayName().equals(((QuerySingleValueParameter) p).getValue()))
                                data.add(t);
                        });
                        break;
                    case "fullName":
                        tenants.values().forEach(t -> {
                            if (t.getFullName().equals(((QuerySingleValueParameter) p).getValue()))
                                data.add(t);
                        });
                        break;
                    default:
                        throw new IllegalStateException("There is no attribute '" + p.getName() + "'!");
                }
            } else {
                switch (p.getName()) {
                    case "id":
                        tenants.values().forEach(t -> {
                            if (((QueryCollectionParameter) p).getValue().contains(t.getId())) {
                                data.add(t);
                            }
                        });
                        break;
                    case "key":
                        tenants.values().forEach(t -> {
                            if (((QueryCollectionParameter) p).getValue().contains(t.getKey())) {
                                data.add(t);
                            }
                        });
                        break;
                    case "displayName":
                        tenants.values().forEach(t -> {
                            if (((QueryCollectionParameter) p).getValue().contains(t.getDisplayName())) {
                                data.add(t);
                            }
                        });
                        break;
                    case "fullName":
                        tenants.values().forEach(t -> {
                            if (((QueryCollectionParameter) p).getValue().contains(t.getFullName())) {
                                data.add(t);
                            }
                        });
                        break;
                    default:
                        throw new IllegalStateException("There is no attribute '" + p.getName() + "'!");
                }
            }
        }

        List pageData = data.subList(page.getFirstResult(), page.getFirstResult() + (int) page.getSize());

        return new PagedListBuilder<Tenant>()
                .withData(pageData)
                .withPageable(
                        new PageableBuilder()
                                .withPage(page)
                                .withTotalCount(data.size())
                                .build()
                ).build();
    }

    public Tenant update(final Tenant tenant) throws TenantDoesNotExistException, TenantExistsException {
        checkForNonexistingTenant(tenant);
        checkDuplicateKey(tenant);
        checkDuplicateDisplayName(tenant);
        checkDuplicateFullName(tenant);

        Tenant oldTenant = tenants.get(tenant.getId());
        displayNames.remove(oldTenant.getDisplayName());
        fullNames.remove(oldTenant.getFullName());
        tenants.remove(tenant.getId());
        tenantsByKey.remove(tenant.getKey());

        tenants.put(tenant.getId(), new TenantBuilder().withTenant(tenant).build());
        tenantsByKey.put(tenant.getKey(), tenants.get(tenant.getId()));
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
            tenantsByKey.remove(oldTenant.getKey());
        }

        tenants.remove(id);

        LOG.info("Deleted tenant with id #{}.", id);
    }
}
