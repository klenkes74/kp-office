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

import de.kaiserpfalzEdv.office.contacts.address.phone.AreaCode;
import de.kaiserpfalzEdv.office.contacts.address.phone.AreaCodeDTO;
import de.kaiserpfalzEdv.office.contacts.address.postal.PostCode;
import de.kaiserpfalzEdv.office.contacts.address.postal.PostCodeDTO;
import de.kaiserpfalzEdv.office.core.KPOEntityDTO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class CityDTO extends KPOEntityDTO implements City {
    private static final long serialVersionUID = -7228761864499189014L;

    private final HashSet<PostCodeDTO> postCodes = new HashSet<>();
    private final HashSet<AreaCodeDTO> areaCodes = new HashSet<>();
    private CountryDTO country;


    @SuppressWarnings("deprecation")
    @Deprecated // Only for Jackson, JAX-B or JPA!
    public CityDTO() {
    }

    CityDTO(@NotNull final UUID id,
            @NotNull final String name,
            @NotNull final String number,
            @NotNull final Country country,
            @NotNull final Set<PostCodeDTO> postCodes,
            @NotNull final Set<AreaCodeDTO> areaCodes) {
        super(id, name, number);

        setCountry(country);
        setPostCodes(postCodes);
        setAreaCodes(areaCodes);
    }


    @Override
    public CountryDTO getCountry() {
        return country;
    }

    void setCountry(Country country) {
        setCountry(new CountryBuilder().withCountry(country).build());
    }

    void setCountry(CountryDTO country) {
        this.country = country;
    }

    @Override
    public Set<PostCodeDTO> getPostCodes() {
        return Collections.unmodifiableSet(postCodes);
    }

    public void setPostCodes(Set<PostCodeDTO> postCodes) {
        this.postCodes.clear();

        if (postCodes != null) {
            this.postCodes.addAll(postCodes);
        }
    }

    public void addPostCode(final PostCodeDTO postCode) {
        if (!postCodes.contains(postCode)) {
            postCodes.add(postCode);
        }
    }

    @Override
    public void addPostCode(final PostCode postCode) {
        addPostCode(new PostCodeDTO(postCode));
    }

    public void removePostCode(final PostCodeDTO postCode) {
        postCodes.remove(postCode);
    }

    @Override
    public void removePostCode(final PostCode postCode) {
        removePostCode(new PostCodeDTO(postCode));
    }


    @Override
    public Set<AreaCodeDTO> getAreaCodes() {
        return Collections.unmodifiableSet(areaCodes);
    }

    public void setAreaCodes(Set<AreaCodeDTO> areaCodes) {
        this.areaCodes.clear();

        if (areaCodes != null) {
            this.areaCodes.addAll(areaCodes);
        }
    }

    public void addAreaCode(final AreaCodeDTO areaCode) {
        if (!this.areaCodes.contains(areaCode)) {
            this.areaCodes.add(areaCode);
        }
    }

    @Override
    public void addAreaCode(final AreaCode areaCode) {
        addAreaCode(new AreaCodeDTO(areaCode));
    }

    public void removeAreaCode(final AreaCodeDTO areaCode) {
        this.areaCodes.remove(areaCode);
    }

    @Override
    public void removeAreaCode(final AreaCode areaCode) {
        removeAreaCode(new AreaCodeDTO(areaCode));
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
