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

import java.util.Optional;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.commons.api.data.base.Identifiable;
import de.kaiserpfalzedv.commons.api.data.paging.Pageable;
import de.kaiserpfalzedv.commons.api.data.paging.PagedListable;
import de.kaiserpfalzedv.commons.api.data.query.Predicate;

/**
 * The default repository interface for simple data.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Repository<T extends Identifiable, P extends Identifiable> {
    T create(T entitlement) throws ObjectExistsException;

    Optional<T> retrieve(@NotNull UUID id);

    PagedListable<T> retrieve(@NotNull Pageable page);

    PagedListable<T> retrieve(@NotNull Predicate<P> predicate, @NotNull Pageable page);

    void update(@NotNull T entitlement);

    void delete(@NotNull UUID id);

    void delete(@NotNull T entitlement);
}
