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

package de.kaiserpfalzedv.office.common.ejb.multitenancy;

import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import de.kaiserpfalzedv.office.common.api.multitenancy.Tenant;
import de.kaiserpfalzedv.office.common.api.multitenancy.TenantHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The tenant holder will keep the tenant id of the current execution thread. If the thread spawns other threads, the
 * tenant id will be inherited.
 *
 * This class also provides the tenant id via CDI. Every UUID annotated with {@link Tenant} can be injected. But if there
 * is no tenant id saved on the thread executing such an annotated method, an {@link ThereIsNoTenantException} will be
 * thrown.
 *
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-13
 * @see Produces
 * @see ThereIsNoTenantException
 * @see Tenant
 */
@ApplicationScoped
public class TenantHolderImpl implements TenantHolder, Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(TenantHolderImpl.class);

    private static final InheritableThreadLocal<UUID> tenant = new InheritableThreadLocal<>();

    /**
     * A CDI producer that inserts the tenant needed for other methods or throws an exception if ther is no tenant to
     * set to the call.
     *
     * @return the tenant id of the current thread.
     * @throws ThereIsNoTenantException If there is no tenant assigned to the thread this exception will signal it.
     */
    @Produces
    @Tenant
    public UUID produceTenant() {
        return getTenant().orElseThrow(ThereIsNoTenantException::new);
    }

    @Override
    public Optional<UUID> getTenant() {
        UUID current = tenant.get();
        LOG.trace("Returning tenant: {}", current);
        return Optional.ofNullable(current);
    }

    @Override
    public void setTenant(UUID currentTenant) {
        LOG.trace("Storing tenant: {}", currentTenant);
        tenant.set(currentTenant);
    }

    public void clearTenant() {
        LOG.trace("Removing tenant: {}", tenant.get());
        tenant.remove();
    }
}
