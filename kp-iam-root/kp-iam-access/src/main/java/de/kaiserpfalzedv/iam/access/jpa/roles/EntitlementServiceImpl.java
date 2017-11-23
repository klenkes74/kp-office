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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.commons.api.data.BaseService;
import de.kaiserpfalzedv.commons.api.data.ObjectDoesNotExistException;
import de.kaiserpfalzedv.commons.api.data.ObjectExistsException;
import de.kaiserpfalzedv.commons.api.data.paging.Pageable;
import de.kaiserpfalzedv.commons.api.data.paging.PageableBuilder;
import de.kaiserpfalzedv.commons.api.data.paging.PagedListBuilder;
import de.kaiserpfalzedv.commons.api.data.paging.PagedListable;
import de.kaiserpfalzedv.commons.api.data.query.Predicate;
import de.kaiserpfalzedv.iam.access.api.roles.Entitlement;
import de.kaiserpfalzedv.iam.access.api.roles.EntitlementPredicate;
import de.kaiserpfalzedv.iam.access.client.roles.EntitlementBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-11-19
 */
@Dependent
public class EntitlementServiceImpl implements BaseService<Entitlement> {
    private static final Logger LOG = LoggerFactory.getLogger(EntitlementServiceImpl.class);


    private JPAEntitlementRepository repository;


    @Inject
    public EntitlementServiceImpl(
            @NotNull final JPAEntitlementRepository repository
    ) {
        this.repository = repository;
    }


    @Override
    public Entitlement create(Entitlement data) throws ObjectExistsException {
        LOG.trace("Saving: {}", data);
        JPAEntitlement jpa = new JPAEntitlementBuilder().withEntitlement(data).build();

        jpa = repository.create(jpa);

        LOG.info("Saved entitlement: {}", jpa);
        return new EntitlementBuilder().withEntitlement(jpa).build();
    }

    @Override
    public Entitlement retrieve(UUID id) throws ObjectDoesNotExistException {
        try {
            return retrieve(
                    EntitlementPredicate.id().isEqualTo(id),
                    new PageableBuilder().build()
            ).getEntries().get(0);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ObjectDoesNotExistException(Entitlement.class, id);
        }
    }

    @Override
    public PagedListable<Entitlement> retrieve(
            @NotNull final Predicate<Entitlement> predicate,
            @NotNull final Pageable page
    ) {
        LOG.debug("Loading data by predicate: predicate={}, page{}", predicate, page);
        PagedListable<JPAEntitlement> jpa = repository.retrieve(predicate, page);
        List<Entitlement> data = new ArrayList<>((int) jpa.getPage().getSize());
        data.addAll(jpa.getEntries());

        PagedListable<Entitlement> result = new PagedListBuilder<Entitlement>()
                .withData(data)
                .withPageable(jpa.getPage())
                .build();


        LOG.trace("Retrieved data page ({}): {}", jpa.getPage(), jpa.getEntries());
        return result;
    }

    @Override
    public Entitlement update(Entitlement data) throws ObjectExistsException, ObjectDoesNotExistException {
        Optional<JPAEntitlement> jpa = repository.retrieve(data.getId());

        if (jpa.isPresent()) {
            JPAEntitlement old = jpa.get();
            old.setName(data.getName());
            old.setDisplayName(data.getDisplayName());
            old.setFullName(data.getFullName());
            old.setDescriptionKey(data.getDescriptionKey());

            repository.update(old);

            return old;
        } else {
            return create(data);
        }

    }

    @Override
    public void delete(UUID id) {
        repository.delete(id);
    }
}
