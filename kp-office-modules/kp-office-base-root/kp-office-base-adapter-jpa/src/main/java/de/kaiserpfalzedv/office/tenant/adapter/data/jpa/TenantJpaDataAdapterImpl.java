/*
 * Copyright 2016 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzedv.office.tenant.adapter.data.jpa;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.office.tenant.Tenant;
import de.kaiserpfalzedv.office.tenant.TenantDoesNotExistException;
import de.kaiserpfalzedv.office.tenant.TenantExistsException;
import de.kaiserpfalzedv.office.tenant.adapter.data.TenantDataAdapter;
import de.kaiserpfalzedv.office.tenant.impl.TenantBuilder;
import de.kaiserpfalzedv.office.tenant.impl.TenantImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The JPA implementation of the data adapter to the tenant service.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-10
 */
@ApplicationScoped
public class TenantJpaDataAdapterImpl implements TenantDataAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(TenantJpaDataAdapterImpl.class);

    @PersistenceContext
    private EntityManager em;

    public TenantJpaDataAdapterImpl() {}

    public TenantJpaDataAdapterImpl(@NotNull final EntityManager em) {
        this.em = em;
    }


    @Override
    public Tenant create(final Tenant data) throws TenantExistsException {
        em.persist(data);

        return data;
    }

    @Override
    public Tenant retrieve(final UUID id) throws TenantDoesNotExistException {
        TenantImpl result = em.find(TenantImpl.class, id);

        if (result == null) {
            throw new TenantDoesNotExistException(id);
        }

        em.detach(result);

        return result;
    }

    @Override
    public Set<Tenant> retrieve() {
        HashSet<Tenant> result = new HashSet<>();

        //noinspection unchecked,JpaQlInspection
        ((List<TenantImpl>) em.createQuery("SELECT t FROM Tenant t").getResultList()).forEach(
                e -> {
                    em.detach(e);

                    result.add(e);
                }
        );

        return result;
    }

    @Override
    public Tenant update(final Tenant data) throws TenantExistsException, TenantDoesNotExistException {
        try {
            TenantImpl db;

            // Check if tenant really exists ...
            retrieve(data.getId());


            try {
                db = (TenantImpl) data;
            } catch (ClassCastException e) {
                db = (TenantImpl) new TenantBuilder().withTenant(data).build();
            }

            em.merge(db);
            em.detach(db);

            return db;
        } catch (PersistenceException e) {
            throw new TenantExistsException(data);
        }
    }

    @Override
    public Tenant delete(final UUID id) throws TenantDoesNotExistException {
        TenantImpl result = em.find(TenantImpl.class, id);

        if (result == null) {
            throw new TenantDoesNotExistException(id);
        }

        em.remove(result);

        return result;
    }
}
