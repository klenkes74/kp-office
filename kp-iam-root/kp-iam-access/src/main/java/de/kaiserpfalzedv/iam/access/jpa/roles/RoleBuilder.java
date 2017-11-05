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

import de.kaiserpfalzedv.iam.access.api.roles.Role;
import org.apache.commons.lang3.builder.Builder;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-11
 */
public class RoleBuilder implements Builder<Role> {
    private final HashSet<JPARole> roles = new HashSet<>();
    private final HashSet<JPAEntitlement> entitlements = new HashSet<>();

    private UUID tenant;
    private UUID id;
    private String displayName;
    private String fullName;

    @Override
    public JPARole build() {
        return new JPARole(tenant, id, displayName, fullName, roles, entitlements);
    }

    public boolean validate() {
        return true;
    }

    public RoleBuilder withRole(final JPARole role) {
        withTenant(role.getTenant());
        withId(role.getId());
        withDisplayName(role.getDisplayName());
        withFullName(role.getFullName());
        withRoles((Set<JPARole>) role.getIncludedRoles());
        withEntitlements((Set<JPAEntitlement>) role.getEntitlements());

        return this;
    }

    public RoleBuilder withId(@NotNull final UUID uniqueId) {
        this.id = uniqueId;
        return this;
    }

    public RoleBuilder withDisplayName(@NotNull final String name) {
        this.displayName = name;
        return this;
    }

    public RoleBuilder withFullName(@NotNull final String name) {
        this.fullName = name;
        return this;
    }

    public RoleBuilder withTenant(@NotNull final UUID tenantId) {
        this.tenant = tenantId;
        return this;
    }

    public RoleBuilder withRoles(@NotNull final Set<JPARole> roles) {
        this.roles.clear();
        return addRoles(roles);
    }

    public RoleBuilder addRoles(@NotNull final Set<JPARole> roles) {
        this.roles.addAll(roles);
        return this;
    }

    public RoleBuilder addRole(@NotNull final JPARole role) {
        this.roles.add(role);
        return this;
    }

    public RoleBuilder clearRoles() {
        this.roles.clear();
        return this;
    }

    public RoleBuilder removeRole(@NotNull final JPARole role) {
        this.roles.remove(role);
        return this;
    }


    public RoleBuilder withEntitlements(@NotNull final Set<JPAEntitlement> entitlements) {
        this.entitlements.clear();
        return addEntitlements(entitlements);
    }

    public RoleBuilder addEntitlements(@NotNull final Set<JPAEntitlement> entitlements) {
        this.entitlements.addAll(entitlements);
        return this;
    }

    /**
     * Adds a single entitlement to the direct entitlements.
     *
     * @param entitlement the entitlement that should be added.
     * @return the builder itself
     */
    public RoleBuilder addEntitlement(@NotNull final JPAEntitlement entitlement) {
        this.entitlements.add(entitlement);
        return this;
    }


    public RoleBuilder clearEntitlements() {
        this.entitlements.clear();
        return this;
    }

    public RoleBuilder removeEntitlements(@NotNull final Collection<JPAEntitlement> entitlements) {
        this.entitlements.removeAll(entitlements);
        return this;
    }

    /**
     * Removes a single entitlement from the direct entitlements.
     *
     * @param entitlement the entitlement that should be removed.
     * @return the builder itself
     */
    public RoleBuilder removeEntitlement(@NotNull final JPAEntitlement entitlement) {
        this.entitlements.remove(entitlement);
        return this;
    }
}
