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

package de.kaiserpfalzedv.iam.access.client.roles;

import de.kaiserpfalzedv.iam.access.api.roles.Entitlement;
import de.kaiserpfalzedv.iam.access.api.roles.Role;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-11
 */
public class RoleImpl implements Role {
    private static final long serialVersionUID = 2265558473391429590L;
    private final HashSet<Role> roles = new HashSet<>();
    private final HashSet<Entitlement> directEntitlements = new HashSet<>();
    private final HashSet<Entitlement> entitlements = new HashSet<>();
    private UUID tenant;
    private UUID id;
    private String displayName;
    private String fullName;


    RoleImpl(
            final UUID tenant,
            final UUID id,
            final String displayName,
            final String fullName,
            final Set<? extends Role> roles,
            final Set<? extends Entitlement> directEntitlements,
            final Set<? extends Entitlement> entitlements
    ) {
        this.tenant = tenant;
        this.id = id;
        this.displayName = displayName;
        this.fullName = fullName;

        this.roles.addAll(roles);
        this.directEntitlements.addAll(directEntitlements);
        this.entitlements.addAll(entitlements);
    }

    @Override
    public String getName() {
        return displayName;
    }

    @Override
    public Set<? extends Role> getDirectRoles() {
        return Collections.unmodifiableSet(roles);
    }

    @Override
    public Set<? extends Entitlement> getEntitlements() {
        return Collections.unmodifiableSet(directEntitlements);
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    @Override
    public UUID getTenant() {
        return tenant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Role)) return false;

        Role role = (Role) o;

        return new EqualsBuilder()
                .append(getTenant(), role.getTenant())
                .append(getDisplayName(), role.getDisplayName())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getTenant())
                .append(getDisplayName())
                .toHashCode();
    }


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder()
                .append(getClass().getSimpleName()).append('@').append(System.identityHashCode(this)).append('{')
                .append(getId()).append(", ").append(getDisplayName());

        if (roles.size() >= 1) {
            result.append(", roles=").append(roles.size());
        }

        if (directEntitlements.size() >= 1) {
            result
                    .append(", entitlements=")
                    .append(directEntitlements.size())
                    .append("/")
                    .append(entitlements.size());
        }

        return result.append('}').toString();
    }
}
