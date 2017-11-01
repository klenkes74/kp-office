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

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.kaiserpfalzedv.office.common.api.commands.BaseCommand;
import de.kaiserpfalzedv.office.common.api.commands.CrudCommands;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-25
 */
public abstract class TenantBaseCommand extends BaseCommand {
    private static final long serialVersionUID = -953913734050792152L;

    @JsonIgnore
    private CrudCommands crudType;

    TenantBaseCommand(
            @NotNull final CrudCommands type,
            @NotNull final UUID source,
            @NotNull final UUID commandId
    ) {
        super(source, commandId);

        crudType = type;
    }

    public void execute(TenantCommandExecutor executor) throws TenantCommandExecutionException {

    }

    @JsonIgnore
    public CrudCommands getCrudType() {
        return crudType;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .append("crudType", crudType)
                .toString();
    }
}
