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

package de.kaiserpfalzedv.iam.tenant.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.UUID;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.commons.api.data.ObjectExistsException;
import de.kaiserpfalzedv.commons.api.data.paging.Pageable;
import de.kaiserpfalzedv.commons.api.data.paging.PagedListBuilder;
import de.kaiserpfalzedv.commons.api.data.paging.PagedListable;
import de.kaiserpfalzedv.commons.api.data.query.Predicate;
import de.kaiserpfalzedv.commons.api.init.InitializationException;
import de.kaiserpfalzedv.iam.tenant.api.Tenant;
import de.kaiserpfalzedv.iam.tenant.api.TenantDoesNotExistException;
import de.kaiserpfalzedv.iam.tenant.api.TenantExistsException;
import de.kaiserpfalzedv.iam.tenant.api.TenantService;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-11-18
 */
@Dependent
public class JPATenantServiceImpl implements TenantService {
    private JPATenantRepository repository;


    @Inject
    public JPATenantServiceImpl(
            @NotNull final JPATenantRepository repository
    ) {
        this.repository = repository;
    }


    @Override
    public Tenant create(@NotNull final Tenant tenant) throws TenantExistsException {
        try {
            try {
                return repository.create((JPATenant) tenant);
            } catch (ClassCastException e) {
                JPATenant data = new JPATenantBuilder().withTenant(tenant).build();

                return repository.create(data);
            }
        } catch (ObjectExistsException e) {
            throw new TenantExistsException((JPATenant) e.getExistingObject());
        }
    }

    @Override
    public Tenant retrieve(@NotNull final UUID id) throws TenantDoesNotExistException {
        Optional<JPATenant> result = repository.retrieve(id);

        if (result.isPresent()) {
            return result.get();
        }

        throw new TenantDoesNotExistException(id);
    }

    @Override
    public PagedListable<Tenant> retrieve(
            @NotNull final Predicate<Tenant> predicate,
            @NotNull final Pageable page
    ) {
        PagedListable<JPATenant> data = repository.retrieve(page);

        List<Tenant> result = new ArrayList<>();
        result.addAll(data.getEntries());

        return new PagedListBuilder<Tenant>()
                .withPageable(page)
                .withData(result)
                .build();
    }

    @Override
    public Tenant update(@NotNull final Tenant tenant) throws TenantDoesNotExistException, TenantExistsException {
        try {
            repository.update((JPATenant) tenant);
        } catch (ClassCastException e) {
            JPATenant data = new JPATenantBuilder().withTenant(tenant).build();

            repository.update(data);
        }

        return tenant;
    }

    @Override
    public void delete(@NotNull final UUID id) {
        repository.delete(id);
    }

    @Override
    public void close() {
    }

    @Override
    public void init() throws InitializationException {
    }

    @Override
    public void init(Properties properties) throws InitializationException {
    }
}
