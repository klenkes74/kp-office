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

package de.kaiserpfalzEdv.office.contacts.geodb.impl;

import de.kaiserpfalzEdv.office.contacts.geodb.GeoCoordinates;
import de.kaiserpfalzEdv.office.contacts.geodb.PostCode;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 25.02.15 14:06
 */
@Entity(name = "GeoDbPostCode")
@Table(schema = "contacts", catalog = "contacts", name = "geodb_postcodes")
public class KPOInternalPostCodeImpl implements PostCode {
    private static final long serialVersionUID = 2529963953411493897L;

    @Id
    @GeneratedValue
    @Column(name = "loc_id", nullable = false, insertable = true, updatable = false, unique = true)
    private int id;

    @Column(name = "postcode", length = 10, nullable = false)
    private String postCode;

    @Column(name = "country", length = 5, nullable = true)
    private String country;

    @Column(name = "city", length = 255, nullable = false)
    private String city;

    @Embedded
    private KPOInternalCoordinates coordinates;


    @Deprecated // Only for Jackson, JAX-B, JPA, ...
    protected KPOInternalPostCodeImpl() {}

    KPOInternalPostCodeImpl(final String country, final String postCode, final String city) {
        this.country = country;
        this.postCode = postCode;
        this.city = city;
    }


    @Override
    public int getId() {
        return id;
    }

    @Override
    public GeoCoordinates getCoordinates() {
        return coordinates;
    }

    @Override
    public String getDisplayName() {
        return city;
    }

    @Override
    public String getDisplayNumber() {
        return postCode;
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
        KPOInternalPostCodeImpl rhs = (KPOInternalPostCodeImpl) obj;
        return new EqualsBuilder()
                .append(this.id, rhs.id)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .toHashCode();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append(id)
                .append(postCode)
                .append(city)
                .toString();
    }
}
