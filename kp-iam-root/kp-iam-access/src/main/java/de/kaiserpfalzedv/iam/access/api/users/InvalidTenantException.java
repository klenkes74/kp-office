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

package de.kaiserpfalzedv.iam.access.api.users;

import java.util.UUID;

/**
 * The user has not access to the given tenant.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-14
 */
public class InvalidTenantException extends SecurityException {
    private static final long serialVersionUID = 100210211841393604L;

    /**
     * @param tenantId The tenant id the user wants to have access to.
     */
    public InvalidTenantException(final UUID tenantId) {
        super(String.format("User has no access to tenant with id '%s'", tenantId.toString()));
    }
}
