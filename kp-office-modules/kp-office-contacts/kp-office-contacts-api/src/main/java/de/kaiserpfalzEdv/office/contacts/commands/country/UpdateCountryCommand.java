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
import de.kaiserpfalzEdv.office.contacts.location.CountryBuilder;
import de.kaiserpfalzEdv.office.contacts.location.CountryDTO;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author klenkes
 * @since 2014Q
 */
public class UpdateCountryCommand extends CountryBaseCommand {
    private static final long serialVersionUID = 1L;


    private CountryDTO country;


    @Deprecated // Only for Jackson, JAX-B and JPA!
    public UpdateCountryCommand() {
    }

    @SuppressWarnings("deprecation")
    public UpdateCountryCommand(@NotNull final UUID id, @NotNull final Country country) {
        super(id);
        setCountry(country);
    }


    public Country getCountry() {
        return country;
    }

    @SuppressWarnings("deprecation")
    @Deprecated // Only for Jackson JAX-B and JPA!
    public void setCountry(@NotNull final Country country) {
        setCountry(new CountryBuilder().withCountry(country).build());
    }

    @Deprecated // Only for Jackson JAX-B and JPA!
    public void setCountry(@NotNull final CountryDTO country) {
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
