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

package de.kaiserpfalzEdv.office.tenants.api.commands;

import de.kaiserpfalzEdv.office.commands.OfficeCommandException;
import de.kaiserpfalzEdv.office.commands.OfficeCommandHandler;

import java.util.UUID;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class DeleteTenantCommand extends TenantStoreCommand {
    private static final long serialVersionUID = 1L;

    @Deprecated // Only for JPA
    protected DeleteTenantCommand() {}


    /**
     * Changes the name of the tenant defined by the displayNumber.
     *
     * @param id The tenant id of this tenant.
     */
    public DeleteTenantCommand(final UUID id) {
        setTenantId(id);
    }


    @Override
    public void execute(final OfficeCommandHandler context) throws OfficeCommandException {
        context.handle(this);
    }
}
