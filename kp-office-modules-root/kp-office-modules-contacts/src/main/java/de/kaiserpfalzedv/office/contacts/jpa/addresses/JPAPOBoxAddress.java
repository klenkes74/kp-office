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

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.office.contacts.api.Contact;
import de.kaiserpfalzedv.office.contacts.api.addresses.AddressType;
import de.kaiserpfalzedv.office.contacts.api.addresses.POBoxAddress;
import de.kaiserpfalzedv.office.geodata.api.City;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-10
 */
@Entity
@DiscriminatorValue("STREET")
public class JPAPOBoxAddress extends JPAPostalAddress implements POBoxAddress {
    private static final long serialVersionUID = 8573343656850577595L;

    @Column(name = "BOX_", length = 100, nullable = false)
    private String box;


    @SuppressWarnings("deprecation")
    @Deprecated
    public JPAPOBoxAddress() {
    }

    public JPAPOBoxAddress(
            @NotNull final UUID id,
            @NotNull final UUID tenant,
            @NotNull final AddressType type,
            @NotNull final Contact contact,
            @NotNull final City city,
            @NotNull final String box
    ) {
        super(id, tenant, type, contact, city);

        this.box = box;
    }


    @Override
    public String getPOBoxNumber() {
        return box;
    }

    void setPOBoxNumber(@NotNull final String box) {
        this.box = box;
    }
}
