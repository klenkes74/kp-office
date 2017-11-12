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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.commons.api.data.ObjectExistsException;
import de.kaiserpfalzedv.commons.api.data.base.DataUpdater;
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
 * The implementation of a CRUD repository for the entitlement.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings("unchecked")
public class JPABaseRepository<T extends Identifiable, J extends JPAAbstractIdentifiable> {
    private static final Logger LOG = LoggerFactory.getLogger(JPABaseRepository.class);

    private Class<J> clasz;
    private String entityName;

    private JPAConverter<T, J> converter;

    public JPABaseRepository(
            @NotNull final Class<J> clasz,
            @NotNull final String entityName,
            @NotNull final JPAConverter<T, J> converter
    ) {
        this.clasz = clasz;
        this.entityName = entityName;
        this.converter = converter;
    }

    public T create(@NotNull final EntityManager em, @NotNull final T entity) throws ObjectExistsException {
        return converter.toModel(createJPA(em, converter.toJPA(entity)));
    }

    private J createJPA(@NotNull final EntityManager em, @NotNull final J entity) throws ObjectExistsException {
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
        return retrieveJPA(em, entity.getId()).get();
    }

    public Optional<T> retrieve(@NotNull final EntityManager em, @NotNull final UUID id) {
        return converter.toModel(retrieveJPA(em, id));
    }

    private Optional<J> retrieveJPA(@NotNull final EntityManager em, @NotNull final UUID id) {
        J result = em.find(clasz, id.toString());

        LOG.trace("Loaded entity of type '{}': {}", clasz.getSimpleName(), result != null ? result : "./.");
        return Optional.ofNullable(result);
    }

    public PagedListable<T> retrieve(
            @NotNull final EntityManager em,
            @NotNull final Predicate<T> predicate,
            @NotNull final Pageable page
    ) {
        PredicateQueryGenerator<T> queryParser = new PredicateToQueryParser<>();
        PredicateParameterGenerator<T> parameterParser = new PredicateToParameterParser<>();

        String where = queryParser.generateQuery(predicate);
        String queryString = "select t from " + entityName + " t where " + where;

        TypedQuery<J> query = em.createQuery(queryString, clasz);

        List<QueryParameter> parameters = parameterParser.generateParameters(predicate);
        for (QueryParameter p : parameters) {
            query.setParameter(p.getName(), p.getValue());
        }

        List<J> result = query.setFirstResult(page.getFirstResult())
                              .setMaxResults(page.getMaxResults())
                              .getResultList();

        ArrayList<T> converted = new ArrayList<>(result.size());
        result.forEach(d -> converted.add(converter.toModel(d)));

        return new PagedListBuilder<T>()
                .withData(converted)
                .withPageable(page)
                .build();
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

        List<J> data = em
                .createNamedQuery(entityName + ".fetch-all", clasz)
                .setFirstResult(page.getFirstResult())
                .setMaxResults(page.getMaxResults())
                .getResultList();

        ArrayList<T> converted = new ArrayList<>(data.size());
        data.forEach(d -> converted.add(converter.toModel(d)));
        PagedListable<T> result = new PagedListBuilder<T>()
                .withData(converted)
                .withPageable(resultPage)
                .build();

        LOG.trace("Loaded page of type '{}': {}", clasz.getSimpleName(), result);
        return result;
    }

    public void update(
            @NotNull final EntityManager em,
            @NotNull final T entity,
            @NotNull final DataUpdater<J> updater
    ) {
        updateJPA(em, converter.toJPA(entity), updater);
    }

    private void updateJPA(
            @NotNull final EntityManager em,
            @NotNull final J entity,
            @NotNull final DataUpdater<J> updater
    ) {
        Optional<J> orig = retrieveJPA(em, entity.getId());

        J data;
        if (orig.isPresent()) {
            data = orig.get();
        } else {
            try {
                data = createJPA(em, entity);
                LOG.debug("Created new entity of type '{}' during update: {}", clasz.getSimpleName(), data);
            } catch (ObjectExistsException e) {
                LOG.warn("Duplicate object of type '{}', but first it has not been found: {}",
                         clasz.getSimpleName(), e.getObjectId()
                );
                data = (J) e.getExistingObject();
            }
        }

        updater.update(data, entity);

        em.merge(data);
        LOG.info("Updated entity of type '{}': {}", clasz.getSimpleName(), data);
    }

    public void delete(@NotNull final EntityManager em, @NotNull final UUID id) {
        Optional<J> data = retrieveJPA(em, id);

        data.ifPresent(e -> deleteJPA(em, e));
    }

    private void deleteJPA(@NotNull final EntityManager em, @NotNull final J entity) {
        LOG.info("Removing entity of type '{}': {}", clasz.getSimpleName(), entity);

        em.remove(entity);
    }

    public void delete(@NotNull final EntityManager em, @NotNull final T entity) {
        deleteJPA(em, converter.toJPA(entity));
    }
}
