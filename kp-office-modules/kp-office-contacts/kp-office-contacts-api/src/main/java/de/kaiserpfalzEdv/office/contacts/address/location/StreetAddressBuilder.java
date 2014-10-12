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

package de.kaiserpfalzEdv.office.contacts.address.location;

import de.kaiserpfalzEdv.commons.ValidationException;
import org.apache.commons.lang3.builder.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * @author klenkes
 * @since 2014Q
 */
public class StreetAddressBuilder extends PostalAddressBuilderBase implements Builder<StreetAddress> {
    private static final Logger LOG = LoggerFactory.getLogger(StreetAddressBuilder.class);

    private String co;
    private String street;
    private String houseNumber;
    private String houseNumberAdd;


    @Override
    public StreetAddress build() {
        validate();

        return new StreetAddressDTO(getId(), getName(), getNumber(), getCity(), getPostCode(), getType(), getUsage(),
                getCo(), getStreet(), getHouseNumber(), getHouseNumberAdd());
    }


    public void validate() {
        HashSet<String> reasons = new HashSet<>();

        generateValidationErrorList(reasons);

        if (!reasons.isEmpty()) {
            throw new ValidationException("Can't build street address!", reasons);
        }
    }

    @Override
    protected void generateValidationErrorList(@NotNull Collection<String> reasons) {
        super.generateValidationErrorList(reasons);

        if (isBlank(street)) reasons.add("Street may not be blank at a street address!");
    }


    String getCo() {
        return co;
    }

    public StreetAddressBuilder withContactOver(String co) {
        this.co = co;

        return this;
    }

    String getStreet() {
        return street;
    }

    public StreetAddressBuilder withStreet(String street) {
        this.street = street;

        return this;
    }

    String getHouseNumber() {
        return houseNumber;
    }

    public StreetAddressBuilder withHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;

        return this;
    }

    String getHouseNumberAdd() {
        return houseNumberAdd;
    }

    public StreetAddressBuilder withHouseNumberAdd(String houseNumberAdd) {
        this.houseNumberAdd = houseNumberAdd;

        return this;
    }
}
