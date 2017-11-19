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

package de.kaiserpfalzedv.commons.api.commands;

import java.io.Serializable;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.commons.api.data.paging.Pageable;
import de.kaiserpfalzedv.commons.api.data.query.Predicate;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-11-19
 */
public interface CrudCommandCreator<T extends Serializable> {
    BaseCommand create(@NotNull final UUID source, @NotNull final UUID commandId, @NotNull final T data);

    BaseCommand retrieve(
            @NotNull final UUID source, @NotNull final UUID commandId,
            @NotNull final Predicate<T> predicate, @NotNull final Pageable page
    );

    BaseCommand update(@NotNull final UUID source, @NotNull final UUID commandId, @NotNull final T data);

    BaseCommand delete(@NotNull final UUID source, @NotNull final UUID commandId, @NotNull final UUID dataId);
}
