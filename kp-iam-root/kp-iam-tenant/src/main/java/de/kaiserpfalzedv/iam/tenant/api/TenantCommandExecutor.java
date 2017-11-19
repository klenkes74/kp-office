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

package de.kaiserpfalzedv.iam.tenant.api;

import java.util.Optional;

import de.kaiserpfalzedv.commons.api.action.CommandExecutionException;
import de.kaiserpfalzedv.commons.api.action.CommandExecutor;
import de.kaiserpfalzedv.iam.tenant.api.commands.TenantCreateCommand;
import de.kaiserpfalzedv.iam.tenant.api.commands.TenantDeleteCommand;
import de.kaiserpfalzedv.iam.tenant.api.commands.TenantRetrieveCommand;
import de.kaiserpfalzedv.iam.tenant.api.commands.TenantUpdateCommand;
import de.kaiserpfalzedv.iam.tenant.api.replies.TenantBaseReply;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-27
 */
public interface TenantCommandExecutor extends CommandExecutor {
    Optional<? extends TenantBaseReply> execute(TenantCreateCommand command) throws CommandExecutionException;

    Optional<? extends TenantBaseReply> execute(TenantRetrieveCommand command) throws CommandExecutionException;

    Optional<? extends TenantBaseReply> execute(TenantUpdateCommand command) throws CommandExecutionException;

    Optional<? extends TenantBaseReply> execute(TenantDeleteCommand command) throws CommandExecutionException;
}
