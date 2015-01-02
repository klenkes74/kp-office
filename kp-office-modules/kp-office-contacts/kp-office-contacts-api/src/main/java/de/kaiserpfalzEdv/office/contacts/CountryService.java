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

import de.kaiserpfalzEdv.commons.paging.Page;
import de.kaiserpfalzEdv.commons.paging.Pageable;
import de.kaiserpfalzEdv.office.contacts.location.Country;
import de.kaiserpfalzEdv.office.contacts.location.CountryAlreadyExistsException;
import de.kaiserpfalzEdv.office.contacts.location.CountryNotRemovedException;
import de.kaiserpfalzEdv.office.contacts.location.InvalidCountryException;
import de.kaiserpfalzEdv.office.contacts.location.NoSuchCountryException;
import de.kaiserpfalzEdv.office.core.EntityQueryBase;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public interface CountryService {
    /**
     * Creates a new country.
     *
     * @param country Country to be saved.
     * @throws CountryAlreadyExistsException The country already exists.
     * @throws InvalidCountryException       The country data is inconsistent.
     */
    public void createCountry(@NotNull final Country country) throws CountryAlreadyExistsException, InvalidCountryException;

    /**
     * Retrieves a country.
     *
     * @param id The id of the country to be retrieved.
     * @return The country with the given id.
     * @throws NoSuchCountryException There is no country with that id.
     */
    public Country retrieveCountry(@NotNull final UUID id) throws NoSuchCountryException;

    /**
     * Retrieves all countries matching the query.
     *
     * @param query The query to retrieve all countries for.
     * @param paging The page definition.
     * @return the cities matching the query.
     */
    public Page<Country> retrieveCountry(@NotNull final EntityQueryBase query, @NotNull final Pageable paging);

    /**
     * Updates the country data.
     *
     * @param id      Id of the country witch data is to be changed.
     * @param country The new country data to be saved.
     * @throws NoSuchCountryException  There is no country with the given id.
     * @throws InvalidCountryException The country data is inconsistent.
     */
    public void updateCountry(@NotNull final UUID id, @NotNull final Country country) throws NoSuchCountryException, InvalidCountryException;

    /**
     * Deletes the country.
     *
     * @param id ID of the country to be removed.
     * @throws CountryNotRemovedException The country can not be removed.
     */
    public void deleteCountry(@NotNull final UUID id) throws CountryNotRemovedException;
}
