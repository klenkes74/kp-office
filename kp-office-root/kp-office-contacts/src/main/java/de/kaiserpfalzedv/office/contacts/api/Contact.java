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

package de.kaiserpfalzedv.office.contacts.api;

import java.util.List;

import de.kaiserpfalzedv.commons.api.data.base.Nameable;
import de.kaiserpfalzedv.commons.api.multitenancy.TenantIdentifiable;
import de.kaiserpfalzedv.office.contacts.api.addresses.Address;
import de.kaiserpfalzedv.office.contacts.api.names.Name;

/**
 * The contact itself. May be a person or an organization.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-08
 */
public interface Contact extends Nameable, TenantIdentifiable {
    /**
     * @param <T> A name type. Normally a {@link de.kaiserpfalzedv.office.contacts.api.names.PersonalName} or
     *            an {@link de.kaiserpfalzedv.office.contacts.api.names.OrganizationalName}.
     *
     * @return The name according to the contact type.
     */
    <T extends Name> T getName();

    /**
     * @return The legal representatives of the contact. May be parents in case of a natural person or the chairs of
     * a corporation.
     */
    List<Representative> getRepresentatives();

    /**
     * @return The addresses of the contact. May be any address (postal or electronic). Phone numbers are electronic
     * addresses.
     */
    List<Address> getAddresses();
}
