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

import de.kaiserpfalzEdv.commons.jee.paging.Page;
import de.kaiserpfalzEdv.commons.jee.paging.Pageable;
import de.kaiserpfalzEdv.office.contacts.address.phone.AreaCode;
import de.kaiserpfalzEdv.office.contacts.address.phone.AreaCodeAlreadyExistsException;
import de.kaiserpfalzEdv.office.contacts.address.phone.AreaCodeNotRemovedException;
import de.kaiserpfalzEdv.office.contacts.address.phone.InvalidAreaCodeException;
import de.kaiserpfalzEdv.office.contacts.address.phone.NoSuchAreaCodeException;
import de.kaiserpfalzEdv.office.contacts.location.City;
import de.kaiserpfalzEdv.office.core.data.EntityQueryBase;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public interface AreaCodeService {
    /**
     * Creates a new area code.
     *
     * @param areaCode Area code to be saved.
     * @throws AreaCodeAlreadyExistsException The area code already exists.
     * @throws InvalidAreaCodeException       The area code data is inconsistent.
     */
    public void createAreaCode(@NotNull final AreaCode areaCode) throws AreaCodeAlreadyExistsException, InvalidAreaCodeException;

    /**
     * Retrieves an area code.
     *
     * @param id Id of the area code to be retrieved.
     * @return The area code with the given id.
     * @throws NoSuchAreaCodeException There is no area code with that ID.
     */
    public City retrieveAreaCode(@NotNull final UUID id) throws NoSuchAreaCodeException;

    /**
     * Retrieves an area code.
     *
     * @param query The query to retrieve all area codes for.
     * @param paging The page definition.
     * @return the cities matching the query.
     */
    public Page<City> retrieveAreaCode(@NotNull final EntityQueryBase query, @NotNull final Pageable paging);

    /**
     * Updates the area code data.
     *
     * @param id       Id of the area code which data is to be changed.
     * @param areaCode The new area code data to be saved.
     * @throws NoSuchAreaCodeException  The area code already exists.
     * @throws InvalidAreaCodeException The area code data is inconsistent.
     */
    public void updateAreaCode(@NotNull final UUID id, @NotNull final AreaCode areaCode) throws NoSuchAreaCodeException, InvalidAreaCodeException;

    /**
     * Deletes the area code.
     *
     * @param id ID of the area code to be removed.
     * @throws AreaCodeNotRemovedException The area code can not be removed.
     */
    public void deleteAreaCode(@NotNull final UUID id) throws AreaCodeNotRemovedException;
}
