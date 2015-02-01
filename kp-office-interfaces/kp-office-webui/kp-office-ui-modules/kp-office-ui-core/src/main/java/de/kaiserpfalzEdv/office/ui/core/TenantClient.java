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

package de.kaiserpfalzEdv.office.ui.core;

import de.kaiserpfalzEdv.commons.jee.paging.Page;
import de.kaiserpfalzEdv.commons.jee.paging.Pageable;
import de.kaiserpfalzEdv.commons.service.BackendService;
import de.kaiserpfalzEdv.commons.service.FrontendService;
import de.kaiserpfalzEdv.office.core.tenants.NoSuchTenantException;
import de.kaiserpfalzEdv.office.core.tenants.Tenant;
import de.kaiserpfalzEdv.office.core.tenants.TenantAlreadyExistsException;
import de.kaiserpfalzEdv.office.core.tenants.TenantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
@Named
@FrontendService
public class TenantClient implements TenantService {
    private static final Logger LOG = LoggerFactory.getLogger(TenantClient.class);
    
    private TenantService service;
    
    
    @Inject
    public TenantClient(@BackendService TenantService service) {
        this.service = service;
        
        LOG.trace("Created: {}", this);
    }
    
    @PreDestroy
    public void close() {
        LOG.trace("Destroyed: {}", this);
    }

    
    @Override
    public void create(@NotNull Tenant tenant) throws TenantAlreadyExistsException {
        service.create(tenant);
    }

    @Override
    public Tenant retrieve(@NotNull UUID id) throws NoSuchTenantException {
        return service.retrieve(id);
    }

    @Override
    public Tenant retrieve(@NotNull String displayNumber) throws NoSuchTenantException {
        return service.retrieve(displayNumber);
    }

    @Override
    public void update(@NotNull UUID id, @NotNull Tenant tenant) throws NoSuchTenantException {
        service.update(id, tenant);
    }

    @Override
    public void delete(@NotNull Tenant tenant) {
        service.delete(tenant);
    }

    @Override
    public void delete(@NotNull UUID id) {
        service.delete(id);
    }


    @Override
    public Page<Tenant> listTenants(Pageable pageable) {
        return service.listTenants(pageable);
    }

    @Override
    public Page<Tenant> listTenants(Tenant tenant, Pageable pageable) {
        return service.listTenants(tenant, pageable);
    }

    @Override
    public Page<Tenant> listTenants(UUID tenant, Pageable pageable) {
        return service.listTenants(tenant, pageable);
    }
}
