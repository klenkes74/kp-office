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

package de.kaiserpfalzEdv.office.contacts.location;

import de.kaiserpfalzEdv.office.contacts.address.phone.CountryCode;
import de.kaiserpfalzEdv.office.contacts.address.phone.CountryCodeDTO;
import de.kaiserpfalzEdv.office.core.KPOEntityDTO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class CountryDTO extends KPOEntityDTO implements Country {
    private static final long serialVersionUID = 8045089034022092439L;

    private String iso2;
    private String iso3;
    private String phoneCountryCode;
    private String postalPrefix;


    @SuppressWarnings("deprecation")
    @Deprecated // only for Jackson, JAX-B and JPA
    public CountryDTO() {
    }


    public CountryDTO(@NotNull final UUID id, @NotNull final String displayName, @NotNull final String displayNumber,
                      @NotNull final String iso2, @NotNull final String iso3,
                      @NotNull final String phoneCountryCode, @NotNull final String postalPrefix) {
        super(id, displayName, displayNumber);

        setIso2(iso2);
        setIso3(iso3);
        setPhoneCountryCode(phoneCountryCode);
        setPostalPrefix(postalPrefix);
    }


    @Override
    public String getIso2() {
        return iso2;
    }

    void setIso2(@NotNull String iso2) {
        this.iso2 = iso2;
    }

    @Override
    public String getIso3() {
        return iso3;
    }

    void setIso3(@NotNull String iso3) {
        this.iso3 = iso3;
    }

    @Override
    public String getPhoneCountryCode() {
        return phoneCountryCode;
    }

    void setPhoneCountryCode(@NotNull String phoneCountryCode) {
        this.phoneCountryCode = phoneCountryCode;
    }

    public CountryCode getCountryCode() {
        return new CountryCodeDTO(this);
    }

    @Override
    public String getPostalPrefix() {
        return postalPrefix;
    }

    void setPostalPrefix(@NotNull String postalPrefix) {
        this.postalPrefix = postalPrefix;
    }


    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .append("iso2", iso2)
                .append("iso3", iso3)
                .append("cc", phoneCountryCode)
                .append("postal", postalPrefix)
                .build();
    }
}
