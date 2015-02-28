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

package de.kaiserpfalzEdv.office.core.tenants.impl;

import de.kaiserpfalzEdv.commons.jee.paging.Page;
import de.kaiserpfalzEdv.commons.jee.paging.PageDO;
import de.kaiserpfalzEdv.commons.jee.paging.Pageable;
import de.kaiserpfalzEdv.commons.jee.paging.PageableDO;
import de.kaiserpfalzEdv.office.commons.KPO;
import de.kaiserpfalzEdv.office.core.tenants.NoSuchTenantException;
import de.kaiserpfalzEdv.office.core.tenants.OnlyInvalidTenantFoundException;
import de.kaiserpfalzEdv.office.core.tenants.Tenant;
import de.kaiserpfalzEdv.office.core.tenants.TenantAlreadyExistsException;
import de.kaiserpfalzEdv.office.core.tenants.TenantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;

import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import java.util.UUID;

import static de.kaiserpfalzEdv.office.commons.KPO.Type.Implementation;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
@Named
@KPO(Implementation)
public class TenantServiceImpl implements TenantService {
    private static final Logger LOG = LoggerFactory.getLogger(TenantServiceImpl.class);
    
    private TenantRepository repository;
    
    
    @Inject
    public TenantServiceImpl(final TenantRepository repository) {
        this.repository = repository;
        
        LOG.trace("Created: {}", this);
    }
    
    
    @PreDestroy
    public void close() {
        LOG.trace("Destroyed: {}", this);
    }
    
    

    @Override
    public void create(@NotNull Tenant tenant) throws TenantAlreadyExistsException {
        try {
            repository.save(new TenantDO(tenant));
        } catch (DataIntegrityViolationException e) {
            throw new TenantAlreadyExistsException(tenant, e);
        }
        
        LOG.info("Saved: {}", tenant);
    }

    @Override
    public Tenant retrieve(@NotNull UUID id) throws NoSuchTenantException {
        TenantDO result = repository.findOne(id);
        
        if (result == null) {
            throw new NoSuchTenantException(id);
        }
        
        if (result.isHidden()) {
            throw new OnlyInvalidTenantFoundException(result);
        }

        LOG.info("Retrieved: {}", result);
        return result;
    }

    @Override
    public Tenant retrieve(@NotNull String displayNumber) throws NoSuchTenantException {
        TenantDO result = repository.findByDisplayNumber(displayNumber);
        
        if (result == null) {
            throw new NoSuchTenantException("No valid tenant with display number '" 
                    + displayNumber + "' found in store.");
        }
        
        if (result.isHidden()) {
            throw new OnlyInvalidTenantFoundException(result);
        }
        
        LOG.info("Retrieved: {}", result);
        return result;
    }

    @Override
    public void update(@NotNull UUID id, @NotNull Tenant tenant) throws NoSuchTenantException {
        TenantDO oldTenant = repository.findOne(id);
        
        if (oldTenant == null) {
            throw new NoSuchTenantException(id);
        }
        
        
        TenantDO newTenant = repository.save(new TenantDO(tenant));
        
        LOG.info("Changed tenant: {} => {}", oldTenant, newTenant);
    }

    @Override
    public void delete(@NotNull Tenant tenant) {
        TenantDO markHidden = new TenantDO(tenant);
        markHidden.setHidden(true);
        
        repository.save(markHidden);
        
        LOG.info("Marked as deleted: {}", markHidden);
    }

    @Override
    public void delete(@NotNull UUID id) {
        TenantDO markHidden = repository.findOne(id);
        
        delete(markHidden);
    }

    @Override
    public Page<Tenant> listTenants(Pageable pageable) {
        org.springframework.data.domain.Pageable paging = ((PageableDO)pageable).getPageable();

        org.springframework.data.domain.Page<TenantDO> tenants = repository.findAll(paging);
        
        return new PageDO<>(tenants);
    }

    @Override
    public Page<Tenant> listTenants(Tenant tenant, Pageable pageable) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public Page<Tenant> listTenants(UUID tenant, Pageable pageable) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
