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

package de.kaiserpfalzedv.commons.api.action.replies;

import java.io.Serializable;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.commons.api.action.commands.CrudCommand;
import de.kaiserpfalzedv.commons.api.data.paging.PagedListable;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-11-19
 */
public interface CrudReplyBuilderValueCalculator<T extends Serializable> {
    UUID setDefaultId(
            @NotNull final CrudCommand<T> command, @NotNull final UUID replyId,
            @NotNull final UUID dataId, @NotNull final T data, @NotNull final PagedListable<T> datalist
    );

    T setDefaultData(
            @NotNull final CrudCommand<T> command, @NotNull final UUID replyId,
            @NotNull final UUID dataId, @NotNull final T data, @NotNull final PagedListable<T> datalist
    );

    PagedListable<T> setDefaultDataList(
            @NotNull final CrudCommand<T> command, @NotNull final UUID replyId,
            @NotNull final UUID dataId, @NotNull final T data, @NotNull final PagedListable<T> datalist
    );
}
