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

package de.kaiserpfalzEdv.office.contacts.commands.city;

import de.kaiserpfalzEdv.office.contacts.location.City;
import de.kaiserpfalzEdv.office.contacts.location.CityBuilder;
import de.kaiserpfalzEdv.office.contacts.location.CityDTO;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Changes the city data for the given city.
 *
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
public class UpdateCityCommand extends CityBaseCommand {
    private static final long serialVersionUID = 1L;


    private CityDTO city;


    @SuppressWarnings({"UnusedDeclaration", "deprecation"})
    @Deprecated // Only for Jackson, JAX-B and JPA!
    public UpdateCityCommand() {
    }

    @SuppressWarnings("deprecation")
    public UpdateCityCommand(@NotNull final UUID id, @NotNull final City city) {
        super(id);

        setCity(city);
    }


    public CityDTO getCity() {
        return city;
    }

    @Deprecated // Only for Jackson JAX-B and JPA!
    public void setCity(@NotNull final CityDTO city) {
        this.city = city;
    }

    @SuppressWarnings("deprecation")
    @Deprecated
    public void setCity(@NotNull final City city) {
        setCity(new CityBuilder().withCity(city).build());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append(city)
                .toString();
    }
}
