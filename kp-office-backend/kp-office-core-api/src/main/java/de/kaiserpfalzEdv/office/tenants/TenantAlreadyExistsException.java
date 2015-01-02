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

package de.kaiserpfalzEdv.office.tenants;

import de.kaiserpfalzEdv.office.core.EntityAlreadyExistsException;

/**
 * @author klenkes
 * @since 2014Q
 */
public class TenantAlreadyExistsException extends EntityAlreadyExistsException {
    private static final long serialVersionUID = -1529571072582385546L;

    private Tenant tenant;

    public TenantAlreadyExistsException(final Tenant tenant, final String message) {
        super(tenant);
    }

    public TenantAlreadyExistsException(final Tenant tenant, final Throwable cause) {
        super(tenant, cause);
    }


    public Tenant getTenant() {
        return (Tenant) super.getEntity();
    }
}
