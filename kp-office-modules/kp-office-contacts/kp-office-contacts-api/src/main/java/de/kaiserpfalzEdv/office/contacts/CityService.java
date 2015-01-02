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

package de.kaiserpfalzEdv.office.contacts;

import de.kaiserpfalzEdv.office.contacts.location.City;
import de.kaiserpfalzEdv.office.contacts.location.CityAlreadyExistsException;
import de.kaiserpfalzEdv.office.contacts.location.CityNotRemovedException;
import de.kaiserpfalzEdv.office.contacts.location.InvalidCityException;
import de.kaiserpfalzEdv.office.contacts.location.NoSuchCityException;
import de.kaiserpfalzEdv.office.core.EntityQueryBase;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public interface CityService {
    /**
     * Creates a new city.
     *
     * @param city City to be saved.
     * @throws de.kaiserpfalzEdv.office.contacts.location.CityAlreadyExistsException The city already exists.
     * @throws de.kaiserpfalzEdv.office.contacts.location.InvalidCityException       The city data is inconsistent.
     */
    public void createCity(@NotNull final City city) throws CityAlreadyExistsException, InvalidCityException;

    /**
     * Retrieves a city.
     *
     * @param id Id of the city to be retrieved.
     * @return The city with the given id.
     * @throws de.kaiserpfalzEdv.office.contacts.location.NoSuchCityException There is no city with that ID.
     */
    public City retrieveCity(@NotNull final UUID id) throws NoSuchCityException;

    /**
     * Retrieves a city.
     *
     * @param query The query to retrieve all cities for.
     * @return the cities matching the query.
     */
    public Iterable<City> retrieveCity(@NotNull final EntityQueryBase query);

    /**
     * Updates the city data.
     *
     * @param id   Id of the city which data is to be changed.
     * @param city The new city data to be saved.
     * @throws de.kaiserpfalzEdv.office.contacts.location.NoSuchCityException  There is no city with the given id.
     * @throws de.kaiserpfalzEdv.office.contacts.location.InvalidCityException The city data is inconsistent.
     */
    public void updateCity(@NotNull final UUID id, @NotNull final City city) throws NoSuchCityException, InvalidCityException;

    /**
     * Deletes the city.
     *
     * @param id ID of the city to be removed.
     * @throws de.kaiserpfalzEdv.office.contacts.location.CityNotRemovedException The city can not be removed.
     */
    public void deleteCity(@NotNull final UUID id) throws CityNotRemovedException;
}
