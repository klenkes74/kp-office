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

import de.kaiserpfalzedv.commons.api.data.ObjectExistsException;
import de.kaiserpfalzedv.commons.api.data.Pageable;
import de.kaiserpfalzedv.commons.api.data.PagedListable;
import de.kaiserpfalzedv.commons.api.data.Predicate;
import de.kaiserpfalzedv.commons.api.data.impl.PageableBuilder;
import de.kaiserpfalzedv.commons.api.data.impl.PagedListBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 1.0.0
 */
@ApplicationScoped
public class JPAEntitlementRepositoryImpl implements JPAEntitlementRepository {
    private static final Logger LOG = LoggerFactory.getLogger(JPAEntitlementRepositoryImpl.class);

    @PersistenceContext(unitName = "ACCESS")
    private EntityManager em;


    @Override
    public JPAEntitlement create(@NotNull final JPAEntitlement entitlement) throws ObjectExistsException {
        try {
            em.persist(entitlement);
        } catch (EntityExistsException e) {
            Optional<JPAEntitlement> result = retrieve(entitlement.getId());

            throw new ObjectExistsException(JPAEntitlement.class, entitlement.getId(), result.get());
        }

        return retrieve(entitlement.getId()).get();
    }

    @Override
    public Optional<JPAEntitlement> retrieve(@NotNull final UUID id) {
        JPAEntitlement result = em.find(JPAEntitlement.class, id.toString());

        return Optional.ofNullable(result);
    }

    @Override
    public PagedListable<JPAEntitlement> retrieve(@NotNull final Pageable page) {
        List<JPAEntitlement> data = em
                .createNamedQuery("Entitlement.fetch-all", JPAEntitlement.class)
                .setFirstResult(page.getFirstResult())
                .setMaxResults(page.getMaxResults())
                .getResultList();

        Pageable resultPage;
        long totalCount = page.getTotalCount();
        if (totalCount == 0) {
            LOG.debug("Need to count the total results for Entitlement full list.");

            totalCount = em.createNamedQuery("Entitlement.fetch-all.count", Long.class)
                    .getSingleResult();

            resultPage = new PageableBuilder()
                    .withPaging(page)
                    .withTotalCount(totalCount)
                    .withTotalPages(0)
                    .build();
        } else {
            resultPage = new PageableBuilder().withPaging(page).build();
        }

        return new PagedListBuilder<JPAEntitlement>().withData(data).withPageable(resultPage).build();
    }

    @Override
    public PagedListable<JPAEntitlement> retrieve(@NotNull final Predicate<JPAEntitlement> predicate,
                                                  @NotNull final Pageable page) {
        // TODO 2017-11-04 rlichti Implement the predicate search.
        LOG.error("Predicate based search for {} not yet implemented. All data base rows will be returned!",
                JPAEntitlement.class.getSimpleName());

        return retrieve(page);
    }

    @Override
    public void update(@NotNull final JPAEntitlement entitlement) {
        Optional<JPAEntitlement> orig = retrieve(entitlement.getId());

        JPAEntitlement data;
        if (orig.isPresent()) {
            data = orig.get();
        } else {
            try {
                data = create(entitlement);
                LOG.debug("Created new entitlement during update: {}", data);
            } catch (ObjectExistsException e) {
                LOG.warn("Duplicate object, but first it has not been found: {}", e.getObjectId());
                data = (JPAEntitlement) e.getExistingObject();
            }
        }

        data.update(entitlement);
        em.merge(data);
        LOG.info("Updated entitlement: {}", data);
    }

    @Override
    public void delete(@NotNull final UUID id) {
        Optional<JPAEntitlement> entitlement = retrieve(id);

        entitlement.ifPresent(this::delete);

    }

    @Override
    public void delete(@NotNull final JPAEntitlement entitlement) {
        LOG.info("Removing entitlement: {}", entitlement);

        em.remove(entitlement);
    }
}
