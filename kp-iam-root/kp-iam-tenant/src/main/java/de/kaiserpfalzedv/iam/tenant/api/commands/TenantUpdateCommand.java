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
import de.kaiserpfalzedv.commons.api.action.CrudCommandType;
import de.kaiserpfalzedv.iam.tenant.api.Tenant;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-25
 */
public class TenantUpdateCommand extends TenantContainingBaseCommand {
    private static final long serialVersionUID = -9051562688508982153L;
    private static final CrudCommandType CRUD_TYPE = CrudCommandType.UPDATE;


    @JsonCreator
    TenantUpdateCommand(
            @JsonProperty("source") @NotNull UUID source,
            @JsonProperty("command") @NotNull UUID commandId,
            @JsonProperty("tenant") @NotNull Tenant tenant
    ) {
        super(CRUD_TYPE, source, commandId, tenant);
    }
}
