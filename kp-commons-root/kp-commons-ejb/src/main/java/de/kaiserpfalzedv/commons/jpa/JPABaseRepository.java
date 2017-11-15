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
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.commons.api.data.ObjectExistsException;
import de.kaiserpfalzedv.commons.api.data.base.Identifiable;
import de.kaiserpfalzedv.commons.api.data.paging.Pageable;
import de.kaiserpfalzedv.commons.api.data.paging.PageableBuilder;
import de.kaiserpfalzedv.commons.api.data.paging.PagedListBuilder;
import de.kaiserpfalzedv.commons.api.data.paging.PagedListable;
import de.kaiserpfalzedv.commons.api.data.query.Predicate;
import de.kaiserpfalzedv.commons.api.data.query.PredicateParameterGenerator;
import de.kaiserpfalzedv.commons.api.data.query.PredicateQueryGenerator;
import de.kaiserpfalzedv.commons.api.data.query.QueryParameter;
import de.kaiserpfalzedv.commons.impl.data.query.PredicateToParameterParser;
import de.kaiserpfalzedv.commons.impl.data.query.PredicateToQueryParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The implementation of a CRUD repository for any class extending {@link JPAAbstractIdentifiable}.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings("unchecked")
public class JPABaseRepository<T extends JPAAbstractIdentifiable, P extends Identifiable> {
    private static final Logger LOG = LoggerFactory.getLogger(JPABaseRepository.class);

    private Class<T> clasz;
    private String entityName;

    public JPABaseRepository(
            @NotNull final Class<T> clasz,
            @NotNull final String entityName
    ) {
        this.clasz = clasz;
        this.entityName = entityName;
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

    public Optional<T> retrieve(@NotNull final EntityManager em, @NotNull final UUID id) {
        T result = em.find(clasz, id.toString());

        LOG.trace("Loaded entity of type '{}': {}", clasz.getSimpleName(), result != null ? result : "./.");
        return Optional.ofNullable(result);
    }

    public PagedListable<T> retrieve(
            @NotNull final EntityManager em,
            @NotNull final Predicate<P> predicate,
            @NotNull final Pageable page
    ) {
        PredicateQueryGenerator<P> queryParser = new PredicateToQueryParser<>();
        PredicateParameterGenerator<P> parameterParser = new PredicateToParameterParser<>();

        String where = queryParser.generateQuery(predicate);
        String queryString = "select t from " + entityName + " t where " + where;

        TypedQuery<T> query = em.createQuery(queryString, clasz);

        List<QueryParameter> parameters = parameterParser.generateParameters(predicate);
        for (QueryParameter p : parameters) {
            query.setParameter(p.getName(), p.getValue());
        }

        List<T> data = query.setFirstResult(page.getFirstResult())
                            .setMaxResults(page.getMaxResults())
                            .getResultList();

        PagedListable<T> result = new PagedListBuilder<T>()
                .withData(data)
                .withPageable(page)
                .build();

        LOG.trace("Loaded page ({}) of type '{}': {}", page, clasz.getSimpleName(), result);
        return result;
    }

    public PagedListable<T> retrieve(@NotNull final EntityManager em, @NotNull final Pageable page) {
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

        List<T> data = em
                .createNamedQuery(entityName + ".fetch-all", clasz)
                .setFirstResult(page.getFirstResult())
                .setMaxResults(page.getMaxResults())
                .getResultList();

        PagedListable<T> result = new PagedListBuilder<T>()
                .withData(data)
                .withPageable(resultPage)
                .build();

        LOG.trace("Loaded page ({}) of type '{}': {}", resultPage, clasz.getSimpleName(), result);
        return result;
    }

    public void update(
            @NotNull final EntityManager em,
            @NotNull final T entity
    ) {
        em.merge(entity);
        LOG.info("Updated entity of type '{}': {}", clasz.getSimpleName(), entity);
    }

    public void delete(@NotNull final EntityManager em, @NotNull final UUID id) {
        Optional<T> data = retrieve(em, id);

        data.ifPresent(e -> delete(em, e));
    }

    public void delete(@NotNull final EntityManager em, @NotNull final T entity) {
        em.remove(entity);
        LOG.info("Deleted entity of type '{}': {}", clasz.getSimpleName(), entity);
    }
}
