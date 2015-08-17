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

import de.kaiserpfalzEdv.office.commons.data.Entity;

/**
 * @author klenkes
 * @since 2014Q
 */
public class OnlyInvalidTenantFoundException extends NoSuchTenantException {
    private static final long serialVersionUID = 6383423541141514937L;

    private static final Class<? extends Entity> clasz = Tenant.class;

    private Tenant invalidTenant;

    public OnlyInvalidTenantFoundException(final Tenant tenant) {
        super("Only invalid tenant with id '" + tenant.getId().toString() + "' found!");

        setInvalidTenant(tenant);
    }


    public Tenant getInvalidTenant() {
        return invalidTenant;
    }

    private void setInvalidTenant(final Tenant tenant) {
        this.invalidTenant = tenant;
    }
}
