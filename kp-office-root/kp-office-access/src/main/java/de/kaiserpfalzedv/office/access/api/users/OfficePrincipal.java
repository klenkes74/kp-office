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

package de.kaiserpfalzedv.office.access.api.users;

import de.kaiserpfalzedv.commons.api.data.Email;
import de.kaiserpfalzedv.commons.api.data.Identifiable;
import de.kaiserpfalzedv.commons.api.data.Nameable;
import de.kaiserpfalzedv.commons.api.data.Tenantable;

import java.security.Principal;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

/**
 * Paladins Inn uses an extended principal containing some additional data of the user.
 * <p>
 * At first registration every user gets a {@link UUID} assigned. This ID may be used for everything else later.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-11
 */
public interface OfficePrincipal extends Principal, Identifiable, Tenantable, Nameable {

    /**
     * @return the preferred language of the user. If not set returns default {@link Locale} of the JVM runtime.
     */
    Locale getLocale();

    /**
     * @return an email address of the user.
     */
    Email getEmailAddress();

    /**
     * @param passwordToCheck The password to check against the saved password.
     *
     * @throws PasswordFailureException The password did not match the password of the user.
     * @throws UserIsLockedException    The user is locked and can not log in.
     */
    void login(final String passwordToCheck) throws PasswordFailureException, UserIsLockedException;

    /**
     * @return TRUE if the user currentl is locked.
     */
    boolean isLocked();

    /**
     * @return all roles this user is in.
     */
    Set<? extends OfficeRole> getRoles();

    /**
     * @param role the role to be checked.
     *
     * @return TURE if the user is in this role.
     */
    boolean isInRole(OfficeRole role);

    /**
     * @return a set of all entitlments of this user.
     */
    Set<? extends OfficeEntitlement> getEntitlements();

    /**
     * @param entitlement the entitlement to be checked.
     *
     * @return TRUE if the user has the entitlement.
     */
    boolean isEntitled(OfficeEntitlement entitlement);

    Set<UUID> getPossibleTenants();

    void switchTenant(UUID tenant);
}
