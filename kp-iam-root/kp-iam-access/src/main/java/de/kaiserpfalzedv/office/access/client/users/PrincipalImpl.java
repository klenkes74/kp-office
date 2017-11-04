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

package de.kaiserpfalzedv.office.access.client.users;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.commons.api.data.Email;
import de.kaiserpfalzedv.office.access.api.PasswordFailureException;
import de.kaiserpfalzedv.office.access.api.roles.Entitlement;
import de.kaiserpfalzedv.office.access.api.roles.Role;
import de.kaiserpfalzedv.office.access.api.users.PasswordHolding;
import de.kaiserpfalzedv.office.access.api.users.Principal;
import de.kaiserpfalzedv.office.access.api.users.UserHasNoAccessToTenantException;
import de.kaiserpfalzedv.office.access.api.users.UserIsLockedException;

/**
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-11
 */
class PrincipalImpl implements Principal, PasswordHolding {
    private static final long serialVersionUID = 6586697150416157913L;

    private UUID id;
    private UUID tenantId;
    private String login;
    private Email emailAddress;
    private String password;
    private boolean locked = false;

    private final HashSet<UUID> possibleTenants = new HashSet<>();
    private final HashMap<UUID, HashSet<Role>> roles = new HashMap<>();


    PrincipalImpl(
            final UUID uniqueId,
            final UUID tenantId,
            final Collection<UUID> possibleTenants,
            final String name,
            final Email emailAddress,
            final String password,
            final boolean locked,
            final Map<UUID, Set<Role>> roles
    ) {
        this.id = uniqueId;
        this.tenantId = tenantId;
        this.possibleTenants.addAll(possibleTenants);
        this.login = name;

        this.emailAddress = emailAddress;
        this.password = password;
        this.locked = locked;


        roles.keySet().forEach(t -> {
            if (!this.roles.containsKey(t)) {
                this.roles.put(t, new HashSet<>());
            }

            this.roles.get(t).addAll(roles.get(t));
        });
    }


    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public UUID getTenant() {
        return tenantId;
    }

    @Override
    public Locale getLocale() {
        return Locale.getDefault();
    }

    @Override
    public Email getEmailAddress() {
        return emailAddress;
    }

    @Override
    public void login(final String passwordToCheck) throws PasswordFailureException, UserIsLockedException {
        if (password == null || !password.equals(passwordToCheck)) {
            throw new PasswordFailureException(login);
        }

        if (locked) {
            throw new UserIsLockedException(login);
        }
    }

    @Override
    public boolean isLocked() {
        return locked;
    }

    @Override
    public Set<Role> getRoles() {
        return getRoles(tenantId);
    }

    @Override
    public Set<Role> getRoles(@NotNull final UUID tenant) {
        HashSet<Role> result = new HashSet<>();

        roles.get(tenantId).forEach(r -> addRole(result, r));

        return Collections.unmodifiableSet(result);
    }

    public Map<UUID, Set<Role>> getRoleStructure() {
        HashMap<UUID, Set<Role>> result = new HashMap<>(roles.size());

        roles.keySet().forEach(t -> {
            result.put(t, Collections.unmodifiableSet(roles.get(t)));
        });

        return Collections.unmodifiableMap(result);
    }

    @Override
    public boolean isInRole(Role role) {
        return roles.get(tenantId).contains(role);
    }

    @Override
    public Set<Entitlement> getEntitlements() {
        HashSet<Entitlement> result = new HashSet<>();

        roles.get(tenantId).forEach(r -> result.addAll(r.getEntitlements()));

        return Collections.unmodifiableSet(result);
    }

    @Override
    public boolean isEntitled(Entitlement entitlement) {
        return getEntitlements().contains(entitlement);
    }

    @Override
    public Set<UUID> getPossibleTenants() {
        return Collections.unmodifiableSet(possibleTenants);
    }

    @Override
    public void switchTenant(@NotNull final UUID tenant) {
        if (!possibleTenants.contains(tenant)) {
            throw new UserHasNoAccessToTenantException(login, tenant);
        }

        this.tenantId = tenant;
    }

    @Override
    public String getDisplayName() {
        return login;
    }

    @Override
    public String getFullName() {
        return login;
    }

    public UUID getTenantId() {
        return tenantId;
    }

    public void setTenantId(UUID tenantId) {
        this.tenantId = tenantId;
    }

    @Override
    public String getName() {
        return login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    private void addRole(HashSet<Role> roles, Role role) {
        if (roles.contains(role))
            return;

        roles.add(role);

        role.getIncludedRoles().forEach(r -> addRole(roles, role));
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder()
                .append(getClass().getSimpleName()).append('@').append(System.identityHashCode(this)).append('{')
                .append(id).append(", ").append(login);

        if (locked) {
            result.append(", locked");
        }

        if (roles.size() >= 1) {
            result.append(", ").append(roles.size()).append(" roles");
        }

        return result.append('}').toString();
    }
}
