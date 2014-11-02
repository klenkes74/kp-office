/*
 * Copyright (c) 2014 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzEdv.office.contacts.contact;

import de.kaiserpfalzEdv.office.contacts.address.Address;
import de.kaiserpfalzEdv.office.core.KPOTenantHoldingEntity;

import java.util.Set;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public interface Contact extends KPOTenantHoldingEntity {
    public ContactType getType();

    public Name getName();

    public Set<? extends Address> getAddresses();

    public <A extends Address> void addAddress(A address);

    public <A extends Address> void removeAddress(A address);

    public Set<? extends Contact> getSubContacts();

    public <C extends Contact> void addSubContact(C contact);

    public <C extends Contact> void removeSubContact(C contact);
}
