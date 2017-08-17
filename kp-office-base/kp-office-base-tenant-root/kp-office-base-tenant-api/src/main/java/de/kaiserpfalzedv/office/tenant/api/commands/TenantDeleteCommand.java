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

package de.kaiserpfalzedv.office.tenant.api.commands;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.kaiserpfalzedv.office.common.api.commands.CrudCommands;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-25
 */
public class TenantDeleteCommand extends TenantIdContainingBaseCommand {
    private static final long serialVersionUID = 3433560321699584058L;

    private static final CrudCommands CRUD_TYPE = CrudCommands.DELETE;

    @JsonCreator
    TenantDeleteCommand(
            @JsonProperty("source") final UUID source,
            @JsonProperty("command") final UUID commandId,
            @JsonProperty("tenant") final UUID tenant
    ) {
        super(CRUD_TYPE, source, commandId, tenant);
    }
}
