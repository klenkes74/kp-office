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

package de.kaiserpfalzEdv.office.core.tenants;

import de.kaiserpfalzEdv.commons.paging.Page;
import de.kaiserpfalzEdv.commons.paging.Pageable;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * The tenant service
 *
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
public interface TenantService {
    /**
     * Saves the tenant to the database.
     *
     * @param tenant The tenant to be saved.
     * @throws TenantAlreadyExistsException If there is already a tenant with the id or same display number.
     */
    public void create(@NotNull final Tenant tenant) throws TenantAlreadyExistsException;


    /**
     * Retrieves the tenant by internal UUID.
     *
     * @param id The UUID of the tenant to be retrieved.
     * @return The tenant with the given id.
     * @throws NoSuchTenantException There is no tenant with the given ID.
     * @throws OnlyInvalidTenantFoundException There is no valid tenant with the given ID.
     */
    @SuppressWarnings("DuplicateThrows")
    public Tenant retrieve(@NotNull final UUID id) throws NoSuchTenantException, OnlyInvalidTenantFoundException;


    /**
     * Retrieves the tenant by the unique display number.
     *  
     * @param displayNumber The unique displayable number of the tenant to be retrieved.
     * @return The tenant with the given display number.
     * @throws NoSuchTenantException There is no tenant with the given display number.
     * @throws OnlyInvalidTenantFoundException There is no valid tenant with the given display number.
     */
    @SuppressWarnings("DuplicateThrows")
    public Tenant retrieve(@NotNull final String displayNumber) throws NoSuchTenantException, OnlyInvalidTenantFoundException;


    /**
     * Updates the data of the tenant with the given id.
     *
     * @param id The id of the tenant to be updated.
     * @param tenant The updated tenant data.
     * @throws NoSuchTenantException There is no tenant with the given ID.
     */
    public void update(@NotNull final UUID id, @NotNull final Tenant tenant) throws NoSuchTenantException;


    /**
     * Deletes the tenant from the database.
     * @param tenant The tenant to be deleted.
     */
    public void delete(@NotNull final Tenant tenant);

    /**
     * Deletes the tenant from the database.
     * @param id The UUID of the tenant to be deleted.
     */
    public void delete(@NotNull final UUID id);


    /**
     * Retrieves the given page of all tenants.
     * @param pageable The page request defining which tenants to display.
     * @return the given page of tenants.
     */
    public Page<Tenant> listTenants(Pageable pageable);


    /**
     * Retrieves the given page of all tenants.
     * @param tenant The main tenant to retrieve all children for.
     * @param pageable The page request defining which tenants to display.
     * @return the given page of tenants.
     */
    public Page<Tenant> listTenants(Tenant tenant, Pageable pageable);


    /**
     * Retrieves the given page of all tenants.
     * @param tenant The UUID of the main tenant to retrieve all children for.
     * @param pageable The page request defining which tenants to display.
     * @return the given page of tenants.
     */
    public Page<Tenant> listTenants(UUID tenant, Pageable pageable);
}
