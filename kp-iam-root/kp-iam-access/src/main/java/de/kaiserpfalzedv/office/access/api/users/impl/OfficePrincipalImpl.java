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

import de.kaiserpfalzedv.commons.api.data.Email;
import de.kaiserpfalzedv.office.access.api.users.*;

import java.util.*;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-11
 */
class OfficePrincipalImpl implements OfficePrincipal {
    private static final long serialVersionUID = 7375303558202040469L;


    private final HashSet<OfficeRole> roles = new HashSet<>();
    private UUID id;
    private UUID tenantId;
    private String login;
    private Email emailAddress;
    private String password;
    private boolean locked = false;


    OfficePrincipalImpl(
            final UUID uniqueId,
            final UUID tenantId,
            final String name,
            final Email emailAddress,
            final String password,
            final boolean locked,
            final Collection<OfficeRole> roles
    ) {
        this.id = uniqueId;
        this.login = name;

        this.emailAddress = emailAddress;
        this.password = password;
        this.locked = locked;

        if (roles != null) {
            this.roles.addAll(roles);
        }
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
    public Set<OfficeRole> getRoles() {
        HashSet<OfficeRole> result = new HashSet<>();


        roles.forEach(r -> addRole(result, r));

        return Collections.unmodifiableSet(result);
    }

    @Override
    public boolean isInRole(OfficeRole role) {
        return getRoles().contains(role);
    }

    @Override
    public Set<OfficeEntitlement> getEntitlements() {
        HashSet<OfficeEntitlement> result = new HashSet<>();

        getRoles().forEach(r -> result.addAll(r.getEntitlements()));

        return result;
    }

    @Override
    public boolean isEntitled(OfficeEntitlement entitlement) {
        return getEntitlements().contains(entitlement);
    }

    @Override
    public Set<UUID> getPossibleTenants() {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.office.access.api.model.impl.OfficePrincipalImpl.getPossibleTenants
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void switchTenant(UUID tenant) {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.office.access.api.model.impl.OfficePrincipalImpl.switchTenant
        //To change body of implemented methods use File | Settings | File Templates.
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

    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * This is an internal method needed for the copying builder.
     *
     * @return the password of this user.
     */
    public String getPassword() {
        return password;
    }

    private void addRole(HashSet<OfficeRole> roles, OfficeRole role) {
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
