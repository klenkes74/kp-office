/*
 * Copyright 2017 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzedv.office.contacts.api.city;

import de.kaiserpfalzedv.commons.api.data.paging.Pageable;
import de.kaiserpfalzedv.commons.api.data.paging.PagedListable;
import de.kaiserpfalzedv.commons.api.init.Closeable;
import de.kaiserpfalzedv.commons.api.init.Initializable;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-08
 */
public interface CityService extends Initializable, Closeable {
    /**
     * @return all available countries of this system.
     */
    PagedListable<? extends Country> retrieveCountries();

    /**
     * @param country    The country of the city.
     * @param postalCode The postal code of the city.
     * @param pageSize   The size of the page to return.
     *
     * @return the city specified by the country and the postal code.
     */
    PagedListable<? extends City> retrieve(final Country country, final String postalCode, int pageSize);

    /**
     * @param country    The country of the city.
     * @param postalCode The postal code of the city.
     * @param page       The page to retrieve.
     *
     * @return the city specified by the country and the postal code.
     */
    PagedListable<? extends City> retrieve(final Country country, final String postalCode, final Pageable page);

    /**
     * @param country  The country of the cities to retrieve.
     * @param pageSize The size of the page.
     *
     * @return The first page of the cities of the country.
     */
    PagedListable<? extends City> retrieve(final Country country, final int pageSize);

    /**
     * @param country The country of the cities to retrieve.
     * @param page    The page definition of the cities to retrieve.
     *
     * @return The requested page of the cities of this country.
     */
    PagedListable<? extends City> retrieve(final Country country, final Pageable page);

    /**
     * @param cityName The name (or part of the name) of the city.
     * @param pageSize The size of the page.
     *
     * @return All cities matching the name or the part of the name.
     */
    PagedListable<? extends City> retrieve(final String cityName, final int pageSize);

    /**
     * @param cityName The name (or part of the name) of the city.
     * @param page     The page definition of the cities to retrieve.
     *
     * @return All cities matching the name or the part of the name.
     */
    PagedListable<? extends City> retrieve(final String cityName, final Pageable page);
}
