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

package de.kaiserpfalzEdv.office.contacts.address.postal;

import de.kaiserpfalzEdv.office.contacts.location.Country;
import de.kaiserpfalzEdv.office.contacts.location.CountryDO;
import de.kaiserpfalzEdv.office.core.KPOEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class PostCodeDO extends KPOEntity implements PostCode {
    private static final long serialVersionUID = 5785596176406990376L;

    private CountryDO country;

    @SuppressWarnings("deprecation")
    @Deprecated // Only for Jackson, JAX-B and JPA!
    public PostCodeDO() {
    }


    public PostCodeDO(@NotNull final UUID id,
                      @NotNull final String name,
                      @NotNull final String code,
                      @NotNull final Country country) {
        super(id, name, code);

        setCountry(country);
    }

    public PostCodeDO(@NotNull final PostCode code) {
        super(code.getId(), code.getDisplayName(), code.getDisplayNumber());

        setCountry(code.getCountry());
    }


    @Override
    public String getCode() {
        return (country != null ? country.getPostalPrefix() + "-" : "") + getDisplayNumber();
    }


    @Override
    public CountryDO getCountry() {
        return country;
    }

    void setCountry(@NotNull final Country country) {
        this.country = (CountryDO) country;
    }


    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .append(country)
                .build();
    }
}