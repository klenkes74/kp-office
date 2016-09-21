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
 *
 */

package de.kaiserpfalzedv.office.tenant.impl;

import java.util.Set;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import de.kaiserpfalzedv.office.tenant.Tenant;
import de.kaiserpfalzedv.office.tenant.TenantDoesNotExistException;
import de.kaiserpfalzedv.office.tenant.TenantExistsException;
import de.kaiserpfalzedv.office.tenant.TenantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The default implementation of a tenant service via JPA.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-10
 */
@ApplicationScoped
public class TenantServiceJpaImpl implements TenantService {
    private static final Logger LOG = LoggerFactory.getLogger(TenantServiceJpaImpl.class);

    private EntityManagerFactory emf;


    @Inject
    public TenantServiceJpaImpl(final EntityManagerFactory emf) {
        this.emf = emf;
    }


    @Override
    public Tenant createTenant(final Tenant data) throws TenantExistsException {
        EntityManager em = emf.createEntityManager();

        em.persist(data);

        Tenant result = (Tenant) em.createQuery("select d from Tenant d where d.id=:id")
                .setParameter("id", data.getId()).getSingleResult();

        em.close();

        return result;
    }

    @Override
    public Tenant retrieveTenant(final UUID id) throws TenantDoesNotExistException {
        throw new UnsupportedOperationException("not yet imlemented");
    }

    @Override
    public Set<Tenant> retrieveTenants() {
        throw new UnsupportedOperationException("not yet imlemented");
    }

    @Override
    public Tenant updateTenant(final Tenant data) throws TenantDoesNotExistException {
        throw new UnsupportedOperationException("not yet imlemented");
    }

    @Override
    public void deleteTenant(final UUID id) {
        throw new UnsupportedOperationException("not yet imlemented");
    }
}
