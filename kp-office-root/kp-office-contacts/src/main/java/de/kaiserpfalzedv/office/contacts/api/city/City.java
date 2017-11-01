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

import java.io.Serializable;

/**
 * The city data for a special postal code.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-08
 */
public interface City extends Serializable {
    /**
     * @return The country code of the city.
     */
    Country getCountry();

    /**
     * @return The postal code of the city.
     */
    String getCode();

    /**
     * @return The name of the city.
     */
    String getName();

    /**
     * @return The first level of administrative entities (like Bundesländer within Germany or Austria)
     */
    AdministrativeEntity getState();

    /**
     * @return The second level of administrative entities (county or province)
     */
    AdministrativeEntity getProvince();

    /**
     * @return The third level of administrative entities (community)
     */
    AdministrativeEntity getCommunity();

    /**
     * @return The position of the city.
     */
    Position getPosition();
}
