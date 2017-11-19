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

import java.util.Optional;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.commons.api.data.ObjectExistsException;
import de.kaiserpfalzedv.commons.api.data.paging.Pageable;
import de.kaiserpfalzedv.commons.api.data.paging.PagedListable;
import de.kaiserpfalzedv.commons.api.data.query.Predicate;
import de.kaiserpfalzedv.commons.jpa.JPABaseRepository;
import de.kaiserpfalzedv.iam.tenant.api.Tenant;

/**
 * The implementation of a CRUD repository for the {@link JPATenant}.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 1.0.0
 */
@ApplicationScoped
public class JPATenantRepositoryImpl implements JPATenantRepository {
    @PersistenceContext(unitName = "TENANT")
    private EntityManager em;

    private JPABaseRepository<JPATenant, Tenant> repo;


    @PostConstruct
    public void init() {
        repo = new JPABaseRepository<>(JPATenant.class, "Tenant");
    }


    @Override
    public JPATenant create(@NotNull final JPATenant entity) throws ObjectExistsException {
        return repo.create(em, entity);
    }

    @Override
    public Optional<JPATenant> retrieve(@NotNull final UUID id) {
        return repo.retrieve(em, id);
    }

    @Override
    public PagedListable<JPATenant> retrieve(@NotNull final Pageable page) {
        return repo.retrieve(em, page);
    }

    @Override
    public PagedListable<JPATenant> retrieve(
            @NotNull final Predicate<Tenant> predicate,
            @NotNull final Pageable page
    ) {
        return repo.retrieve(em, predicate, page);
    }

    @Override
    public void update(@NotNull final JPATenant entity) {
        repo.update(em, entity);
    }

    @Override
    public void delete(@NotNull final UUID id) {
        repo.delete(em, id);
    }

    @Override
    public void delete(@NotNull final JPATenant entity) {
        repo.delete(em, entity);
    }
}
