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

import de.kaiserpfalzEdv.office.commons.Entity;
import de.kaiserpfalzEdv.office.core.NoSuchEntityException;

import java.util.UUID;

/**
 * @author klenkes
 * @since 2014Q
 */
public class NoSuchTenantException extends NoSuchEntityException {
    private static final long serialVersionUID = 1519887477271722791L;

    private static final Class<? extends Entity> clasz = Tenant.class;


    public NoSuchTenantException(final UUID tenantId) {
        super(clasz, "No valid tenant with id '" + tenantId.toString() + "' found in store.");
    }

    public NoSuchTenantException(final String message) {
        super(clasz, message);
    }

    public NoSuchTenantException(final Throwable cause) {
        super(clasz, cause);
    }

    public NoSuchTenantException(final String message, final Throwable cause) {
        super(clasz, message, cause);
    }
}
