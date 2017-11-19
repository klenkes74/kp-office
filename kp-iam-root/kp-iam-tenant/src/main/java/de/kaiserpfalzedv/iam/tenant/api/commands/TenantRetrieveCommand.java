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

package de.kaiserpfalzedv.iam.tenant.api.commands;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.kaiserpfalzedv.commons.api.commands.CrudCommands;
import de.kaiserpfalzedv.commons.api.data.paging.Pageable;
import de.kaiserpfalzedv.commons.api.data.query.Predicate;
import de.kaiserpfalzedv.iam.tenant.api.Tenant;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-25
 */
public class TenantRetrieveCommand extends TenantBaseCommand {
    private static final long serialVersionUID = -3920045218010855443L;
    private static final CrudCommands CRUD_TYPE = CrudCommands.RETRIEVE;

    private Predicate<Tenant> predicate;
    private Pageable page;


    @JsonCreator
    TenantRetrieveCommand(
            @JsonProperty("source") @NotNull final UUID source,
            @JsonProperty("command") @NotNull final UUID commandId,
            @JsonProperty("predicate") @NotNull final Predicate<Tenant> predicate,
            @JsonProperty("page") @NotNull final Pageable page
    ) {
        super(CRUD_TYPE, source, commandId);

        this.predicate = predicate;
        this.page = page;
    }

    public Predicate<Tenant> getPredicate() {
        return predicate;
    }

    public Pageable getPage() {
        return page;
    }
}
