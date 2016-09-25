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

package de.kaiserpfalzedv.office.tenant.adapter.data;

import java.util.Set;
import java.util.UUID;

import de.kaiserpfalzedv.office.tenant.Tenant;
import de.kaiserpfalzedv.office.tenant.TenantDoesNotExistException;
import de.kaiserpfalzedv.office.tenant.TenantExistsException;

/**
 * The definition of the data saving adapter for the tenant data.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-24
 */
public interface TenantDataAdapter {
    /**
     * Creates a new tenant.
     *
     * @param tenant The tenant to save.
     *
     * @return the saved tenant.
     *
     * @throws TenantExistsException
     */
    Tenant create(final Tenant tenant) throws TenantExistsException;

    /**
     * Retrieves an existing tenant.
     *
     * @param id The ID of the tenant to be retrieved.
     *
     * @return The tenant with the given ID.
     *
     * @throws TenantDoesNotExistException If there is no tenant with the ID.
     */
    Tenant retrieve(final UUID id) throws TenantDoesNotExistException;

    /**
     * @return A list of all existing tenants.
     */
    Set<Tenant> retrieve();

    /**
     * Updates the tenant with the current data. The ID of the tenant to update will be taken from the new data. Since
     * the ID is not updateable, it's sure to do so.
     *
     * @param tenant The new tenant data to save.
     *
     * @return The new tenant
     *
     * @throws TenantExistsException       If there is a constraint violation like duplicate display name.
     * @throws TenantDoesNotExistException If there is no tenant with the given ID.
     */
    Tenant update(final Tenant tenant) throws TenantExistsException, TenantDoesNotExistException;

    /**
     * Deletes the tenant.
     *
     * @param id The ID of the tenant to be deleted.
     *
     * @return the old entry.
     *
     * @throws TenantDoesNotExistException If there is no such tenant.
     */
    Tenant delete(final UUID id) throws TenantDoesNotExistException;
}
