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

package de.kaiserpfalzedv.office.contacts.jpa;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.ConstraintMode;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.office.common.jpa.JPAAbstractTenantIdentifiable;
import de.kaiserpfalzedv.office.contacts.api.Contact;
import de.kaiserpfalzedv.office.contacts.api.Representative;
import de.kaiserpfalzedv.office.contacts.api.addresses.Address;
import de.kaiserpfalzedv.office.contacts.jpa.addresses.JPAAddress;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-10
 */
@Entity
@Table(name = "CONTACTS")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("BASE")
@DiscriminatorColumn(name = "TYPE_")
public abstract class JPAContact extends JPAAbstractTenantIdentifiable implements Contact {
    private static final long serialVersionUID = -1627383431970226551L;


    @OneToMany(
            targetEntity = JPARepresentative.class,
            orphanRemoval = true,
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "CONTACT_ID_",
            nullable = false,
            referencedColumnName = "ID_",
            foreignKey = @ForeignKey(name = "REPRESENTATIVE_CONTACT_FK", value = ConstraintMode.CONSTRAINT)
    )
    @OrderColumn(name = "INDEX_")
    private List<Representative> representatives;

    @OneToMany(
            targetEntity = JPAAddress.class,
            mappedBy = "contact",
            orphanRemoval = true,
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @OrderColumn(name = "INDEX_")
    private List<Address> addresses;


    @SuppressWarnings("deprecation")
    @Deprecated // Only for JPA
    public JPAContact() {}

    public JPAContact(
            @NotNull final UUID id,
            @NotNull final UUID tenant,
            @NotNull final List<Representative> representatives,
            @NotNull final List<Address> addresses
    ) {
        super(id, tenant);

        this.representatives = representatives;
        this.addresses = addresses;
    }


    @Override
    public List<Representative> getRepresentatives() {
        return representatives;
    }

    public void setRepresentatives(final List<Representative> representatives) {
        this.representatives = representatives;

        this.representatives.forEach(r -> { ((JPARepresentative) r).setContact(this); });
    }


    @Override
    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(final List<Address> addresses) {
        this.addresses = addresses;

        this.addresses.forEach(a -> { ((JPAAddress) a).setContact(this); });
    }
}
