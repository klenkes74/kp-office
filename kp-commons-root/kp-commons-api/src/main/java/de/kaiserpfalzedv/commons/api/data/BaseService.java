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

package de.kaiserpfalzedv.commons.api.data;

import java.io.Serializable;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.commons.api.data.base.Identifiable;
import de.kaiserpfalzedv.commons.api.data.paging.Pageable;
import de.kaiserpfalzedv.commons.api.data.paging.PagedListable;
import de.kaiserpfalzedv.commons.api.data.query.Predicate;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-30
 */
public interface BaseService<T extends Identifiable> {
    T create(@NotNull final T data) throws ObjectExistsException;

    T retrieve(@NotNull final UUID id) throws ObjectDoesNotExistException;

    PagedListable<T> retrieve(Predicate<T> predicate, Pageable page);

    T update(@NotNull final T data) throws ObjectExistsException, ObjectDoesNotExistException;

    void delete(@NotNull final UUID id);
}
