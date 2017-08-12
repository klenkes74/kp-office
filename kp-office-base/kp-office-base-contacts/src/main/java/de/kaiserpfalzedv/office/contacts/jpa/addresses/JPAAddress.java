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

package de.kaiserpfalzedv.office.contacts.jpa.addresses;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.office.commons.jpa.impl.JPAAbstractIdentifiable;
import de.kaiserpfalzedv.office.contacts.api.Contact;
import de.kaiserpfalzedv.office.contacts.api.addresses.Address;
import de.kaiserpfalzedv.office.contacts.api.addresses.AddressType;
import de.kaiserpfalzedv.office.contacts.jpa.JPAContact;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-10
 */
@Entity
@Table(name = "ADDRESSES")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class JPAAddress extends JPAAbstractIdentifiable implements Address {
    private static final long serialVersionUID = 2005983615694473019L;

    @Enumerated(EnumType.STRING)
    private AddressType addressType;

    @ManyToOne(
            targetEntity = JPAContact.class,
            fetch = FetchType.EAGER,
            optional = false
    )
    @JoinColumn(
            name = "CONTACT_ID_",
            referencedColumnName = "ID_",
            nullable = false,
            foreignKey = @ForeignKey(name = "ADDRESS_CONTAKT_FK")
    )
    private Contact contact;


    @SuppressWarnings({"deprecation", "DeprecatedIsStillUsed"})
    @Deprecated
    public JPAAddress() {}


    public JPAAddress(
            @NotNull final UUID id,
            @NotNull final UUID tenant,
            @NotNull final AddressType type,
            @NotNull final Contact contact
    ) {
        super(id, tenant);

        this.addressType = type;
        this.contact = contact;
    }

    @Override
    public AddressType getAddressType() {
        return addressType;
    }

    public void setAddressType(final AddressType addressType) {
        this.addressType = addressType;
    }

    @Override
    public boolean isBillabaleAddress() {
        return addressType.equals(AddressType.MAIN) || addressType.equals(AddressType.BILLING);
    }

    @Override
    public boolean isNotBillableAddress() {
        return !isBillabaleAddress();
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Contact getContract() {
        return contact;
    }
}
