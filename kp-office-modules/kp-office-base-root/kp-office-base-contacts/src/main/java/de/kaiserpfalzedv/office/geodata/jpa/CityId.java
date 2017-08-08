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

package de.kaiserpfalzedv.office.geodata.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import de.kaiserpfalzedv.office.geodata.api.Country;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * The CityId consists of the two-letter country code and the postal code of the city.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-08
 */
@Embeddable
public class CityId implements Serializable {
    private static final long serialVersionUID = 5569318709027909269L;

    @Column(name = "COUNTRY_", length = 2, updatable = false, insertable = false)
    @Enumerated(EnumType.STRING)
    private Country country;

    @Column(name = "POSTAL_CODE_", length = 20, updatable = false, insertable = false)
    private String code;

    @Column(name = "PLACE_NAME_", length = 100, updatable = false, insertable = false)
    private String name;

    @Deprecated // only JPA
    public CityId() {}

    public CityId(final Country country, final String code, final String name) {
        this.country = country;
        this.code = code;
        this.name = name;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(country)
                .append(code)
                .append(name)
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }

        CityId rhs = (CityId) obj;
        return new EqualsBuilder()
                .append(this.getName(), rhs.getName())
                .append(this.getCode(), rhs.getCode())
                .append(this.getCountry(), rhs.getCountry())
                .isEquals();
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public Country getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("country", country)
                .append("code", code)
                .append("name", name)
                .toString();
    }
}
