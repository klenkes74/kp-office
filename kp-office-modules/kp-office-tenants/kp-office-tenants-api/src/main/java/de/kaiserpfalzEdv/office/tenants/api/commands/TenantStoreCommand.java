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

import de.kaiserpfalzEdv.office.commands.OfficeCommand;
import de.kaiserpfalzEdv.office.commands.OfficeCommandException;
import de.kaiserpfalzEdv.office.commands.OfficeCommandHandler;
import de.kaiserpfalzEdv.office.tenants.api.TenantCommandException;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.UUID;

/**
 * @author klenkes
 * @since 2014Q
 */
public abstract class TenantStoreCommand extends OfficeCommand {
    public String getTarget() {
        return "Tenant";
    }

    private UUID tenantId = UUID.randomUUID();


    public UUID getTenantId() {
        return tenantId;
    }

    protected void setTenantId(final UUID tenantId) {
        this.tenantId = tenantId;
    }


    public abstract void execute(TenantCommandHandler context) throws TenantCommandException;

    @Override
    public void execute(OfficeCommandHandler context) throws OfficeCommandException {
        execute((TenantCommandHandler) context);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("tenantId", tenantId)
                .toString();
    }
}
