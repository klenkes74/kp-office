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

import de.kaiserpfalzEdv.office.contacts.address.phone.AreaCode;
import de.kaiserpfalzEdv.office.contacts.address.phone.AreaCodeDO;
import de.kaiserpfalzEdv.office.contacts.address.postal.PostCode;
import de.kaiserpfalzEdv.office.contacts.address.postal.PostCodeDO;
import de.kaiserpfalzEdv.office.core.impl.KPOEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
@Entity(name = "City")
@Table(
        name = "cntct_city", 
        uniqueConstraints = {}
)
public class CityDO extends KPOEntity implements City {
    private static final long serialVersionUID = 6127116796683601706L;

    private final HashSet<PostCodeDO> postCodes = new HashSet<>();
    private final HashSet<AreaCodeDO> areaCodes = new HashSet<>();
    private CountryDO country;


    @SuppressWarnings("deprecation")
    @Deprecated // Only for Jackson, JAX-B or JPA!
    protected CityDO() {
    }

    CityDO(@NotNull final UUID id,
           @NotNull final String name,
           @NotNull final String number,
           @NotNull final Country country,
           @NotNull final Set<PostCodeDO> postCodes,
           @NotNull final Set<AreaCodeDO> areaCodes) {
        super(id, name, number);

        setCountry(country);
        setPostCodes(postCodes);
        setAreaCodes(areaCodes);
    }


    @Override
    public CountryDO getCountry() {
        return country;
    }

    void setCountry(Country country) {
        setCountry(new CountryBuilder().withCountry(country).build());
    }

    void setCountry(CountryDO country) {
        this.country = country;
    }

    @Override
    public Set<PostCodeDO> getPostCodes() {
        return Collections.unmodifiableSet(postCodes);
    }

    public void setPostCodes(Set<PostCodeDO> postCodes) {
        this.postCodes.clear();

        if (postCodes != null) {
            this.postCodes.addAll(postCodes);
        }
    }

    public void addPostCode(final PostCodeDO postCode) {
        if (!postCodes.contains(postCode)) {
            postCodes.add(postCode);
        }
    }

    @Override
    public void addPostCode(final PostCode postCode) {
        addPostCode(new PostCodeDO(postCode));
    }

    public void removePostCode(final PostCodeDO postCode) {
        postCodes.remove(postCode);
    }

    @Override
    public void removePostCode(final PostCode postCode) {
        removePostCode(new PostCodeDO(postCode));
    }


    @Override
    public Set<AreaCodeDO> getAreaCodes() {
        return Collections.unmodifiableSet(areaCodes);
    }

    public void setAreaCodes(Set<AreaCodeDO> areaCodes) {
        this.areaCodes.clear();

        if (areaCodes != null) {
            this.areaCodes.addAll(areaCodes);
        }
    }

    public void addAreaCode(final AreaCodeDO areaCode) {
        if (!this.areaCodes.contains(areaCode)) {
            this.areaCodes.add(areaCode);
        }
    }

    @Override
    public void addAreaCode(final AreaCode areaCode) {
        addAreaCode(new AreaCodeDO(areaCode));
    }

    public void removeAreaCode(final AreaCodeDO areaCode) {
        this.areaCodes.remove(areaCode);
    }

    @Override
    public void removeAreaCode(final AreaCode areaCode) {
        removeAreaCode(new AreaCodeDO(areaCode));
    }


    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .append("country", getCountry())
                .append("postCodes", getPostCodes())
                .append("areaCodes", getAreaCodes())
                .build();
    }
}
