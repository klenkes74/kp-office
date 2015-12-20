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

package de.kaiserpfalzEdv.office.core.tenants.impl;

import de.kaiserpfalzEdv.commons.util.BuilderException;
import de.kaiserpfalzEdv.office.core.tenants.Tenant;
import org.apache.commons.lang3.builder.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 16.08.15 23:43
 */
public class TenantBuilder implements Builder<TenantImpl> {
    private static final Logger LOG = LoggerFactory.getLogger(TenantBuilder.class);

    private UUID   id;
    private String number;
    private String name;

    @Override
    public TenantImpl build() {
        calculateDefaults();
        validate();

        return new TenantImpl(id, name, number);
    }

    private void calculateDefaults() {
        if (id == null) id = UUID.randomUUID();
        if (isBlank(number)) number = id.toString();
        if (isBlank(name)) name = "Tenant #" + id.toString();
    }

    private void validate() {
        ArrayList<String> errors = new ArrayList<>();

        if (id == null) errors.add("No UUID for tenant given!");
        if (isBlank(number)) errors.add("No display number for tenant given!");
        if (isBlank(name)) errors.add("No name for the tenant given!");

        if (!errors.isEmpty()) {
            throw new BuilderException(errors);
        }
    }

    public TenantBuilder withId(final UUID id) {
        this.id = id;
        return this;
    }

    public TenantBuilder withNumber(final String number) {
        this.number = number;
        return this;
    }

    public TenantBuilder withName(final String name) {
        this.name = name;
        return this;
    }

    public TenantBuilder withTenant(final Tenant tenant) {
        this.id = tenant.getId();
        this.number = tenant.getDisplayNumber();
        this.name = tenant.getDisplayName();
        return this;
    }
}
