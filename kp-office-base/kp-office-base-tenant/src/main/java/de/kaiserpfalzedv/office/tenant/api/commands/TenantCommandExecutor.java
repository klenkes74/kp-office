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

import de.kaiserpfalzedv.office.common.api.commands.CommandExecutor;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-27
 */
public interface TenantCommandExecutor extends CommandExecutor {
    void execute(TenantCreateCommand command) throws TenantCommandExecutionException;

    void execute(TenantRetrieveCommand command) throws TenantCommandExecutionException;

    void execute(TenantRetrieveAllCommand command) throws TenantCommandExecutionException;

    void execute(TenantUpdateCommand command) throws TenantCommandExecutionException;

    void execute(TenantDeleteCommand command) throws TenantCommandExecutionException;
}