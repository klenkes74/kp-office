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
import de.kaiserpfalzedv.iam.access.api.roles.Role;
import de.kaiserpfalzedv.iam.access.client.roles.RoleBuilder;
import de.kaiserpfalzedv.iam.access.impl.roles.RoleRepository;

/**
 * The implementation of a CRUD repository for the entitlement.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 1.0.0
 */
@ApplicationScoped
public class RoleRepositoryImpl implements RoleRepository, DataUpdater<JPARole>, JPAConverter<Role, JPARole> {
    @PersistenceContext(unitName = "ACCESS")
    private EntityManager em;

    private JPABaseRepository<Role, JPARole> repo;

    @PostConstruct
    public void init() {
        repo = new JPABaseRepository<>(JPARole.class, "Role", this);
    }

    @Override
    public Role create(@NotNull final JPARole entity) throws ObjectExistsException {
        return repo.create(em, entity);
    }

    @Override
    public Optional<Role> retrieve(@NotNull final UUID id) {
        return repo.retrieve(em, id);
    }

    @Override
    public PagedListable<Role> retrieve(@NotNull final Pageable page) {
        return repo.retrieve(em, page);
    }

    @Override
    public PagedListable<Role> retrieve(
            @NotNull final Predicate<Role> predicate,
            @NotNull final Pageable page
    ) {
        return repo.retrieve(em, predicate, page);
    }

    @Override
    public void update(@NotNull final Role entity) {
        repo.update(em, entity, this);
    }

    @Override
    public void delete(@NotNull final UUID id) {
        repo.delete(em, id);
    }

    @Override
    public void delete(@NotNull final Role entity) {
        repo.delete(em, entity);
    }

    @Override
    public void update(@NotNull JPARole old, @NotNull final JPARole data) {
        old.update(data);
    }

    @Override
    public JPARole toJPA(Role model) {
        de.kaiserpfalzedv.iam.access.jpa.roles.RoleBuilder builder
                = new de.kaiserpfalzedv.iam.access.jpa.roles.RoleBuilder();

        builder
                .withDisplayName(model.getDisplayName())
                .withFullName(model.getFullName())
                .withTenant(model.getTenant());


    }

    @Override
    public Role toModel(JPARole jpa) {
        return new RoleBuilder().withRole(jpa).build();
    }

    @Override
    public Optional<Role> toModel(Optional<JPARole> jpa) {
        if (jpa.isPresent()) {
            return Optional.of(new RoleBuilder().withRole(jpa.get()).build());
        }

        return Optional.empty();
    }
}
