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

package de.kaiserpfalzedv.iam.access.jpa.roles;

import java.util.Optional;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.commons.api.data.DataUpdater;
import de.kaiserpfalzedv.commons.api.data.ObjectExistsException;
import de.kaiserpfalzedv.commons.api.data.Pageable;
import de.kaiserpfalzedv.commons.api.data.PagedListable;
import de.kaiserpfalzedv.commons.api.data.Predicate;
import de.kaiserpfalzedv.commons.jpa.AbstractBaseRepository;

/**
 * The implementation of a CRUD repository for the entitlement.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 1.0.0
 */
@ApplicationScoped
public class JPAEntitlementRepositoryImpl implements JPAEntitlementRepository, DataUpdater<JPAEntitlement> {
    @PersistenceContext(unitName = "ACCESS")
    private EntityManager em;

    private AbstractBaseRepository<JPAEntitlement> repo;

    @PostConstruct
    public void init() {
        repo = new AbstractBaseRepository<>(JPAEntitlement.class, "Entitlement");
    }

    public JPAEntitlement create(@NotNull final JPAEntitlement entity) throws ObjectExistsException {
        return repo.create(em, entity);
    }

    public Optional<JPAEntitlement> retrieve(@NotNull final UUID id) {
        return repo.retrieve(em, id);
    }

    public PagedListable<JPAEntitlement> retrieve(@NotNull final Pageable page) {
        return repo.retrieve(em, page);
    }

    public PagedListable<JPAEntitlement> retrieve(
            @NotNull final Predicate<JPAEntitlement> predicate,
            @NotNull final Pageable page
    ) {
        return repo.retrieve(em, predicate, page);
    }

    public void update(@NotNull final JPAEntitlement entity) {
        repo.update(em, entity, this);
    }

    public void delete(@NotNull final UUID id) {
        repo.delete(em, id);
    }

    public void delete(@NotNull final JPAEntitlement entity) {
        repo.delete(em, entity);
    }

    @Override
    public void update(@NotNull JPAEntitlement old, @NotNull final JPAEntitlement data) {
        old.update(data);
    }
}
