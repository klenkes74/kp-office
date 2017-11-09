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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.commons.api.data.ObjectExistsException;
import de.kaiserpfalzedv.commons.api.data.base.DataUpdater;
import de.kaiserpfalzedv.commons.api.data.paging.Pageable;
import de.kaiserpfalzedv.commons.api.data.paging.PageableBuilder;
import de.kaiserpfalzedv.commons.api.data.paging.PagedListBuilder;
import de.kaiserpfalzedv.commons.api.data.paging.PagedListable;
import de.kaiserpfalzedv.commons.api.data.query.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The implementation of a CRUD repository for the entitlement.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings("unchecked")
public class JPABaseRepository<T extends JPAAbstractIdentifiable> {
    private static final Logger LOG = LoggerFactory.getLogger(JPABaseRepository.class);

    private Class<?> clasz;
    private String entityName;

    public JPABaseRepository(
            @NotNull final Class<?> clasz,
            @NotNull final String entityName
    ) {
        this.clasz = clasz;
        this.entityName = entityName;
    }

    public PagedListable<T> retrieve(
            @NotNull final EntityManager em,
            @NotNull final Predicate<T> predicate,
            @NotNull final Pageable page
    ) {
        // TODO 2017-11-04 rlichti Implement the predicate search.
        LOG.error(
                "Predicate based search for '{}' not yet implemented. All data base rows will be returned!",
                clasz.getSimpleName()
        );

        return retrieve(em, page);
    }

    public PagedListable<T> retrieve(@NotNull final EntityManager em, @NotNull final Pageable page) {
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

        PagedListable<T> result = new PagedListBuilder<T>().withData(data).withPageable(resultPage).build();

        LOG.trace("Loaded page of type '{}': {}", clasz.getSimpleName(), result);
        return result;
    }

    public void update(
            @NotNull final EntityManager em,
            @NotNull final T entity,
            @NotNull final DataUpdater<T> updater
    ) {
        Optional<T> orig = retrieve(em, entity.getId());

        T data;
        if (orig.isPresent()) {
            data = orig.get();
        } else {
            try {
                data = create(em, entity);
                LOG.debug("Created new entity of type '{}' during update: {}", clasz.getSimpleName(), data);
            } catch (ObjectExistsException e) {
                LOG.warn("Duplicate object of type '{}', but first it has not been found: {}",
                         clasz.getSimpleName(), e.getObjectId()
                );
                data = (T) e.getExistingObject();
            }
        }

        updater.update(data, entity);

        em.merge(data);
        LOG.info("Updated entity of type '{}': {}", clasz.getSimpleName(), data);
    }

    public Optional<T> retrieve(@NotNull final EntityManager em, @NotNull final UUID id) {
        T result = (T) em.find(clasz, id.toString());

        LOG.trace("Loaded entity of type '{}': {}", clasz.getSimpleName(), result != null ? result : "./.");
        return Optional.ofNullable(result);
    }

    public T create(@NotNull final EntityManager em, @NotNull final T entity) throws ObjectExistsException {
        try {
            em.persist(entity);
        } catch (PersistenceException e) {
            Optional<T> result = retrieve(em, entity.getId());

            if (result.isPresent()) {
                throw new ObjectExistsException(clasz, entity.getId(), result.get());
            } else {
                throw new IllegalStateException("While persisting, the object has been there, then when trying to " +
                                                        "retrieve it, it wasn't. Won't work that way!");
            }
        }

        //noinspection ConstantConditions
        return retrieve(em, entity.getId()).get();
    }

    public void delete(@NotNull final EntityManager em, @NotNull final UUID id) {
        Optional<T> data = retrieve(em, id);

        data.ifPresent(e -> delete(em, e));
    }


    public void delete(@NotNull final EntityManager em, @NotNull final T entity) {
        LOG.info("Removing entity of type '{}': {}", clasz.getSimpleName(), entity);

        em.remove(entity);
    }
}