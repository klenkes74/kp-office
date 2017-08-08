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

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import de.kaiserpfalzedv.office.geodata.api.AdministrativeEntity;
import de.kaiserpfalzedv.office.geodata.api.City;
import de.kaiserpfalzedv.office.geodata.api.Country;
import de.kaiserpfalzedv.office.geodata.api.Position;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-08
 */
@SuppressWarnings("unused")
@Entity
@Table(name = "CITIES")
public class CityJPA implements City {
    private static final long serialVersionUID = -7856052277648326779L;


    @EmbeddedId
    private CityId id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "ADMIN1_NAME_")),
            @AttributeOverride(name = "code", column = @Column(name = "ADMIN1_CODE_")),
    })
    private AdministrativeEntityJPA state;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "ADMIN2_NAME_")),
            @AttributeOverride(name = "code", column = @Column(name = "ADMIN2_CODE_")),
    })
    private AdministrativeEntityJPA province;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "ADMIN3_NAME_")),
            @AttributeOverride(name = "code", column = @Column(name = "ADMIN3_CODE_")),
    })
    private AdministrativeEntityJPA community;

    @Embedded
    private PositionJPA position;

    @Deprecated // only for JPA
    public CityJPA() {

    }

    public CityJPA(
            final CityId id,
            final AdministrativeEntityJPA state,
            final AdministrativeEntityJPA province,
            final AdministrativeEntityJPA community,
            final PositionJPA position
    ) {
        this.id = id;
        this.state = state;
        this.province = province;
        this.community = community;
        this.position = position;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.getCountry().getLocale())
                .append(this.getCountry().getPostalName())
                .append(this.getCode())
                .append(this.getName())
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
        if (!City.class.isAssignableFrom(obj.getClass())) {
            return false;
        }

        City rhs = (City) obj;
        return new EqualsBuilder()
                .append(this.getCountry().getLocale(), rhs.getCountry().getLocale())
                .append(this.getCountry().getPostalName(), rhs.getCountry().getPostalName())
                .append(this.getCode(), rhs.getCode())
                .append(this.getName(), rhs.getName())
                .isEquals();
    }

    @Override
    public Country getCountry() {
        return id.getCountry();
    }

    @Override
    public String getCode() {
        return id.getCode();
    }

    @Override
    public String getName() {
        return id.getName();
    }

    /**
     * @return The state level administrative entry
     */
    @Override
    public AdministrativeEntity getState() {
        return state;
    }

    /**
     * @return the county/province level administrative entry.
     */
    @Override
    public AdministrativeEntity getProvince() {
        return province;
    }

    /**
     * @return the community level administrative entry.
     */
    @Override
    public AdministrativeEntity getCommunity() {
        return community;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public String toString() {
        ToStringBuilder result = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append(id);

        if (position != null) {
            result.append(position);
        }

        if (state != null) {
            result.append("state", state);
        }

        if (province != null) {
            result.append("province", province);
        }

        if (community != null) {
            result.append("community", community);
        }

        return result.toString();
    }
}
