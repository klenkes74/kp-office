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

package de.kaiserpfalzedv.iam.access.api.roles;

import java.security.Principal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.commons.api.data.base.Nameable;
import de.kaiserpfalzedv.commons.api.multitenancy.TenantIdentifiable;

/**
 * The basic role within this system. It can have entitlements and other roles attached.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-11
 */
public interface Role extends Principal, TenantIdentifiable, Nameable {
    /**
     * @param role the role to be tested.
     *
     * @return TRUE if the role includes the requested role.
     */
    default boolean isInRole(@NotNull final Role role) {
        boolean result;

        // Fastest bail out if the roles looked for is the current role.
        if (this.equals(role))
            return true;

        // Fast bail out if the directly attached roles are already there.
        if (getDirectRoles().contains(role))
            return true;

        return getIncludedRoles().contains(role);

    }


    /**
     * @param entitlement the entitlement to be tested.
     *
     * @return TRUE if the entitlement is included in this role.
     */
    default boolean isEntitled(@NotNull final Entitlement entitlement) {
        // Fast exit out without handling included roles
        if (getEntitlements().contains(entitlement))
            return true;

        for (Role r : getIncludedRoles()) {
            if (r.isEntitled(entitlement))
                return true;
        }

        return false;
    }


    /**
     * @return an unmodifiable set of all included roles. These roles may contain other additional roles.
     */
    default Set<? extends Role> getIncludedRoles() {
        HashSet<Role> result = new HashSet<>();
        result.addAll(getDirectRoles());

        getDirectRoles().forEach(r -> r.getIncludedRoles().forEach(i -> result.add(i)));

        return Collections.unmodifiableSet(result);
    }

    /**
     * @return an unmodifiable set of all direct subroles of the current role.
     */
    Set<? extends Role> getDirectRoles();

    /**
     * @return an unmodifiable set of all directly attached entitlements. The entitlemens of included roles are not given back.
     */
    Set<? extends Entitlement> getEntitlements();
}
