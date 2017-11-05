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

package de.kaiserpfalzedv.iam.access.jpa.roles;

import de.kaiserpfalzedv.commons.api.data.ObjectExistsException;
import de.kaiserpfalzedv.commons.api.data.Pageable;
import de.kaiserpfalzedv.commons.api.data.PagedListable;
import de.kaiserpfalzedv.commons.api.data.Predicate;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 1.0.0
 */
public interface JPAEntitlementRepository {
    JPAEntitlement create(@NotNull JPAEntitlement entitlement) throws ObjectExistsException;

    Optional<JPAEntitlement> retrieve(@NotNull UUID id);

    PagedListable<JPAEntitlement> retrieve(@NotNull Pageable page);

    PagedListable<JPAEntitlement> retrieve(@NotNull Predicate<JPAEntitlement> predicate,
                                           @NotNull Pageable page);

    void update(@NotNull JPAEntitlement entitlement);

    void delete(@NotNull UUID id);

    void delete(@NotNull JPAEntitlement entitlement);
}
