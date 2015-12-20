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

package de.kaiserpfalzEdv.office.clients.core.tenant.impl;

import de.kaiserpfalzEdv.commons.jee.paging.Page;
import de.kaiserpfalzEdv.commons.jee.paging.Pageable;
import de.kaiserpfalzEdv.office.commons.KPO;
import de.kaiserpfalzEdv.office.commons.client.mock.Store;
import de.kaiserpfalzEdv.office.core.tenants.NoSuchTenantException;
import de.kaiserpfalzEdv.office.core.tenants.Tenant;
import de.kaiserpfalzEdv.office.core.tenants.TenantAlreadyExistsException;
import de.kaiserpfalzEdv.office.core.tenants.TenantService;
import de.kaiserpfalzEdv.office.core.tenants.impl.TenantBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import java.util.UUID;

import static de.kaiserpfalzEdv.office.commons.KPO.Type.Mock;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 01.03.15 22:40
 */
@Named
@KPO(Mock)
public class TenantMockClient implements TenantService {
    private static final Logger LOG = LoggerFactory.getLogger(TenantMockClient.class);

    private static final Store<Tenant, UUID> tenants = new Store<>();

    public TenantMockClient() {
        LOG.trace("***** Created: {}", this);

        generateMockData();

        LOG.debug("***** Initialized: {}", this);
    }

    private void generateMockData() {
        for (int i = 1000; i < 1010; i++) {
            try {
                Tenant t = new TenantBuilder().withNumber(Integer.toString(i)).build();
                create(t);
            } catch (TenantAlreadyExistsException e) {
                LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);
            }
        }
    }

    @PreDestroy
    public void close() {
        LOG.trace("***** Destroyed: {}", this);
    }


    @Override
    public void create(@NotNull Tenant tenant) throws TenantAlreadyExistsException {
        if (tenants.findOne(tenant.getId().toString()) != null) {
            throw new TenantAlreadyExistsException(tenant, "A tenant with this id already exists!");
        }

        tenants.save(tenant);
    }

    @Override
    public Tenant retrieve(@NotNull UUID id) throws NoSuchTenantException {
        Tenant result = tenants.findOne(id.toString());

        if (result == null) {
            throw new NoSuchTenantException(id);
        }

        return result;
    }

    @Override
    public Tenant retrieve(@NotNull String displayNumber) throws NoSuchTenantException {
        for (Tenant t : tenants.findAll()) {
            if (t.getDisplayNumber().equals(displayNumber))
                return t;
        }

        throw new NoSuchTenantException(displayNumber);
    }

    @Override
    public void update(@NotNull UUID id, @NotNull Tenant tenant) throws NoSuchTenantException {
        tenants.save(tenant);
    }

    @Override
    public void delete(@NotNull Tenant tenant) {
        delete(tenant.getId());
    }

    @Override
    public void delete(@NotNull UUID id) {
        tenants.delete(id.toString());
    }

    @Override
    public Page<Tenant> listTenants(Pageable pageable) {
        return tenants.findAll(pageable);
    }

    @Override
    public Page<Tenant> listTenants(Tenant tenant, Pageable pageable) {
        throw new UnsupportedOperationException("Not yet implemented.");
    }

    @Override
    public Page<Tenant> listTenants(UUID tenant, Pageable pageable) {
        throw new UnsupportedOperationException("Not yet implemented.");
    }
}