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

package de.kaiserpfalzedv.office.access.client.roles;

import java.util.UUID;

import de.kaiserpfalzedv.office.access.api.roles.Entitlement;
import org.apache.commons.lang3.builder.Builder;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;


/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-11
 */
public class EntitlementBuilder implements Builder<Entitlement> {

    private UUID id;
    private String fullName;
    private String displayName;

    @Override
    public EntitlementImpl build() {
        setDefaultsIfNeeded();

        return new EntitlementImpl(id, displayName, fullName);
    }

    public void setDefaultsIfNeeded() {
        if (id == null) {
            id = UUID.randomUUID();
        }

        if (isEmpty(displayName) && isNotEmpty(fullName)) {
            displayName = fullName;
        }

        if (isNotEmpty(displayName) && isEmpty(fullName)) {
            fullName = displayName;
        }
    }

    public boolean validate() {
        return id != null
                && !(isEmpty(displayName) && isEmpty(fullName));
    }

    public EntitlementBuilder withEntitlement(final Entitlement data) {
        withId(data.getId());
        withFullName(data.getFullName());
        withDisplayName(data.getDisplayName());

        return this;
    }

    public EntitlementBuilder withId(final UUID uniqueId) {
        this.id = uniqueId;
        return this;
    }

    public EntitlementBuilder withFullName(final String name) {
        this.fullName = name;
        return this;
    }

    public EntitlementBuilder withDisplayName(final String name) {
        this.displayName = name;
        return this;
    }
}
