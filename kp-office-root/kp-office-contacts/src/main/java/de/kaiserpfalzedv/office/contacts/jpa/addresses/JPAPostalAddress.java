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
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.office.contacts.api.Contact;
import de.kaiserpfalzedv.office.contacts.api.addresses.AddressType;
import de.kaiserpfalzedv.office.contacts.api.addresses.PostalAddress;
import de.kaiserpfalzedv.office.contacts.api.city.City;
import de.kaiserpfalzedv.office.contacts.jpa.city.JPACity;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-10
 */
@Entity
@Table(name = "ADDRESSES_POSTAL")
public abstract class JPAPostalAddress extends JPAAddress implements PostalAddress {
    private static final long serialVersionUID = 8787995899632203268L;

    @ManyToOne(
            targetEntity = JPACity.class,
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name = "CITY_",
            referencedColumnName = "ID_",
            nullable = false
    )
    private City city;


    @SuppressWarnings({"deprecation", "DeprecatedIsStillUsed"})
    @Deprecated
    public JPAPostalAddress() {}

    public JPAPostalAddress(
            @NotNull final UUID id,
            @NotNull final UUID tenant,
            @NotNull final AddressType type,
            @NotNull final Contact contact,
            @NotNull final City city
    ) {
        super(id, tenant, type, contact);

        this.city = city;
    }

    @Override
    public City getCity() {
        return city;
    }

    public void setCity(final City city) {
        this.city = city;
    }
}
