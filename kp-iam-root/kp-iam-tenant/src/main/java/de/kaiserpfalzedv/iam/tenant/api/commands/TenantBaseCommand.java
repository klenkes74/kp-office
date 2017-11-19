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

import java.util.Optional;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.commons.api.action.CommandExecutionException;
import de.kaiserpfalzedv.commons.api.action.CrudCommandType;
import de.kaiserpfalzedv.commons.api.action.commands.CrudCommand;
import de.kaiserpfalzedv.iam.tenant.api.Tenant;
import de.kaiserpfalzedv.iam.tenant.api.TenantCommandExecutor;
import de.kaiserpfalzedv.iam.tenant.api.replies.TenantBaseReply;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-25
 */
public abstract class TenantBaseCommand extends CrudCommand<Tenant> {
    private static final long serialVersionUID = -7995764167329794088L;

    TenantBaseCommand(
            @NotNull final CrudCommandType type,
            @NotNull final UUID source,
            @NotNull final UUID commandId
    ) {
        super(source, commandId, type);
    }

    public Optional<? extends TenantBaseReply> execute(TenantCommandExecutor executor) throws CommandExecutionException {
        throw new UnsupportedOperationException("Can't execute a TenantBaseCommand directly. Only subclasses are supported");
    }
}
