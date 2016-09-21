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

package de.kaiserpfalzedv.office.tenant.impl;

import java.util.ArrayList;
import java.util.UUID;

import de.kaiserpfalzedv.office.common.BuilderException;
import de.kaiserpfalzedv.office.tenant.Tenant;
import org.apache.commons.lang3.builder.Builder;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-05
 */
public class TenantBuilder implements Builder<Tenant> {
    private UUID tenantId;
    private UUID id;
    private String displayName;
    private String fullName;

    @Override
    public Tenant build() {
        setDefaults();
        validate();

        return new TenantImpl(tenantId, id, displayName, fullName);
    }

    private void setDefaults() {
        if (tenantId == null)
            tenantId = new NullTenant().getId();

        if (id == null)
            id = UUID.randomUUID();

        if (displayName == null)
            displayName = fullName;

        if (fullName == null)
            fullName = displayName;
    }

    private void validate() {
        ArrayList<String> failures = new ArrayList<>(4);

        if (tenantId == null) {
            failures.add("No tenant for the new tenant given!");
        }

        if (id == null) {
            failures.add("No id for the new tenant given!");
        }

        if (isBlank(displayName)) {
            failures.add("No display name for the new tenant given!");
        }

        if (isBlank(fullName)) {
            failures.add("No full name for the new tenant given!");
        }

        if (!failures.isEmpty()) {
            throw new BuilderException(Tenant.class, failures.toArray(new String[1]));
        }
    }


    public TenantBuilder withTenant(Tenant tenant) {
        withTenantId(tenant.getTenantId());
        withId(tenant.getId());
        withDisplayName(tenant.getDisplayName());
        withFullName(tenant.getFullName());

        return this;
    }


    public TenantBuilder withTenantId(UUID tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public TenantBuilder withId(UUID id) {
        this.id = id;
        return this;
    }

    public TenantBuilder withDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public TenantBuilder withFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }
}
