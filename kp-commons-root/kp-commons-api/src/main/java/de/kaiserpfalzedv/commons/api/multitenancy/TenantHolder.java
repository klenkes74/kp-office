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

package de.kaiserpfalzedv.commons.api.multitenancy;

import java.util.Optional;
import java.util.UUID;

/**
 * The tenant holder holds the current tenant. It has to be stored by the first recieving service and can
 * be retrieved during the call. After the call it has to be cleared so the next call to the same thread won't receive
 * the old tenant.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-13
 */
public interface TenantHolder {
    /**
     * @return The tenant id (if one is set).
     */
    Optional<UUID> getTenant();

    /**
     * Saves the tenant id for the current thread.
     *
     * @param tenant the tenant id to save for the current thread.
     */
    void setTenant(final UUID tenant);

    /**
     * Removes the tenant from the current thread.
     */
    void clearTenant();
}
