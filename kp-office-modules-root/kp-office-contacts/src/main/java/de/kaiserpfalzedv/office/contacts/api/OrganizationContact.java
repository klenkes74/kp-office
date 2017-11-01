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
import java.util.Optional;

import de.kaiserpfalzedv.office.contacts.api.names.OrganizationalName;

/**
 * An organizational contact.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-10
 */
public interface OrganizationContact extends Contact {
    @Override
    OrganizationalName getName();

    /**
     * @return The registration of the contact.
     */
    Optional<OrganizationRegistration> getRegistration();

    /**
     * @return The owning organization of this organization (if any).
     */
    Optional<OrganizationContact> getSuperOrganization();

    /**
     * @return The organizations belonging to this organization.
     */
    List<OrganizationContact> getSubOrganizations();
}
