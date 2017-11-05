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

package de.kaiserpfalzedv.commons.jpa;

import de.kaiserpfalzedv.commons.api.data.ObjectExistsException;
import de.kaiserpfalzedv.commons.api.data.Pageable;
import de.kaiserpfalzedv.commons.api.data.PagedListable;
import de.kaiserpfalzedv.commons.api.data.Predicate;
import de.kaiserpfalzedv.commons.api.data.impl.PageableBuilder;
import de.kaiserpfalzedv.commons.api.data.impl.PagedListBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The implementation of a CRUD repository for the entitlement.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings("unchecked")
public abstract class AbstractBaseRepository<T extends JPAAbstractIdentifiable> {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractBaseRepository.class);

    private Class<?> clasz;
    private String entityName;
    private EntityManager em;

    public AbstractBaseRepository(
            @NotNull final Class<?> clasz,
            @NotNull final String entityName,
            @NotNull final EntityManager em
    ) {
        this.clasz = clasz;
        this.entityName = entityName;
        this.em = em;
    }

    public T create(@NotNull final T entitlement) throws ObjectExistsException {
        try {
            em.persist(entitlement);
        } catch (EntityExistsException e) {
            Optional<T> result = retrieve(entitlement.getId());

            if (result.isPresent()) {
                throw new ObjectExistsException(clasz, entitlement.getId(), result.get());
            } else {
                throw new IllegalStateException("While persisting, the object has been there, then when trying to " +
                        "retrieve it, it wasn't. Won't work that way!");
            }
        }

        //noinspection ConstantConditions
        return retrieve(entitlement.getId()).get();
    }


    public Optional<T> retrieve(@NotNull final UUID id) {
        T result = (T) em.find(clasz, id.toString());

        return Optional.ofNullable(result);
    }


    public PagedListable<T> retrieve(@NotNull final Pageable page) {
        List<T> data = (List<T>) em
                .createNamedQuery(entityName + ".fetch-all", clasz)
                .setFirstResult(page.getFirstResult())
                .setMaxResults(page.getMaxResults())
                .getResultList();

        Pageable resultPage;
        long totalCount = page.getTotalCount();
        if (totalCount == 0) {
            LOG.debug("Need to count the total results for Entitlement full list.");

            totalCount = em.createNamedQuery(entityName + ".fetch-all.count", Long.class)
                    .getSingleResult();

            resultPage = new PageableBuilder()
                    .withPaging(page)
                    .withTotalCount(totalCount)
                    .withTotalPages(0)
                    .build();
        } else {
            resultPage = new PageableBuilder().withPaging(page).build();
        }

        return new PagedListBuilder<T>().withData(data).withPageable(resultPage).build();
    }


    public PagedListable<T> retrieve(@NotNull final Predicate<T> predicate,
                                     @NotNull final Pageable page) {
        // TODO 2017-11-04 rlichti Implement the predicate search.
        LOG.error("Predicate based search for '{}' not yet implemented. All data base rows will be returned!",
                clasz.getSimpleName());

        return retrieve(page);
    }


    public void update(@NotNull final T entitlement) {
        Optional<T> orig = retrieve(entitlement.getId());

        T data;
        if (orig.isPresent()) {
            data = orig.get();
        } else {
            try {
                data = create(entitlement);
                LOG.debug("Created new entitlement during update: {}", data);
            } catch (ObjectExistsException e) {
                LOG.warn("Duplicate object, but first it has not been found: {}", e.getObjectId());
                data = (T) e.getExistingObject();
            }
        }

        updateData(data, entitlement);

        em.merge(data);
        LOG.info("Updated entitlement: {}", data);
    }

    protected abstract void updateData(@NotNull T old, @NotNull final T data);


    public void delete(@NotNull final UUID id) {
        Optional<T> entitlement = retrieve(id);

        entitlement.ifPresent(this::delete);
    }


    public void delete(@NotNull final T entitlement) {
        LOG.info("Removing entitlement: {}", entitlement);

        em.remove(entitlement);
    }
}
