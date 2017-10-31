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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.office.access.api.users.OfficePrincipal;
import de.kaiserpfalzedv.office.access.api.users.OfficeRole;
import de.kaiserpfalzedv.office.access.api.users.PasswordHolding;
import de.kaiserpfalzedv.office.common.api.BuilderException;
import de.kaiserpfalzedv.office.common.api.data.Email;
import de.kaiserpfalzedv.office.tenant.api.Tenant;
import org.apache.commons.lang3.builder.Builder;

import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * Builds the principal given the correct data.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-11
 */
public class OfficePrincipalBuilder implements Builder<OfficePrincipalImpl> {
    private final HashSet<OfficeRole> roles = new HashSet<>();
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

    public OfficePrincipalImpl build() {
        setDefaultValuesIfNeeded();
        validateDuringBuild();

        return new OfficePrincipalImpl(
                id, tenant, login,
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
            throw new BuilderException(OfficePrincipalImpl.class, errors);
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

    public OfficePrincipalBuilder withUser(final OfficePrincipal user) {
        try {
            withPassword(((PasswordHolding) user).getPassword());
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("The user given does not implement the interface PasswordHolding. So no password can be transfered!");
        }

        withId(user.getId());
        withLogin(user.getName());
        withEmailAddress(user.getEmailAddress());
        possibleTenants(user.getPossibleTenants());
        withRoles(user.getRoles());
        setLocked(user.isLocked());

        return this;
    }

    public OfficePrincipalBuilder withPassword(final String password) {
        this.password = password;
        return this;
    }

    public OfficePrincipalBuilder withId(final UUID uniqueId) {
        this.id = uniqueId;
        return this;
    }

    public OfficePrincipalBuilder withLogin(final String name) {
        this.login = name;
        return this;
    }

    public OfficePrincipalBuilder withEmailAddress(final Email emailAddress) {
        this.email = emailAddress;
        return this;
    }

    public OfficePrincipalBuilder possibleTenants(@NotNull final Set<UUID> tenants) {
        this.tenants.clear();

        if (tenants != null) {
            this.tenants.addAll(tenants);
        }
        return this;
    }

    public OfficePrincipalBuilder withRoles(@NotNull final Collection<? extends OfficeRole> roles) {
        this.roles.clear();

        if (roles != null) {
            this.roles.addAll(roles);
        }
        return this;
    }

    private void setLocked(final boolean locked) {
        this.locked = locked;
    }

    public OfficePrincipalBuilder clearPossibleTenants() {
        this.tenants.clear();
        return this;
    }

    public OfficePrincipalBuilder addPossibleTenant(@NotNull final UUID tenant) {
        this.tenants.add(tenant);
        return this;
    }

    public OfficePrincipalBuilder removePossibleTenant(@NotNull final UUID tenant) {
        this.tenants.remove(tenant);
        return this;
    }

    public OfficePrincipalBuilder locked() {
        this.locked = true;
        return this;
    }

    public OfficePrincipalBuilder unlocked() {
        this.locked = false;
        return this;
    }

    public OfficePrincipalBuilder clearRoles() {
        this.roles.clear();
        return this;
    }

    public OfficePrincipalBuilder addRole(final OfficeRole role) {
        roles.remove(role);
        return this;
    }

    public OfficePrincipalBuilder removeRole(final OfficeRole role) {
        roles.remove(role);
        return this;
    }
}
