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

import de.kaiserpfalzedv.commons.api.data.ObjectExistsException;
import de.kaiserpfalzedv.commons.api.data.base.DataUpdater;
import de.kaiserpfalzedv.commons.api.data.paging.Pageable;
import de.kaiserpfalzedv.commons.api.data.paging.PagedListable;
import de.kaiserpfalzedv.commons.api.data.query.Predicate;
import de.kaiserpfalzedv.commons.jpa.JPABaseRepository;
import de.kaiserpfalzedv.commons.jpa.JPAConverter;
import de.kaiserpfalzedv.iam.access.api.roles.Entitlement;
import de.kaiserpfalzedv.iam.access.impl.roles.EntitlementRepository;

/**
 * The implementation of a CRUD repository for the entitlement.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 1.0.0
 */
@ApplicationScoped
public class EntitlementRepositoryImpl
        implements EntitlementRepository, DataUpdater<JPAEntitlement>, JPAConverter<Entitlement, JPAEntitlement> {
    @PersistenceContext(unitName = "ACCESS")
    private EntityManager em;

    private JPABaseRepository<Entitlement, JPAEntitlement> repo;

    @PostConstruct
    public void init() {
        repo = new JPABaseRepository<>(JPAEntitlement.class, "Entitlement", this);
    }

    public Entitlement create(@NotNull final Entitlement entity) throws ObjectExistsException {
        return repo.create(em, entity);
    }


    public Optional<Entitlement> retrieve(@NotNull final UUID id) {
        return repo.retrieve(em, id);
    }

    public PagedListable<Entitlement> retrieve(@NotNull final Pageable page) {
        return repo.retrieve(em, page);
    }

    public PagedListable<Entitlement> retrieve(
            @NotNull final Predicate<Entitlement> predicate,
            @NotNull final Pageable page
    ) {
        return repo.retrieve(em, predicate, page);
    }


    public void update(@NotNull final Entitlement entity) {
        repo.update(em, entity, this);
    }

    public void delete(@NotNull final Entitlement entity) {
        repo.delete(em, entity);
    }


    public void delete(@NotNull final UUID id) {
        repo.delete(em, id);
    }

    @Override
    public void update(JPAEntitlement old, JPAEntitlement data) {
        old.update(data);
    }

    @Override
    public JPAEntitlement toJPA(Entitlement model) {
        return new EntitlementBuilder()
                .withEntitlement(model)
                .build();
    }

    @Override
    public Entitlement toModel(JPAEntitlement jpa) {
        return new de.kaiserpfalzedv.iam.access.client.roles.EntitlementBuilder()
                .withEntitlement(jpa).build();
    }

    @Override
    public Optional<Entitlement> toModel(Optional<JPAEntitlement> jpa) {
        if (jpa.isPresent()) {
            return Optional.of(
                    new de.kaiserpfalzedv.iam.access.client.roles.EntitlementBuilder()
                            .withEntitlement(jpa.get())
                            .build()
            );

        }

        return Optional.empty();
    }
}
