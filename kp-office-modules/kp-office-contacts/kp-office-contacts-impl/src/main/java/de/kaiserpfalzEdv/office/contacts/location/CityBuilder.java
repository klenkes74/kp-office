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

import de.kaiserpfalzEdv.commons.BuilderValidationException;
import de.kaiserpfalzEdv.office.contacts.address.phone.AreaCode;
import de.kaiserpfalzEdv.office.contacts.address.phone.AreaCodeDO;
import de.kaiserpfalzEdv.office.contacts.address.postal.PostCode;
import de.kaiserpfalzEdv.office.contacts.address.postal.PostCodeDO;
import de.kaiserpfalzEdv.office.core.DisplayNumberGenerator;
import de.kaiserpfalzEdv.office.core.NumberGenerationFailureException;
import org.apache.commons.lang3.builder.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

/**
 * @author klenkes
 * @since 2014Q
 */
public class CityBuilder implements Builder<CityDO> {
    private static final Logger LOG = LoggerFactory.getLogger(CityBuilder.class);
    private final HashSet<PostCode> postCodes = new HashSet<>();
    private final HashSet<AreaCode> areaCodes = new HashSet<>();
    private UUID id;
    private String displayName;
    private String displayNumber;
    private Country country;
    private DisplayNumberGenerator displayNumberGenerator;


    public CityDO build() {
        setDefaults();
        validate();

        return new CityDO(
                id, displayName, displayNumber,
                country,
                convertPostCodesToPostCodeDTOs(), convertAreaCodesToAreaCodeDTOs()
        );
    }

    protected void setDefaults() {
        if (id == null) id = UUID.randomUUID();

        if (displayNumberGenerator == null) displayNumberGenerator = new DefaultCityNumberGenerator();
        if (displayNumber == null) try {
            displayNumber = displayNumberGenerator.generate(id, displayName, displayNumber, country);
        } catch (NumberGenerationFailureException e) {
            LOG.error("Can't generate display number for city!", e);
        }
    }

    public void validate() {
        HashSet<String> reasons = new HashSet<>();

        generateValidationErrorList(reasons);

        if (!reasons.isEmpty()) {
            throw new BuilderValidationException("Can't build city information!", reasons);
        }
    }

    protected void generateValidationErrorList(@NotNull Collection<String> reasons) {
        if (displayName == null)
            reasons.add("No valid name given!");

        if (displayNumber == null)
            reasons.add("No valid number given!");

        if (country == null)
            reasons.add("No valid country information given!");
    }


    protected HashSet<PostCodeDO> convertPostCodesToPostCodeDTOs() {
        HashSet<PostCodeDO> result = new HashSet<>(postCodes.size());
        postCodes.parallelStream().forEach(c -> result.add(new PostCodeDO(c)));
        return result;
    }

    protected HashSet<AreaCodeDO> convertAreaCodesToAreaCodeDTOs() {
        HashSet<AreaCodeDO> result = new HashSet<>(areaCodes.size());
        areaCodes.parallelStream().forEach(c -> result.add(new AreaCodeDO(c)));
        return result;
    }


    public CityBuilder withCity(@NotNull final City orig) {
        withCountry(orig.getCountry());
        withName(orig.getDisplayName());
        withNumber(orig.getDisplayNumber());
        withAreaCodes(orig.getAreaCodes());
        withPostCodes(orig.getPostCodes());

        return this;
    }


    public CityBuilder withCountry(@NotNull final Country country) {
        this.country = country;

        return this;
    }


    public CityBuilder withName(@NotNull final String name) {
        displayName = name;

        return this;
    }

    public CityBuilder withNumber(@NotNull final String number) {
        displayNumber = number;

        return this;
    }


    public CityBuilder withPostCodes(@NotNull final Collection<? extends PostCode> postCodes) {
        this.postCodes.clear();

        if (postCodes != null)
            this.postCodes.addAll(postCodes);

        return this;
    }

    public CityBuilder addPostCode(@NotNull final PostCode postCode) {
        this.postCodes.add(postCode);

        return this;
    }

    public CityBuilder removePostCode(@NotNull final PostCode postCode) {
        this.postCodes.remove(postCode);

        return this;
    }


    public CityBuilder withAreaCodes(@NotNull final Collection<? extends AreaCode> areaCodes) {
        this.areaCodes.clear();

        if (areaCodes != null)
            this.areaCodes.addAll(areaCodes);

        return this;
    }

    public CityBuilder addAreaCode(@NotNull final AreaCode areaCode) {
        this.areaCodes.add(areaCode);

        return this;
    }

    public CityBuilder removeAreaCode(@NotNull final AreaCode areaCode) {
        this.areaCodes.remove(areaCode);

        return this;
    }
}
