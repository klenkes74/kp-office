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

package de.kaiserpfalzedv.iam.access.impl.roles;

import java.util.Optional;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.commons.api.data.ObjectExistsException;
import de.kaiserpfalzedv.commons.api.data.paging.Pageable;
import de.kaiserpfalzedv.commons.api.data.paging.PagedListable;
import de.kaiserpfalzedv.commons.api.data.query.Predicate;
import de.kaiserpfalzedv.iam.access.jpa.roles.JPARole;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 1.0.0
 */
public interface JPARoleRepository {
    JPARole create(@NotNull JPARole entity) throws ObjectExistsException;

    Optional<JPARole> retrieve(@NotNull UUID id);

    PagedListable<JPARole> retrieve(@NotNull Pageable page);

    PagedListable<JPARole> retrieve(
            @NotNull Predicate<JPARole> predicate,
            @NotNull Pageable page
    );

    void update(@NotNull JPARole entity);

    void delete(@NotNull UUID id);

    void delete(@NotNull JPARole entity);
}