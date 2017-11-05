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

import de.kaiserpfalzedv.commons.jpa.AbstractBaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;

/**
 * The implementation of a CRUD repository for the entitlement.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 1.0.0
 */
@ApplicationScoped
public class JPAEntitlementRepositoryImpl extends AbstractBaseRepository<JPAEntitlement> implements JPAEntitlementRepository {
    private static final Logger LOG = LoggerFactory.getLogger(JPAEntitlementRepositoryImpl.class);

    @PersistenceContext(unitName = "ACCESS")
    private EntityManager em;

    public JPAEntitlementRepositoryImpl() {
        this(null);
    }

    @Inject
    public JPAEntitlementRepositoryImpl(
            @NotNull final EntityManager em
    ) {
        super(JPAEntitlementRepositoryImpl.class, "Entitlement", em);
    }


    @Override
    protected void updateData(@NotNull JPAEntitlement old, @NotNull final JPAEntitlement data) {
        old.update(data);
    }
}
