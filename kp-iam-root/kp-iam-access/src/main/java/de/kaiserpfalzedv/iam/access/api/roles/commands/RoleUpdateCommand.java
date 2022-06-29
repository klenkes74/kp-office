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

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.kaiserpfalzedv.commons.api.action.CrudCommandType;
import de.kaiserpfalzedv.iam.access.api.roles.Role;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-11-19
 */
public class RoleUpdateCommand extends RoleBaseCommand {
    private static final long serialVersionUID = 4460853520317478580L;

    private Role role;

    /**
     * Constructs a prototypical Event.
     *
     * @param source  The object on which the Event initially occurred.
     * @param command The unique ID of this command.
     *
     * @throws IllegalArgumentException if source is null.
     */
    public RoleUpdateCommand(
            @NotNull @JsonProperty("source") final UUID source,
            @NotNull @JsonProperty("command") final UUID command,
            @NotNull @JsonProperty("role") final Role role
    ) {
        super(source, command, CrudCommandType.UPDATE);

        this.role = role;
    }

    public Role getRole() {
        return role;
    }
}