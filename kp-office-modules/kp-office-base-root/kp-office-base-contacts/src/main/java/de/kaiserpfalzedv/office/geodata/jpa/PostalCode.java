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
 * The PostalCode consists of the two-letter country code and the postal code of the city.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-08
 */
@Embeddable
public class PostalCode implements Serializable {
    private static final long serialVersionUID = -7472355432056014008L;

    @Column(name = "COUNTRY_", length = 2, updatable = false, insertable = false)
    @Enumerated(EnumType.STRING)
    private Country country;

    @Column(name = "POSTAL_CODE_", length = 20, updatable = false, insertable = false)
    private String code;

    @Deprecated // only JPA
    public PostalCode() {}

    public PostalCode(final Country country, final String code) {
        this.country = country;
        this.code = code;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(country)
                .append(code)
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

        PostalCode rhs = (PostalCode) obj;
        return new EqualsBuilder()
                .append(this.getCode(), rhs.getCode())
                .append(this.getCountry(), rhs.getCountry())
                .isEquals();
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
                .toString();
    }
}
