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
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.office.contacts.api.Contact;
import de.kaiserpfalzedv.office.contacts.api.addresses.AddressType;
import de.kaiserpfalzedv.office.contacts.api.addresses.PostalStreetAddress;
import de.kaiserpfalzedv.office.geodata.api.City;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-10
 */
@Entity
@Table(name = "ADDRESSES_STREET")
public class JPAStreetAddress extends JPAPostalAddress implements PostalStreetAddress {
    private static final long serialVersionUID = -886008448530006200L;

    @Column(name = "STREET_", length = 100)
    private String street;
    @Column(name = "HOUSENUMBER_", length = 10)
    private String houseNumber;
    @Column(name = "HOUSENUMBER_ADD_", length = 10)
    private String houseNumberAdd;


    @SuppressWarnings("deprecation")
    @Deprecated
    public JPAStreetAddress() {
    }

    public JPAStreetAddress(
            @NotNull final UUID id,
            @NotNull final UUID tenant,
            @NotNull final AddressType type,
            @NotNull final Contact contact,
            @NotNull final City city,
            @NotNull final String street,
            @NotNull final String houseNumber,
            @NotNull final String houseNumberAdd
    ) {
        super(id, tenant, type, contact, city);

        this.street = street;
        this.houseNumber = houseNumber;
        this.houseNumberAdd = houseNumberAdd;
    }


    @Override
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    @Override
    public String getHouseNumberAdd() {
        return houseNumberAdd;
    }

    public void setHouseNumberAdd(String houseNumberAdd) {
        this.houseNumberAdd = houseNumberAdd;
    }
}
