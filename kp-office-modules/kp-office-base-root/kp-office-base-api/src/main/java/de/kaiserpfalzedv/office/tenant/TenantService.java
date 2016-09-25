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

package de.kaiserpfalzedv.office.tenant;

import java.util.Collection;
import java.util.UUID;

import de.kaiserpfalzedv.office.common.init.Closeable;
import de.kaiserpfalzedv.office.common.init.Initializable;

/**
 * The tenant service manages the tenant data within the system. It's a simple crud service.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-04
 */
public interface TenantService extends Initializable, Closeable {
    /**
     * Saves a tenant to the database.
     *
     * @param data The tenant to be saved.
     *
     * @throws TenantExistsException A tenant with the given UUID, full or display name already exists on the system.
     */
    Tenant createTenant(final Tenant data) throws TenantExistsException;


    /**
     * Retrieves the tenant data for the given ID.
     *
     * @param id The UUID of the tenant to be trieved.
     *
     * @return The tenant with the given UUID.
     *
     * @throws TenantDoesNotExistException If the tenant does not exist.
     */
    Tenant retrieveTenant(final UUID id) throws TenantDoesNotExistException;


    /**
     * Retrieve all tenants from the database.
     *
     * @return A set of tenants available.
     */
    Collection<Tenant> retrieveTenants();


    /**
     * Saves new tenant data to the database.
     *
     * @param data The data to be saved. The data.getID() will define the UUID of the tenant to update.
     *
     * @return The updated tenant.
     *
     * @throws TenantDoesNotExistException If there is no tentant with the UUID to be updated.
     * @throws TenantExistsException If the new data would break a constraint (unique full or display name).
     */
    Tenant updateTenant(final Tenant data) throws TenantDoesNotExistException, TenantExistsException;


    /**
     * Deletes the tenant. If it doesn't existed, then no error is given, since that's exactly the state wanted by the
     * caller.
     *
     * @param id The UUID of the tenant to be removed.
     */
    void deleteTenant(final UUID id);
}
