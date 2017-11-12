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

package de.kaiserpfalzedv.commons.jpa;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.commons.api.data.base.Identifiable;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-11-12
 */
public interface JPAConverter<T extends Identifiable, J extends JPAAbstractIdentifiable> {
    J toJPA(@NotNull final T model);

    T toModel(@NotNull final J jpa);

    Optional<T> toModel(@NotNull final Optional<J> jpa);
}
