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

package de.kaiserpfalzEdv.office.contacts.commands.contact;

import de.kaiserpfalzEdv.office.contacts.address.Address;
import de.kaiserpfalzEdv.office.contacts.address.AddressDTO;
import de.kaiserpfalzEdv.office.contacts.contact.Contact;
import de.kaiserpfalzEdv.office.contacts.contact.ContactDTO;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class CreateContactCommand extends ContactBaseCommand {
    private static final long serialVersionUID = 1L;
    private final HashSet<AddressDTO> addresses = new HashSet<>();
    private final HashSet<ContactDTO> contacts = new HashSet<>();
    private String name;
    private String number;
    private UUID tenantId;

    @SuppressWarnings("UnusedDeclaration")
    @Deprecated // Only for Jackson, JAX-B and JPA!
    public CreateContactCommand() {
    }

    public CreateContactCommand(@NotNull final Contact contact) {
        super(contact.getId());

        setName(contact.getDisplayName());
        setNumber(contact.getDisplayNumber());
        setTenantId(contact.getTenantId());

        setAddresses(contact.getAddresses());
        setContacts(contact.getSubContacts());
    }


    public String getName() {
        return name;
    }

    protected void setName(@NotNull final String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    protected void setNumber(@NotNull final String number) {
        this.number = number;
    }

    public UUID getTenantId() {
        return tenantId;
    }

    protected void setTenantId(@NotNull final UUID tenantId) {
        this.tenantId = tenantId;
    }


    public Set<AddressDTO> getAddresses() {
        return addresses;
    }

    public void setAddresses(@NotNull final Collection<? extends Address> addresses) {
        HashSet<AddressDTO> result = new HashSet<>(addresses.size());
    }

    public void setAddresses(@NotNull final Set<AddressDTO> addresses) {
        this.addresses.clear();

        if (addresses != null)
            this.addresses.addAll(addresses);
    }

    public Set<ContactDTO> getContacts() {
        return contacts;
    }

    public void setContacts(@NotNull final Set<ContactDTO> contacts) {
        this.contacts.clear();

        if (contacts != null)
            this.contacts.addAll(contacts);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("displayNumber", number)
                .append("displayName", name)
                .toString();
    }
}
