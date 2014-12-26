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

package de.kaiserpfalzEdv.office.tenants;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public interface TenantClient {
    public Tenant createTenant(@NotNull final UUID id, @NotNull final String name, @NotNull final String number) throws TenantAlreadyExistsException;


    public Tenant retrieveById(@NotNull final UUID id) throws NoSuchTenantException, OnlyInvalidTenantFoundException;

    public Tenant retrieveByName(@NotNull final String name) throws NoSuchTenantException, OnlyInvalidTenantFoundException;

    public Tenant retrieveByNumber(@NotNull final String number) throws NoSuchTenantException, OnlyInvalidTenantFoundException;


    public Tenant updateTenantName(@NotNull final UUID id, @NotNull final String name) throws NoSuchTenantException, OnlyInvalidTenantFoundException, TenantAlreadyExistsException;

    public Tenant updateTenantNumber(@NotNull final UUID id, @NotNull final String number) throws NoSuchTenantException, OnlyInvalidTenantFoundException, TenantAlreadyExistsException;


    public void deleteTenant(@NotNull final UUID id);

    public void deleteTenant(@NotNull final Tenant tenant);
}
