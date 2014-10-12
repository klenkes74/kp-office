/*
 * Copyright (c) 2014 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzEdv.office.tenants.commands;

import de.kaiserpfalzEdv.office.tenants.TenantCommandException;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public interface TenantCommandHandler {
    void handle(CreateTenantCommand command) throws TenantCommandException;

    void handle(RenameTenantCommand command) throws TenantCommandException;

    void handle(RenumberTenantCommand command) throws TenantCommandException;

    void handle(DeleteTenantCommand command) throws TenantCommandException;

    void handle(SyncTenantCommand command) throws TenantCommandException;
}
