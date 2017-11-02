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

import de.kaiserpfalzedv.commons.jpa.JPAAbstractTenantIdentifiable;
import de.kaiserpfalzedv.office.contacts.api.Contact;
import de.kaiserpfalzedv.office.contacts.api.Representative;
import de.kaiserpfalzedv.office.contacts.api.RepresentativeType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-10
 */
@Entity
@Table(name = "REPRESENTATIVES")
public class JPARepresentative extends JPAAbstractTenantIdentifiable implements Representative {

    @Enumerated(EnumType.STRING)
    @Column(name = "REPRESENTATIVE_TYPE_", nullable = false)
    private RepresentativeType type;

    @OneToOne(
            targetEntity = JPAContact.class
    )
    @Column(name = "CONTACT_ID_", nullable = false)
    private Contact contact;


    @SuppressWarnings("deprecation")
    @Deprecated
    public JPARepresentative() {}

    public JPARepresentative(
            @NotNull final UUID id,
            @NotNull final UUID tenant,
            @NotNull final RepresentativeType type,
            @NotNull final Contact contact
    ) {
        super(id, tenant);

        this.type = type;
        this.contact = contact;
    }


    @Override
    public RepresentativeType getRepresentativeType() {
        return type;
    }


    @Override
    public Contact getContact() {
        return contact;
    }

    void setContact(@NotNull final Contact contact) {
        this.contact = contact;
    }
}
