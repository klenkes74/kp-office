/*
 * Copyright 2015 Kaiserpfalz EDV-Service, Roland T. Lichti
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
import de.kaiserpfalzEdv.office.contacts.address.AddressDO;
import de.kaiserpfalzEdv.office.core.KPOTenantHoldingEntity;
import de.kaiserpfalzEdv.office.core.tenants.TenantDO;
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
public abstract class ContactDO extends KPOTenantHoldingEntity implements Contact {
    private static final long serialVersionUID = 7264252295359181362L;


    private final HashSet<AddressDO> addresses = new HashSet<>();
    private final HashSet<ContactDO> contacts = new HashSet<>();
    private ContactType type;


    @SuppressWarnings("deprecation")
    @Deprecated // Only for Jackson, JAX-B and JPA!
    public ContactDO() {
    }

    public ContactDO(@NotNull final UUID id,
                     @NotNull final String name,
                     @NotNull final String number,
                     @NotNull final TenantDO tenant,
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
    public Set<AddressDO> getAddresses() {
        return Collections.unmodifiableSet(addresses);
    }

    @SuppressWarnings("deprecation")
    public void setAddresses(@NotNull final Collection<? extends Address> addresses) {
        HashSet<AddressDO> addressDOs = new HashSet<>(addresses.size());
        addresses.parallelStream().forEach(a -> addressDOs.add((AddressDO) a));

        setAddresses(addressDOs);
    }

    @Deprecated // Only for Jackson, JAX-B and JPA!
    public void setAddresses(@NotNull final Set<AddressDO> addresses) {
        this.addresses.clear();

        if (addresses != null) {
            this.addresses.addAll(addresses);
        }
    }

    public void addAddress(@NotNull final AddressDO address) {
        if (!addresses.contains(address)) {
            this.addresses.add(address);
        }
    }

    public <A extends Address> void addAddress(@NotNull final A address) {
        addAddress((AddressDO) address);
    }

    public void removeAddress(@NotNull final AddressDO address) {
        if (addresses.contains(address)) {
            this.addresses.remove(address);
        }
    }

    public <A extends Address> void removeAddress(@NotNull final A address) {
        removeAddress((AddressDO) address);
    }


    @Override
    public Set<ContactDO> getSubContacts() {
        return Collections.unmodifiableSet(contacts);
    }

    @SuppressWarnings("deprecation")
    public void setSubContacts(@NotNull final Collection<? extends Contact> contacts) {
        HashSet<ContactDO> contactDOs = new HashSet<>(contacts.size());
        contactDOs.parallelStream().forEach(c -> contactDOs.add((ContactDO) c));

        setSubContacts(contactDOs);
    }

    @Deprecated // Only for Jackson, JAX-B and JPA!
    public void setSubContacts(@NotNull final Set<ContactDO> contacts) {
        this.contacts.clear();

        if (contacts != null) {
            this.contacts.addAll(contacts);
        }
    }

    public void addSubContact(@NotNull final ContactDO contact) {
        if (!contacts.contains(contact)) {
            this.contacts.add(contact);
        }
    }

    public <C extends Contact> void addSubContact(@NotNull final C contact) {
        addSubContact((ContactDO) contact);
    }

    public void removeSubContact(@NotNull final ContactDO contact) {
        if (contacts.contains(contact)) {
            this.contacts.remove(contact);
        }
    }

    public <C extends Contact> void removeSubContact(@NotNull final C contact) {
        removeSubContact((ContactDO) contact);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("type", type)
                .toString();
    }
}
