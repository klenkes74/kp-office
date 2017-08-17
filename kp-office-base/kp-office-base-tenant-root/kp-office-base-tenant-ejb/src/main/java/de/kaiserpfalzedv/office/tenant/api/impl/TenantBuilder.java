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

package de.kaiserpfalzedv.office.tenant.api.impl;

import java.util.ArrayList;
import java.util.UUID;

import de.kaiserpfalzedv.office.common.api.BuilderException;
import de.kaiserpfalzedv.office.tenant.api.Tenant;
import org.apache.commons.lang3.builder.Builder;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2016-09-20
 */
public class TenantBuilder implements Builder<Tenant> {
    private UUID tenant;
    private UUID id;
    private String key;
    private String displayName;
    private String fullName;

    private Builder<String> keygenerator;

    public TenantBuilder() {}

    public TenantBuilder(final Builder<String> keygenerator) {
        this.keygenerator = keygenerator;
    }


    @Override
    public Tenant build() {
        defaultValues();
        validate();

        return new TenantImpl(tenant, id, key, displayName, fullName);
    }

    private void defaultValues() {
        if (tenant == null) {
            tenant = new NullTenant().getId();
        }

        if (id == null) {
            id = UUID.randomUUID();
        }

        if (isBlank(key) && keygenerator != null) {
            key = keygenerator.build();
        }

        if (isBlank(displayName)) {
            displayName = fullName;
        }

        if (isBlank(fullName)) {
            fullName = displayName;
        }
    }

    private void validate() {
        ArrayList<String> failures = new ArrayList<>(3);

        if (isBlank(key)) {
            failures.add("A tenant needs a key. No Key given or key generator failed.");
        }

        if (isBlank(displayName)) {
            failures.add("A tenant needs a display name. No name given.");
        }

        if (isBlank(fullName)) {
            failures.add("A tenant needs a full name. No name given.");
        }

        if (!failures.isEmpty()) {
            throw new BuilderException(Tenant.class, failures);
        }
    }

    public TenantBuilder withKeyGenerator(final Builder<String> keygenerator) {
        this.keygenerator = keygenerator;
        return this;
    }


    public TenantBuilder withTenant(final Tenant orig) {
        withTenantId(orig.getTenant());
        withId(orig.getId());
        withKey(orig.getKey());
        withDisplayName(orig.getDisplayName());
        withFullName(orig.getFullName());

        return this;
    }


    public TenantBuilder withTenantId(final UUID tenant) {
        this.tenant = tenant;
        return this;
    }

    public TenantBuilder withId(final UUID id) {
        this.id = id;
        return this;
    }

    public TenantBuilder withKey(String key) {
        this.key = key;
        return this;
    }

    public TenantBuilder withDisplayName(final String displayName) {
        this.displayName = displayName;
        return this;
    }

    public TenantBuilder withFullName(final String fullName) {
        this.fullName = fullName;
        return this;
    }
}
