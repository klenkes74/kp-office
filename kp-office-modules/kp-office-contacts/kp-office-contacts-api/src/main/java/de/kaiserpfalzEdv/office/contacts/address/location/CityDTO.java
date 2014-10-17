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

import de.kaiserpfalzEdv.office.contacts.address.phone.AreaCode;
import de.kaiserpfalzEdv.office.contacts.address.postal.PostCode;
import de.kaiserpfalzEdv.office.core.KPOEntityDTO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class CityDTO extends KPOEntityDTO implements City {
    private static final long serialVersionUID = 7424076027124950641L;
    private final HashSet<PostCode> postCodes = new HashSet<>();
    private final HashSet<AreaCode> areaCodes = new HashSet<>();
    private Country country;


    @SuppressWarnings("deprecation")
    @Deprecated // Only for Jackson, JAX-B or JPA!
    public CityDTO() {
    }

    CityDTO(@NotNull final UUID id,
            @NotNull final String name,
            @NotNull final String number,
            @NotNull final Country country,
            @NotNull final Collection<PostCode> postCodes,
            @NotNull final Collection<AreaCode> areaCodes) {
        super(id, name, number);

        setCountry(country);
        setPostCodes(postCodes);
        setAreaCodes(areaCodes);
    }


    @Override
    public Country getCountry() {
        return country;
    }

    void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public Set<PostCode> getPostCode() {
        return Collections.unmodifiableSet(postCodes);
    }

    @Override
    public void setPostCodes(Collection<? extends PostCode> postCode) {
        this.postCodes.clear();

        if (postCode != null) {
            this.postCodes.addAll(postCode);
        }
    }

    @Override
    public void addPostCode(final PostCode postCode) {
        if (!postCodes.contains(postCode)) {
            postCodes.add(postCode);
        }
    }

    @Override
    public void removePostCode(final PostCode postCode) {
        if (postCodes.contains(postCode)) {
            postCodes.remove(postCode);
        }
    }


    @Override
    public Set<AreaCode> getAreaCodes() {
        return Collections.unmodifiableSet(areaCodes);
    }

    @Override
    public void setAreaCodes(Collection<? extends AreaCode> areaCodes) {
        this.areaCodes.clear();

        if (areaCodes != null) {
            this.areaCodes.addAll(areaCodes);
        }
    }

    @Override
    public void addAreaCode(final AreaCode areaCode) {
        if (!this.areaCodes.contains(areaCode)) {
            this.areaCodes.add(areaCode);
        }
    }

    @Override
    public void removeAreaCode(final AreaCode areaCode) {
        if (this.areaCodes.contains(areaCode)) {
            this.areaCodes.remove(areaCode);
        }
    }


    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .append("country", getCountry())
                .append("postCodes", getPostCode())
                .append("areaCodes", getAreaCodes())
                .build();
    }
}
