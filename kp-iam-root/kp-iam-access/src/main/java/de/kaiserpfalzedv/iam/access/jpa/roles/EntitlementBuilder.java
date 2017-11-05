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

package de.kaiserpfalzedv.iam.access.jpa.roles;

import de.kaiserpfalzedv.iam.access.api.roles.Entitlement;
import org.apache.commons.lang3.builder.Builder;

import javax.validation.constraints.NotNull;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;


/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-11-04
 */
public class EntitlementBuilder implements Builder<Entitlement> {

    private UUID id;
    private String fullName;
    private String displayName;
    private String descriptionKey;

    @Override
    public JPAEntitlement build() {
        setDefaultsIfNeeded();

        return new JPAEntitlement(id, displayName, fullName, descriptionKey);
    }

    private void setDefaultsIfNeeded() {
        if (id == null) {
            id = UUID.randomUUID();
        }

        if (isBlank(displayName) && isNotBlank(fullName)) {
            displayName = fullName;
        }

        if (isNotBlank(displayName) && isBlank(fullName)) {
            fullName = displayName;
        }

        if (isBlank(descriptionKey)) {
            descriptionKey = displayName;
        }
    }

    public boolean validate() {
        return id != null
                && !(isBlank(displayName) && isBlank(fullName))
                && isNotBlank(descriptionKey);
    }

    public EntitlementBuilder withEntitlement(final Entitlement data) {
        withId(data.getId());
        withFullName(data.getFullName());
        withDisplayName(data.getDisplayName());
        withDescriptionKey(data.getDescriptionKey());

        return this;
    }

    public EntitlementBuilder withId(@NotNull final UUID uniqueId) {
        this.id = uniqueId;
        return this;
    }

    public EntitlementBuilder withFullName(@NotNull final String name) {
        this.fullName = name;
        return this;
    }

    public EntitlementBuilder withDisplayName(@NotNull final String name) {
        this.displayName = name;
        return this;
    }

    public EntitlementBuilder withDescriptionKey(@NotNull final String descriptionKey) {
        this.descriptionKey = descriptionKey;
        return this;
    }
}
