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

package de.kaiserpfalzedv.iam.tenant.jpa;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.iam.tenant.api.Tenant;
import org.apache.commons.lang3.builder.Builder;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-11
 */
public class JPATenantBuilder implements Builder<Tenant> {
    private UUID tenant;
    private UUID id;
    private String displayName;
    private String fullName;
    private String key;

    @Override
    public JPATenant build() {
        return new JPATenant(tenant, id, key, displayName, fullName);
    }

    public boolean validate() {
        return true;
    }

    public JPATenantBuilder withTenant(final Tenant tenant) {
        withTenant(tenant.getTenant());
        withId(tenant.getId());
        withKey(tenant.getKey());
        withDisplayName(tenant.getDisplayName());
        withFullName(tenant.getFullName());

        return this;
    }

    public JPATenantBuilder withTenant(@NotNull final UUID tenantId) {
        this.tenant = tenantId;
        return this;
    }

    public JPATenantBuilder withId(@NotNull final UUID uniqueId) {
        this.id = uniqueId;
        return this;
    }

    public JPATenantBuilder withKey(@NotNull final String key) {
        this.key = key;
        return this;
    }

    public JPATenantBuilder withDisplayName(@NotNull final String name) {
        this.displayName = name;
        return this;
    }

    public JPATenantBuilder withFullName(@NotNull final String name) {
        this.fullName = name;
        return this;
    }
}
