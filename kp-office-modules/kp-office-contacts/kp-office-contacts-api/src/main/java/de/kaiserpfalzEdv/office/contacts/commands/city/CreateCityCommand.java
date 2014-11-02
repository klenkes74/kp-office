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

/**
 * @author klenkes
 * @since 2014Q
 */
public class CreateCityCommand extends CityBaseCommand {
    private static final long serialVersionUID = 1L;


    private CityDTO city;


    @SuppressWarnings("deprecation")
    @Deprecated // Only for Jackson, JAX-B and JPA!
    public CreateCityCommand() {
    }

    @SuppressWarnings("deprecation")
    public CreateCityCommand(@NotNull final City city) {
        super(city.getId());

        setCity(city);
    }

    public CityDTO getCity() {
        return city;
    }

    @SuppressWarnings("deprecation")
    @Deprecated // Only for Jackson JAX-B and JPA!
    public void setCity(@NotNull final City city) {
        setCity(new CityBuilder().withCity(city).build());
    }

    @Deprecated // Only for Jackson JAX-B and JPA!
    public void setCity(@NotNull final CityDTO city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append(city)
                .toString();
    }
}
