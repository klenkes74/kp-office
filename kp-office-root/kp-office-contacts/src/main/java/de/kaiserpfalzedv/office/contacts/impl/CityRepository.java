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

package de.kaiserpfalzedv.office.contacts.impl;

import de.kaiserpfalzedv.commons.api.data.Pageable;
import de.kaiserpfalzedv.commons.api.data.PagedListable;
import de.kaiserpfalzedv.office.contacts.api.city.City;
import de.kaiserpfalzedv.office.contacts.api.city.Country;

/**
 * The repository for cities.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-09
 */
public interface CityRepository {
    PagedListable<? extends Country> retrieveCountries();

    PagedListable<? extends City> retrieve(Country country, String postalCode, Pageable page);

    PagedListable<? extends City> retrieve(Country country, Pageable page);

    PagedListable<? extends City> retrieve(String cityName, Pageable page);
}
