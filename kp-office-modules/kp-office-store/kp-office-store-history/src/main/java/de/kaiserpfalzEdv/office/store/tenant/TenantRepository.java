/*
 * Copyright (c) 2014 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzEdv.office.store.tenant;

import de.kaiserpfalzEdv.office.tenant.Tenant;
import de.kaiserpfalzEdv.office.tenant.TenantDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

/**
 * @author klenkes
 * @since 2014Q
 */
@Stateless
public class TenantRepository {
    private static final Logger LOG = LoggerFactory.getLogger(TenantRepository.class);

    @PersistenceContext
    private EntityManager em;


    public void persist(Tenant e) {
        em.persist(toTenantDTO(e));
    }


    public Tenant findById(UUID id) {
        return em.createNamedQuery("Tenant.ById", TenantDTO.class).setParameter("id", id).getSingleResult();
    }

    public Tenant findByCode(final String code) {
        return em.createNamedQuery("Tenant.ByCode", TenantDTO.class).setParameter("code", code).getSingleResult();
    }


    public List<? extends Tenant> all() {
        return em.createNamedQuery("Tenant.All", TenantDTO.class).getResultList();
    }


    private TenantDTO toTenantDTO(Tenant tenant) {
        try {
            return (TenantDTO) tenant;
        } catch (ClassCastException e) {
            return new TenantDTO(tenant);
        }
    }
}
