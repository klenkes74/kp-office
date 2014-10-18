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

package de.kaiserpfalzEdv.office.contacts.commands.country;

import de.kaiserpfalzEdv.office.contacts.location.Country;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class CreateCountryCommand extends CountryBaseCommand {
    private static final long serialVersionUID = 1L;


    private Country country;


    @Deprecated // Only for Jackson, JAX-B and JPA!
    public CreateCountryCommand() {
    }

    @SuppressWarnings("deprecation")
    public CreateCountryCommand(@NotNull final Country country) {
        setCountry(country);
    }

    public Country getCountry() {
        return country;
    }


    @Deprecated // Only for Jackson JAX-B and JPA!
    public void setCountry(@NotNull final Country country) {
        this.country = country;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append(country)
                .toString();
    }
}
