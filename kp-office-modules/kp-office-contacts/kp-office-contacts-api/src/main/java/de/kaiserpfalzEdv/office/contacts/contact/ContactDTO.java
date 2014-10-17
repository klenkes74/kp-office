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
import de.kaiserpfalzEdv.office.core.KPOTenantHoldingEntityDTO;
import de.kaiserpfalzEdv.office.tenants.Tenant;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author klenkes
 * @since 2014Q
 */
public abstract class ContactDTO extends KPOTenantHoldingEntityDTO implements Contact {
    private static final long serialVersionUID = 7264252295359181362L;
    private final HashSet<Address> addresses = new HashSet<>();
    private final HashSet<Contact> contacts = new HashSet<>();
    private ContactType type;


    @SuppressWarnings("deprecation")
    @Deprecated // Only for Jackson, JAX-B and JPA!
    public ContactDTO() {
    }

    public ContactDTO(@NotNull final UUID id,
                      @NotNull final String name,
                      @NotNull final String number,
                      @NotNull final Tenant tenant,
                      @NotNull final ContactType type,
                      @NotNull final Collection<Address> addresses,
                      @NotNull final Collection<Contact> contacts) {
        super(id, name, number, tenant.getId());

        setType(type);

        setAddresses(addresses);
        setSubContacts(contacts);
    }


    @Override
    public ContactType getType() {
        return type;
    }

    public void setType(@NotNull final ContactType type) {
        this.type = type;
    }

    @Override
    public Set<Address> getAddresses() {
        return Collections.unmodifiableSet(addresses);
    }

    public void setAddresses(@NotNull final Collection<Address> addresses) {
        this.addresses.clear();

        if (addresses != null) {
            this.addresses.addAll(addresses);
        }
    }

    public void addAddress(@NotNull final Address address) {
        if (!addresses.contains(address)) {
            this.addresses.add(address);
        }
    }

    public void removeAddress(@NotNull final Address address) {
        if (addresses.contains(address)) {
            this.addresses.remove(address);
        }
    }

    @Override
    public Set<Contact> getSubContacts() {
        return Collections.unmodifiableSet(contacts);
    }

    public void setSubContacts(@NotNull final Collection<Contact> contacts) {
        this.contacts.clear();

        if (contacts != null) {
            this.contacts.addAll(contacts);
        }
    }

    public void addSubContact(@NotNull final Contact contact) {
        if (!contacts.contains(contact)) {
            this.contacts.add(contact);
        }
    }

    public void removeSubContact(@NotNull final Contact contact) {
        if (contacts.contains(contact)) {
            this.contacts.remove(contact);
        }
    }
}
