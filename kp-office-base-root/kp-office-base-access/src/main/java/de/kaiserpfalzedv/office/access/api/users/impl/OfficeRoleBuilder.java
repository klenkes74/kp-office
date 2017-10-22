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

package de.kaiserpfalzedv.office.access.api.users.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import de.kaiserpfalzedv.office.access.api.users.OfficeEntitlement;
import de.kaiserpfalzedv.office.access.api.users.OfficeRole;
import org.apache.commons.lang3.builder.Builder;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-11
 */
public class OfficeRoleBuilder implements Builder<OfficeRole> {
    private final HashSet<OfficeRole> roles = new HashSet<>();
    private final HashSet<OfficeEntitlement> entitlements = new HashSet<>();

    private UUID id;
    private String displayName;
    private String fullName;

    @Override
    public OfficeRoleImpl build() {
        validateDuringBuild();

        return new OfficeRoleImpl(id, displayName, fullName, roles, entitlements, getEffectiveEntitlements());
    }

    public void validateDuringBuild() {
    }

    private HashSet<OfficeEntitlement> getEffectiveEntitlements() {
        HashSet<OfficeEntitlement> roleEntitlements = new HashSet<>(entitlements);
        roles.forEach(r -> roleEntitlements.addAll(r.getEntitlements()));
        return roleEntitlements;
    }

    public boolean validate() {
        return true;
    }

    public OfficeRoleBuilder withRole(final OfficeRole role) {
        withId(role.getId());
        withDisplayName(role.getDisplayName());
        withFullName(role.getFullName());
        withRoles(role.getIncludedRoles());
        withEntitlements(role.getEntitlements());

        return this;
    }

    public OfficeRoleBuilder withId(final UUID uniqueId) {
        this.id = uniqueId;
        return this;
    }

    public OfficeRoleBuilder withDisplayName(final String name) {
        this.displayName = name;
        return this;
    }

    public OfficeRoleBuilder withFullName(final String name) {
        this.fullName = name;
        return this;
    }

    public OfficeRoleBuilder withRoles(final Set<? extends OfficeRole> roles) {
        this.roles.addAll(roles);

        return this;
    }

    public OfficeRoleBuilder withEntitlements(final Set<? extends OfficeEntitlement> entitlements) {
        this.entitlements.addAll(entitlements);
        return this;
    }

    public OfficeRoleBuilder clearRoles() {
        this.roles.clear();
        return this;
    }

    public <R extends OfficeRole> OfficeRoleBuilder addRole(R role) {
        this.roles.add(role);
        return this;
    }

    public <R extends OfficeRole> OfficeRoleBuilder removeRole(R role) {
        this.roles.remove(role);
        return this;
    }

    /**
     * Removes all directly assigned {@link OfficeEntitlement}s. The entitlements resulting from {@link OfficeRole} memberships will
     * be preserved.
     *
     * @return the builder itself.
     */
    public OfficeRoleBuilder clearEntitlements() {
        this.entitlements.clear();
        return this;
    }

    /**
     * Adds a single entitlement to the direct entitlements.
     *
     * @param entitlement the entitlement that should be added.
     * @param <E>         every interface deriving from OfficeEntitlement should work
     *
     * @return the builder itself
     */
    public <E extends OfficeEntitlement> OfficeRoleBuilder addEntitlement(E entitlement) {
        this.entitlements.add(entitlement);
        return this;
    }

    /**
     * Removes a single entitlement from the direct entitlements.
     *
     * @param entitlement the entitlement that should be removed.
     * @param <E>         every interface deriving from OfficeEntitlement should work
     *
     * @return the builder itself
     */
    public <E extends OfficeEntitlement> OfficeRoleBuilder removeEntitlement(E entitlement) {
        this.entitlements.remove(entitlement);
        return this;
    }
}
