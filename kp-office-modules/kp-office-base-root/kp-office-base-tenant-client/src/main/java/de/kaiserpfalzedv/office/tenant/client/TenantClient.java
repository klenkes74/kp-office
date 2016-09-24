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

package de.kaiserpfalzedv.office.tenant.client;

import de.kaiserpfalzedv.office.common.cdi.Implementation;
import de.kaiserpfalzedv.office.tenant.Tenant;
import de.kaiserpfalzedv.office.tenant.TenantDoesNotExistException;
import de.kaiserpfalzedv.office.tenant.TenantExistsException;
import de.kaiserpfalzedv.office.tenant.TenantService;

import java.util.Collection;
import java.util.UUID;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2016-09-20
 */
@Implementation
public class TenantClient implements TenantService {

    @Override
    public Tenant createTenant(Tenant data) throws TenantExistsException {
        return null;
    }

    @Override
    public Tenant retrieveTenant(UUID id) throws TenantDoesNotExistException {
        return null;
    }

    @Override
    public Collection<Tenant> retrieveTenants() {
        return null;
    }

    @Override
    public Tenant updateTenant(Tenant data) throws TenantDoesNotExistException {
        return null;
    }

    @Override
    public void deleteTenant(UUID id) {

    }
}
