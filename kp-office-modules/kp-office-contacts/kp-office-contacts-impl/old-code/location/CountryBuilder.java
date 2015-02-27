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

package de.kaiserpfalzEdv.office.contacts.location;

import de.kaiserpfalzEdv.commons.BuilderValidationException;
import org.apache.commons.lang3.builder.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

/**
 * @author klenkes
 * @since 2014Q
 */
public class CountryBuilder implements Builder<CountryDO> {
    private static final Logger LOG = LoggerFactory.getLogger(CountryBuilder.class);


    private UUID id;
    private String displayName;
    private String displayNumber;

    private String iso2;
    private String iso3;

    private String phoneCountryCode;
    private String postalPrefix;


    public CountryDO build() {
        setDefaults();
        validate();

        return new CountryDO(id, displayName, displayNumber, iso2, iso3, phoneCountryCode, postalPrefix);
    }


    public void validate() {
        HashSet<String> reasons = new HashSet<>();

        generateValidationErrorList(reasons);

        if (!reasons.isEmpty()) {
            throw new BuilderValidationException("Can't build country information!", reasons);
        }
    }


    protected void setDefaults() {
        if (id == null) id = UUID.randomUUID();

        if (displayNumber == null) displayNumber = iso2;
        if (displayName == null) displayName = iso2;
    }


    protected void generateValidationErrorList(@NotNull Collection<String> reasons) {
        if (iso2 == null)
            reasons.add("Can't create a country without the ISO-2 code!");

        if (iso3 == null)
            reasons.add("Can't create a country without the ISO-3 code!");

        if (phoneCountryCode == null)
            reasons.add("Can't create a country without phone country code!");

        if (postalPrefix == null)
            reasons.add("Can't create a country without postal prefix!");
    }


    public CountryBuilder withCountry(@NotNull final Country orig) {
        withId(orig.getId());
        withDisplayName(orig.getDisplayName());
        withDisplayNumber(orig.getDisplayNumber());

        withIso2(orig.getIso2());
        withIso3(orig.getIso3());

        withPhoneCountryCode(orig.getPhoneCountryCode());
        withPostalPrefix(orig.getPostalPrefix());

        return this;
    }


    public CountryBuilder withId(@NotNull final UUID id) {
        this.id = id;

        return this;
    }

    public void clearId() {
        this.id = null;
    }


    public CountryBuilder withDisplayName(@NotNull final String displayName) {
        this.displayName = displayName;

        return this;
    }


    public CountryBuilder withDisplayNumber(@NotNull final String displayNumber) {
        this.displayNumber = displayNumber;

        return this;
    }


    public CountryBuilder withIso2(@NotNull final String iso2) {
        this.iso2 = iso2;

        return this;
    }


    public CountryBuilder withIso3(@NotNull final String iso3) {
        this.iso3 = iso3;

        return this;
    }


    public CountryBuilder withPhoneCountryCode(@NotNull final String phoneCountryCode) {
        this.phoneCountryCode = phoneCountryCode;

        return this;
    }


    public CountryBuilder withPostalPrefix(@NotNull final String postalPrefix) {
        this.postalPrefix = postalPrefix;

        return this;
    }
}
