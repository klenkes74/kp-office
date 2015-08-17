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
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 25.02.15 14:11
 */
@Embeddable
public class KPOInternalCoordinates implements GeoCoordinates {
    private static final long serialVersionUID = -5015763720704076501L;

    @Column(name = "lat")
    private Double latitude;

    @Column(name = "lon")
    private Double longitude;


    @Deprecated // Only for Jackson, JAX-B, JPA, ...
    protected KPOInternalCoordinates() {}

    KPOInternalCoordinates(final double latitude, final double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }


    @Override
    public Double getLatitude() {
        return latitude;
    }

    @Override
    public Double getLongitude() {
        return longitude;
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
        KPOInternalCoordinates rhs = (KPOInternalCoordinates) obj;
        return new EqualsBuilder()
                .append(this.latitude, rhs.latitude)
                .append(this.longitude, rhs.longitude)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(latitude)
                .append(longitude)
                .toHashCode();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("lat", latitude)
                .append("lon", longitude)
                .toString();
    }
}
