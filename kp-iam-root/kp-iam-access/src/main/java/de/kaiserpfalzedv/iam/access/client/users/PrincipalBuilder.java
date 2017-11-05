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

package de.kaiserpfalzedv.iam.access.client.users;

import de.kaiserpfalzedv.commons.api.BuilderException;
import de.kaiserpfalzedv.commons.api.data.Email;
import de.kaiserpfalzedv.iam.access.api.roles.Role;
import de.kaiserpfalzedv.iam.access.api.users.PasswordHolding;
import de.kaiserpfalzedv.iam.access.api.users.Principal;
import de.kaiserpfalzedv.iam.tenant.api.Tenant;
import org.apache.commons.lang3.builder.Builder;

import javax.validation.constraints.NotNull;
import java.util.*;

import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * Builds the principal given the correct data.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-11
 */
public class PrincipalBuilder implements Builder<PrincipalImpl> {
    private final Map<UUID, Set<Role>> roles = new HashMap<>();
    /**
     * A list of errors during validation.
     */
    private final ArrayList<String> errors = new ArrayList<>(2);
    private final HashSet<UUID> tenants = new HashSet<>();
    private UUID id;
    private String login;
    private Email email;
    private String password;
    private boolean locked = false;
    private UUID tenant;

    public PrincipalImpl build() {
        setDefaultValuesIfNeeded();
        validateDuringBuild();

        return new PrincipalImpl(
                id,
                tenant,
                tenants,
                login,
                email,
                password,
                locked,
                roles
        );
    }

    private void setDefaultValuesIfNeeded() {
        if (id == null) {
            id = UUID.randomUUID();
        }

        if (tenant == null) {
            tenant = Tenant.DEFAULT_TENANT_ID;
        }
    }

    public void validateDuringBuild() {
        if (!validate()) {
            throw new BuilderException(PrincipalImpl.class, errors);
        }
    }

    public boolean validate() {
        boolean result = true;
        errors.clear();

        if (isEmpty(login)) {
            result = false;
            errors.add("The login is not set.");
        }

        return result;
    }

    public List<String> getValidationResults() {
        return Collections.unmodifiableList(errors);
    }

    public PrincipalBuilder withUser(final Principal user) {
        try {
            withPassword(((PasswordHolding) user).getPassword());
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("The user given does not implement the interface PasswordHolding. So no password can be transfered!");
        }

        withId(user.getId());
        withLogin(user.getName());
        withEmailAddress(user.getEmailAddress());
        possibleTenants(user.getPossibleTenants());
        withRoles(user.getRoleStructure());
        setLocked(user.isLocked());

        return this;
    }

    public PrincipalBuilder withPassword(final String password) {
        this.password = password;
        return this;
    }

    public PrincipalBuilder withId(final UUID uniqueId) {
        this.id = uniqueId;
        return this;
    }

    public PrincipalBuilder withLogin(final String name) {
        this.login = name;
        return this;
    }

    public PrincipalBuilder withEmailAddress(final Email emailAddress) {
        this.email = emailAddress;
        return this;
    }

    public PrincipalBuilder possibleTenants(@NotNull final Set<UUID> tenants) {
        this.tenants.clear();

        if (tenants != null) {
            this.tenants.addAll(tenants);
        }
        return this;
    }

    private void setLocked(final boolean locked) {
        this.locked = locked;
    }

    public PrincipalBuilder withRoles(@NotNull final Map<UUID, Set<Role>> roles) {
        roles.keySet().forEach(t -> {
            if (!this.roles.containsKey(t)) {
                this.roles.put(t, new HashSet<>());
            } else {
                this.roles.get(t).clear();
            }

            this.roles.get(t).addAll(roles.get(t));
        });
        return this;
    }

    public PrincipalBuilder clearPossibleTenants() {
        this.tenants.clear();
        return this;
    }

    public PrincipalBuilder addPossibleTenant(@NotNull final UUID tenant) {
        this.tenants.add(tenant);
        return this;
    }

    public PrincipalBuilder removePossibleTenant(@NotNull final UUID tenant) {
        this.tenants.remove(tenant);
        return this;
    }

    public PrincipalBuilder locked() {
        this.locked = true;
        return this;
    }

    public PrincipalBuilder unlocked() {
        this.locked = false;
        return this;
    }

    public PrincipalBuilder withRoles(@NotNull final UUID tenant, @NotNull final Collection<? extends Role> roles) {
        this.roles.clear();

        if (roles != null) {
            this.roles.get(tenant).addAll(roles);
        }
        return this;
    }

    public PrincipalBuilder addRoles(@NotNull final UUID tenant, final Set<Role> roles) {
        if (!this.roles.containsKey(tenant)) {
            this.roles.put(tenant, new HashSet<>());
        }

        this.roles.get(tenant).addAll(roles);
        return this;
    }

    public PrincipalBuilder addRole(@NotNull final UUID tenant, final Role role) {
        if (!roles.containsKey(tenant)) {
            roles.put(tenant, new HashSet<>());
        }

        roles.get(tenant).add(role);
        return this;
    }


    public PrincipalBuilder clearRoles() {
        this.roles.clear();
        return this;
    }

    public PrincipalBuilder removeRoles(@NotNull final UUID tenant, final Set<Role> roles) {
        if (!this.roles.containsKey(tenant)) return this;

        this.roles.get(tenant).removeAll(roles);
        return this;
    }

    public PrincipalBuilder removeRole(@NotNull UUID tenant, final Role role) {
        if (!roles.containsKey(tenant)) return this;

        roles.remove(role);
        return this;
    }
}
