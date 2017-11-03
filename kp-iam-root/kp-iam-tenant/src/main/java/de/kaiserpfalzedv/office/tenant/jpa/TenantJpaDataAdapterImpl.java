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

package de.kaiserpfalzedv.office.tenant.jpa;

import de.kaiserpfalzedv.commons.api.init.InitializationException;
import de.kaiserpfalzedv.office.tenant.adapter.data.TenantDataAdapter;
import de.kaiserpfalzedv.office.tenant.api.Tenant;
import de.kaiserpfalzedv.office.tenant.api.TenantDoesNotExistException;
import de.kaiserpfalzedv.office.tenant.api.TenantExistsException;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

import static javax.persistence.LockModeType.PESSIMISTIC_WRITE;
import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;

/**
 * The JPA implementation of the data adapter to the tenant service.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-10
 */
@ApplicationScoped
public class TenantJpaDataAdapterImpl implements TenantDataAdapter {
    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unused") // Used for CDI
    public TenantJpaDataAdapterImpl() {}

    TenantJpaDataAdapterImpl(@NotNull final EntityManager em) {
        this.em = em;
    }


    @Override
    @Transactional(REQUIRED)
    public Tenant create(final Tenant data) throws TenantExistsException {
        try {
            Tenant create = new JPATenant(data);

            em.persist(create);

            return create;
        } catch (PersistenceException e) {
            try {
                throw new TenantExistsException(retrieve(data.getId()));
            } catch (TenantDoesNotExistException e1) {
                throw new TenantExistsException(data);
            }
        }
    }

    @Override
    @Transactional(SUPPORTS)
    public Tenant retrieve(final UUID id) throws TenantDoesNotExistException {
        try {
            Tenant result = em.find(JPATenant.class, id.toString());

            em.detach(result);

            return result;
        } catch (NoResultException | QueryTimeoutException e) {
            throw new TenantDoesNotExistException(id);
        }
    }

    @Override
    @Transactional(SUPPORTS)
    public Set<Tenant> retrieve() {
        HashSet<Tenant> result = new HashSet<>();

        em.createNamedQuery("fetch-all", JPATenant.class).getResultList().forEach(
                e -> {
                    em.detach(e);

                    result.add(e);
                }
        );

        return result;
    }

    @Override
    @Transactional(REQUIRED)
    public Tenant update(final Tenant data) throws TenantExistsException, TenantDoesNotExistException {
        try {
            JPATenant db;
            try {
                db = em.find(JPATenant.class, data.getId().toString(), PESSIMISTIC_WRITE);
            } catch (NoResultException | QueryTimeoutException e) {
                throw new TenantDoesNotExistException(data.getId());
            }

            db.update(data);

            em.merge(db);
            em.detach(db);

            return db;
        } catch (PersistenceException e) {
            throw new TenantExistsException(data);
        }
    }

    @Override
    @Transactional(REQUIRED)
    public void delete(final UUID id) {
        JPATenant db = em.find(JPATenant.class, id.toString());

        em.remove(db);
    }

    @Override
    public Tenant retrieve(String businessKey) throws TenantDoesNotExistException {
        try {
            JPATenant result = em.createNamedQuery("find-by-key", JPATenant.class)
                                 .getSingleResult();

            em.detach(result);

            return result;
        } catch (NoResultException | NonUniqueResultException | QueryTimeoutException e) {
            throw new TenantDoesNotExistException(businessKey);
        }
    }

    @Override
    public void close() {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.office.tenant.jpa.TenantJpaDataAdapterImpl.close
    }

    @Override
    public void init() throws InitializationException {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.office.tenant.jpa.TenantJpaDataAdapterImpl.init
    }

    @Override
    public void init(Properties properties) throws InitializationException {
        init();
    }
}
