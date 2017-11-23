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

package de.kaiserpfalzedv.iam.access.api.roles.commands;

import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.commons.api.action.commands.CrudCommandBuilderCreator;
import de.kaiserpfalzedv.commons.api.data.paging.Pageable;
import de.kaiserpfalzedv.commons.api.data.query.Predicate;
import de.kaiserpfalzedv.iam.access.api.roles.Entitlement;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-11-19
 */
@ApplicationScoped
public class EntitlementCrudCommandBuilderCreator implements CrudCommandBuilderCreator<Entitlement> {
    @Override
    public EntitlementCreateCommand create(
            @NotNull final UUID source, @NotNull final UUID commandId,
            @NotNull final Entitlement data
    ) {
        return new EntitlementCreateCommand(source, commandId, data);
    }

    @Override
    public EntitlementRetrieveCommand retrieve(
            @NotNull final UUID source, @NotNull final UUID commandId,
            @NotNull final Predicate<Entitlement> predicate, @NotNull final Pageable page
    ) {
        return new EntitlementRetrieveCommand(source, commandId, predicate, page);
    }

    @Override
    public EntitlementUpdateCommand update(
            @NotNull final UUID source, @NotNull final UUID commandId,
            @NotNull final Entitlement data
    ) {
        return new EntitlementUpdateCommand(source, commandId, data);
    }

    @Override
    public EntitlementDeleteCommand delete(
            @NotNull final UUID source, @NotNull final UUID commandId,
            @NotNull final UUID dataId
    ) {
        return new EntitlementDeleteCommand(source, commandId, dataId);
    }
}
