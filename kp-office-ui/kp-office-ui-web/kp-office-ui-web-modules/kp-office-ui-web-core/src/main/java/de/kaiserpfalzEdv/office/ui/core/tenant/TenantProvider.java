/*
 * Copyright 2015 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzEdv.office.ui.core.tenant;

import de.kaiserpfalzEdv.office.commons.KPO;
import de.kaiserpfalzEdv.office.core.tenants.NoSuchTenantException;
import de.kaiserpfalzEdv.office.core.tenants.NullTenant;
import de.kaiserpfalzEdv.office.core.tenants.Tenant;
import de.kaiserpfalzEdv.office.core.tenants.TenantService;
import de.kaiserpfalzEdv.office.core.tenants.impl.TenantBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;

import java.util.UUID;

import static de.kaiserpfalzEdv.office.commons.KPO.Type.Mock;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 21.08.15 12:14
 */
@Named
public class TenantProvider {
    private static final Logger LOG = LoggerFactory.getLogger(TenantProvider.class);

    static final ThreadLocal<Tenant> currentTenants = new ThreadLocal<Tenant>() {
        @Override
        public Tenant initialValue() {
            //noinspection deprecation
            return new NullTenant();
        }
    };


    private TenantService service;


    @Inject
    public TenantProvider(
            @KPO(Mock) final TenantService service
    ) {
        LOG.trace("***** Created: {}", this);

        this.service = service;
        LOG.trace("*   *   tenant service: {}", this.service);

        LOG.debug("***** Initialized: {}", this);
    }

    @PreDestroy
    public void close() {
        LOG.trace("***** Destroyed: {}", this);
    }


    public Tenant currentTenant() {
        return currentTenants.get();
    }

    public void changeCurrentTenant(final Tenant tenant) {
        currentTenants.set(new TenantBuilder().withTenant(tenant).build());
    }

    public void changeCurrentTenant(final UUID id) throws NoSuchTenantException {
        changeCurrentTenant(service.retrieve(id));
    }
}
