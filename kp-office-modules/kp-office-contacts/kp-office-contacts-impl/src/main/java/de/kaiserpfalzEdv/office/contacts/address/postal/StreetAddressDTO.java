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

package de.kaiserpfalzEdv.office.contacts.address.postal;

import de.kaiserpfalzEdv.office.contacts.address.AddressType;
import de.kaiserpfalzEdv.office.contacts.address.AddressUsage;
import de.kaiserpfalzEdv.office.contacts.location.City;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author klenkes
 * @since 2014Q
 */
@Entity
@DiscriminatorValue("STREET")
public class StreetAddressDTO extends PostalAddressDTO implements StreetAddress {
    private static final long serialVersionUID = -4265964191255501773L;

    private static final Logger LOG = LoggerFactory.getLogger(StreetAddressDTO.class);

    private String co;
    private String street;
    private String houseNumber;
    private String houseNumberAdd;

    @SuppressWarnings("deprecation")
    @Deprecated // Only for Jackson, JAX-B and JPA!
    public StreetAddressDTO() {
    }


    StreetAddressDTO(@NotNull final UUID id,
                     @NotNull final String name,
                     @NotNull final String number,
                     @NotNull final City city,
                     @NotNull final PostCode postCode,
                     @NotNull final AddressType type,
                     @NotNull final AddressUsage usage,
                     final String co,
                     @NotNull final String street,
                     final String houseNumber,
                     final String houseNumberAdd,
                     @NotNull final UUID tenantId) {
        super(id, name, number, city, postCode, type, usage, tenantId);

        setContactOver(co);
        setStreet(street);
        setHouseNumber(houseNumber);
        setHouseNumberAdd(houseNumberAdd);
    }

    @Override
    public String getContactOver() {
        return co;
    }

    void setContactOver(final String co) {
        this.co = co;
    }


    @Override
    public String getStreet() {
        return street;
    }

    void setStreet(@NotNull final String street) {
        this.street = street;
    }


    @Override
    public String getHouseNumber() {
        return houseNumber;
    }

    void setHouseNumber(final String houseNumber) {
        this.houseNumber = houseNumber;
    }


    @Override
    public String getHouseNumberAdd() {
        return houseNumberAdd;
    }

    void setHouseNumberAdd(final String houseNumberAdd) {
        this.houseNumberAdd = houseNumberAdd;
    }


    public String toString() {
        ToStringBuilder result = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString());

        if (co != null) {
            result.append("c/o", co);
        }

        result.append("street", street);

        if (houseNumber != null) {
            result.append("nr", houseNumber + (houseNumberAdd != null ? houseNumberAdd : ""));
        }

        return result.build();
    }
}
