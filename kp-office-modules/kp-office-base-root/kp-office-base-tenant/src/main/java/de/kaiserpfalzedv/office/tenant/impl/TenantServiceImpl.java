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

package de.kaiserpfalzedv.office.tenant.impl;

import java.util.Collection;
import java.util.Properties;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.office.common.init.InitializationException;
import de.kaiserpfalzedv.office.tenant.adapter.data.TenantDataAdapter;
import de.kaiserpfalzedv.office.tenant.api.Tenant;
import de.kaiserpfalzedv.office.tenant.api.TenantDoesNotExistException;
import de.kaiserpfalzedv.office.tenant.api.TenantExistsException;
import de.kaiserpfalzedv.office.tenant.api.TenantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-24
 */
@ApplicationScoped
public class TenantServiceImpl implements TenantService {
    private static final Logger LOG = LoggerFactory.getLogger(TenantServiceImpl.class);

    private TenantDataAdapter repository;

    @Inject
    public TenantServiceImpl(
            @NotNull final TenantDataAdapter repository
    ) {
        this.repository = repository;
    }


    @Override
    public void init() throws InitializationException {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.office.tenant.impl.TenantServiceImpl.init
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void init(Properties properties) throws InitializationException {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.office.tenant.impl.TenantServiceImpl.init
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void close() {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.office.tenant.impl.TenantServiceImpl.close
        //To change body of implemented methods use File | Settings | File Templates.
    }


    @Override
    public Tenant create(Tenant data) throws TenantExistsException {
        return repository.create(data);
    }

    @Override
    public Tenant retrieve(UUID id) throws TenantDoesNotExistException {
        return repository.retrieve(id);
    }

    @Override
    public Collection<Tenant> retrieve() {
        return repository.retrieve();
    }

    @Override
    public Tenant update(Tenant data) throws TenantDoesNotExistException, TenantExistsException {
        return repository.update(data);
    }

    @Override
    public void delete(UUID id) {
        repository.delete(id);
    }

    @Override
    public Tenant retrieve(String businessKey) throws TenantDoesNotExistException {
        return repository.retrieve(businessKey);
    }
}
