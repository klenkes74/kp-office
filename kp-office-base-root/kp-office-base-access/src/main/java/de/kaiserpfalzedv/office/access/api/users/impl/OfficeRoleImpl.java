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

import java.security.Principal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import de.kaiserpfalzedv.office.access.api.users.OfficeEntitlement;
import de.kaiserpfalzedv.office.access.api.users.OfficeRole;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-11
 */
public class OfficeRoleImpl implements OfficeRole {
    private static final long serialVersionUID = 2265558473391429590L;
    private final HashSet<OfficeRole> roles = new HashSet<>();
    private final HashSet<OfficeEntitlement> directEntitlements = new HashSet<>();
    private final HashSet<OfficeEntitlement> entitlements = new HashSet<>();
    private UUID id;
    private String displayName;
    private String fullName;


    OfficeRoleImpl(
            final UUID id,
            final String displayName,
            final String fullName,
            final Set<? extends OfficeRole> roles,
            final Set<? extends OfficeEntitlement> directEntitlements,
            final Set<? extends OfficeEntitlement> entitlements
    ) {
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

    public boolean isInRole(OfficeRole role) {
        boolean result = equals(role);

        Iterator<OfficeRole> roleIterator = roles.iterator();
        while (!result && roleIterator.hasNext()) {
            result = roleIterator.next().isInRole(role);
        }

        return result;
    }

    @Override
    public boolean isEntitled(Principal entitlement) {
        for (Principal e : entitlements) {
            if (e.equals(entitlement))
                return true;
        }

        return false;
    }

    @Override
    public Set<? extends OfficeRole> getIncludedRoles() {
        return Collections.unmodifiableSet(roles);
    }

    @Override
    public Set<? extends OfficeEntitlement> getEntitlements() {
        return Collections.unmodifiableSet(directEntitlements);
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
}
