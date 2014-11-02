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
import de.kaiserpfalzEdv.office.contacts.address.AddressDTO;
import de.kaiserpfalzEdv.office.core.KPOTenantHoldingEntityDTO;
import de.kaiserpfalzEdv.office.tenants.Tenant;
import org.apache.commons.lang3.builder.ToStringBuilder;

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


    private final HashSet<AddressDTO> addresses = new HashSet<>();
    private final HashSet<ContactDTO> contacts = new HashSet<>();
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
    public Set<AddressDTO> getAddresses() {
        return Collections.unmodifiableSet(addresses);
    }

    @SuppressWarnings("deprecation")
    public void setAddresses(@NotNull final Collection<? extends Address> addresses) {
        HashSet<AddressDTO> addressDTOs = new HashSet<>(addresses.size());
        addresses.parallelStream().forEach(a -> addressDTOs.add((AddressDTO) a));

        setAddresses(addressDTOs);
    }

    @Deprecated // Only for Jackson, JAX-B and JPA!
    public void setAddresses(@NotNull final Set<AddressDTO> addresses) {
        this.addresses.clear();

        if (addresses != null) {
            this.addresses.addAll(addresses);
        }
    }

    public void addAddress(@NotNull final AddressDTO address) {
        if (!addresses.contains(address)) {
            this.addresses.add(address);
        }
    }

    public <A extends Address> void addAddress(@NotNull final A address) {
        addAddress((AddressDTO) address);
    }

    public void removeAddress(@NotNull final AddressDTO address) {
        if (addresses.contains(address)) {
            this.addresses.remove(address);
        }
    }

    public <A extends Address> void removeAddress(@NotNull final A address) {
        removeAddress((AddressDTO) address);
    }


    @Override
    public Set<ContactDTO> getSubContacts() {
        return Collections.unmodifiableSet(contacts);
    }

    @SuppressWarnings("deprecation")
    public void setSubContacts(@NotNull final Collection<? extends Contact> contacts) {
        HashSet<ContactDTO> contactDTOs = new HashSet<>(contacts.size());
        contactDTOs.parallelStream().forEach(c -> contactDTOs.add((ContactDTO) c));

        setSubContacts(contactDTOs);
    }

    @Deprecated // Only for Jackson, JAX-B and JPA!
    public void setSubContacts(@NotNull final Set<ContactDTO> contacts) {
        this.contacts.clear();

        if (contacts != null) {
            this.contacts.addAll(contacts);
        }
    }

    public void addSubContact(@NotNull final ContactDTO contact) {
        if (!contacts.contains(contact)) {
            this.contacts.add(contact);
        }
    }

    public <C extends Contact> void addSubContact(@NotNull final C contact) {
        addSubContact((ContactDTO) contact);
    }

    public void removeSubContact(@NotNull final ContactDTO contact) {
        if (contacts.contains(contact)) {
            this.contacts.remove(contact);
        }
    }

    public <C extends Contact> void removeSubContact(@NotNull final C contact) {
        removeSubContact((ContactDTO) contact);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("type", type)
                .toString();
    }
}
