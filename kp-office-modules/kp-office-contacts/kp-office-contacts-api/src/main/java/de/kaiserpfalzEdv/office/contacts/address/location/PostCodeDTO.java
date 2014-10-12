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

import de.kaiserpfalzEdv.office.core.KPOEntityDTO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class PostCodeDTO extends KPOEntityDTO implements PostCode {
    private static final long serialVersionUID = 7259563519553303989L;

    private Country country;

    @SuppressWarnings("deprecation")
    @Deprecated // Only for Jackson, JAX-B and JPA!
    public PostCodeDTO() {
    }


    public PostCodeDTO(@NotNull final UUID id,
                       @NotNull final String name,
                       @NotNull final String code,
                       @NotNull final Country country) {
        super(id, name, code);

        setCountry(country);
    }


    @Override
    public String getCode() {
        return (country != null ? country.getPostalPrefix() + "-" : "") + getDisplayNumber();
    }


    @Override
    public Country getCountry() {
        return country;
    }

    void setCountry(@NotNull final Country country) {
        this.country = country;
    }


    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .append(country)
                .build();
    }
}