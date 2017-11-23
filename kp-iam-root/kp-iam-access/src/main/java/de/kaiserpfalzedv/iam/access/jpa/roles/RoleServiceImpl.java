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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.commons.api.cdi.Implementation;
import de.kaiserpfalzedv.commons.api.data.ObjectDoesNotExistException;
import de.kaiserpfalzedv.commons.api.data.ObjectExistsException;
import de.kaiserpfalzedv.commons.api.data.paging.Pageable;
import de.kaiserpfalzedv.commons.api.data.paging.PageableBuilder;
import de.kaiserpfalzedv.commons.api.data.paging.PagedListBuilder;
import de.kaiserpfalzedv.commons.api.data.paging.PagedListable;
import de.kaiserpfalzedv.commons.api.data.query.Predicate;
import de.kaiserpfalzedv.iam.access.api.roles.Entitlement;
import de.kaiserpfalzedv.iam.access.api.roles.EntitlementPredicate;
import de.kaiserpfalzedv.iam.access.api.roles.Role;
import de.kaiserpfalzedv.iam.access.api.roles.RolePredicate;
import de.kaiserpfalzedv.iam.access.api.roles.RoleService;
import de.kaiserpfalzedv.iam.access.client.roles.RoleBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-11-19
 */
@Dependent
@Implementation
public class RoleServiceImpl implements RoleService {
    private static final Logger LOG = LoggerFactory.getLogger(RoleServiceImpl.class);


    private JPARoleRepository repository;
    private JPAEntitlementRepository entitlementRepository;


    @Inject
    public RoleServiceImpl(
            @NotNull final JPARoleRepository repository,
            @NotNull final JPAEntitlementRepository entitlementRepository
    ) {
        this.repository = repository;
        this.entitlementRepository = entitlementRepository;
    }


    @Override
    public Role create(Role data) throws ObjectExistsException {
        LOG.trace("Saving: {}", data);
        JPARole jpa = new JPARoleBuilder()
                .withId(data.getId())
                .withTenant(data.getTenant())
                .withDisplayName(data.getDisplayName())
                .withFullName(data.getFullName())
                .build();

        jpa = repository.create(jpa);

        LOG.info("Saved entitlement: {}", jpa);
        return new RoleBuilder().withRole(jpa).build();
    }

    @Override
    public Role retrieve(UUID id) throws ObjectDoesNotExistException {
        try {
            return retrieve(
                    RolePredicate.id().isEqualTo(id),
                    new PageableBuilder().build()
            ).getEntries().get(0);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ObjectDoesNotExistException(Entitlement.class, id);
        }
    }

    @Override
    public PagedListable<Role> retrieve(@NotNull final Predicate<Role> predicate, @NotNull final Pageable page) {
        LOG.debug("Loading data by predicate: predicate={}, page{}", predicate, page);
        PagedListable<JPARole> jpa = repository.retrieve(predicate, page);
        List<Role> data = new ArrayList<>((int) jpa.getPage().getSize());
        data.addAll(jpa.getEntries());

        PagedListable<Role> result = new PagedListBuilder<Role>()
                .withData(data)
                .withPageable(jpa.getPage())
                .build();


        LOG.trace("Retrieved data page ({}): {}", jpa.getPage(), jpa.getEntries());
        return result;
    }

    @Override
    public Role update(@NotNull final Role data) throws ObjectExistsException, ObjectDoesNotExistException {
        Optional<JPARole> jpa = repository.retrieve(data.getId());

        if (jpa.isPresent()) {
            JPARole old = jpa.get();
            old.setName(data.getName());
            old.setDisplayName(data.getDisplayName());
            old.setFullName(data.getFullName());

            syncRoles(old, data.getDirectRoles());
            syncEntitlements(old, data.getEntitlements());

            repository.update(old);

            return old;
        } else {
            return create(data);
        }

    }

    private void syncRoles(@NotNull JPARole old, @NotNull final Collection<? extends Role> roles) {
        HashSet<UUID> ids = new HashSet<>(roles.size());
        Pageable page = new PageableBuilder()
                .withSize(roles.size())
                .build();
        roles.forEach(r -> ids.add(r.getId()));
        PagedListable<JPARole> jpaRoles = repository.retrieve(RolePredicate.id().in(ids), page);

        old.setRoles(jpaRoles.getEntries());
    }

    private void syncEntitlements(@NotNull JPARole old, @NotNull final Collection<? extends Entitlement> entitlements) {
        HashSet<UUID> ids = new HashSet<>(entitlements.size());
        Pageable page = new PageableBuilder()
                .withSize(entitlements.size())
                .build();
        entitlements.forEach(r -> ids.add(r.getId()));
        PagedListable<JPAEntitlement> jpaEntitlements = entitlementRepository.retrieve(EntitlementPredicate.id()
                                                                                                           .in(ids), page);

        old.setEntitlements(jpaEntitlements.getEntries());
    }

    @Override
    public void delete(UUID id) {
        repository.delete(id);
    }
}
