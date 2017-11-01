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

package de.kaiserpfalzedv.office.contacts.jpa.city;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.office.contacts.api.city.AdministrativeEntity;
import de.kaiserpfalzedv.office.contacts.api.city.City;
import de.kaiserpfalzedv.office.contacts.api.city.Country;
import de.kaiserpfalzedv.office.contacts.api.city.Position;
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
@NamedQueries({
        @NamedQuery(name = "City.ByPostalCode", query = "select c from JPACity c where c.postalCode.country=:country and c.postalCode.code=:code"),
        @NamedQuery(name = "City.ByPostalCode.count", query = "select count(c) from JPACity c where c.postalCode.country=:country and c.postalCode.code=:code"),
        @NamedQuery(name = "City.ByCountry", query = "select c from JPACity c where c.postalCode.country=:country"),
        @NamedQuery(name = "City.ByCountry.count", query = "select count(c) from JPACity c where c.postalCode.country=:country"),
        @NamedQuery(name = "City.ByCityNme", query = "select c from JPACity c where c.name=:cityName"),
        @NamedQuery(name = "City.ByCityNme.count", query = "select count(c) from JPACity c where c.name=:cityName"),
})
public class JPACity implements City {
    private static final long serialVersionUID = -8558359050864128104L;

    @Id
    @Column(name = "ID_", updatable = false, nullable = false, unique = true)
    private Long id;

    @Version
    @Column(name = "VERSION_", nullable = false)
    private Long version = 0L;

    @Embedded
    private JPAPostalCode postalCode;

    @Column(name = "PLACE_NAME_", length = 100, updatable = false, insertable = false)
    private String name;


    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "ADMIN1_NAME_")),
            @AttributeOverride(name = "code", column = @Column(name = "ADMIN1_CODE_")),
    })
    private JPAAdministrativeEntity state;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "ADMIN2_NAME_")),
            @AttributeOverride(name = "code", column = @Column(name = "ADMIN2_CODE_")),
    })
    private JPAAdministrativeEntity province;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "ADMIN3_NAME_")),
            @AttributeOverride(name = "code", column = @Column(name = "ADMIN3_CODE_")),
    })
    private JPAAdministrativeEntity community;

    @Embedded
    private JPAPosition position;

    @Deprecated // only for JPA
    public JPACity() {

    }

    public JPACity(
            @NotNull final Long id,
            @NotNull final Long version,
            @NotNull final JPAPostalCode postalCode,
            @NotNull final String cityName,
            @NotNull final JPAAdministrativeEntity state,
            @NotNull final JPAAdministrativeEntity province,
            @NotNull final JPAAdministrativeEntity community,
            @NotNull final JPAPosition position
    ) {
        this.id = id;
        this.version = version;
        this.postalCode = postalCode;
        this.name = cityName;
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

        JPACity rhs = (JPACity) obj;
        return new EqualsBuilder()
                .append(this.getCountry().getLocale(), rhs.getCountry().getLocale())
                .append(this.getCountry().getPostalName(), rhs.getCountry().getPostalName())
                .append(this.getCode(), rhs.getCode())
                .append(this.getName(), rhs.getName())
                .isEquals();
    }

    @Override
    public String toString() {
        ToStringBuilder result = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append(System.identityHashCode(this))
                .append("id", id)
                .append("version", version)
                .append(postalCode)
                .append("name", name);

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

    @Override
    public Country getCountry() {
        return postalCode.getCountry();
    }

    @Override
    public String getCode() {
        return postalCode.getCode();
    }

    @Override
    public String getName() {
        return name;
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

    public Long getId() {
        return id;
    }

    public Long getVersion() {
        return version;
    }
}
