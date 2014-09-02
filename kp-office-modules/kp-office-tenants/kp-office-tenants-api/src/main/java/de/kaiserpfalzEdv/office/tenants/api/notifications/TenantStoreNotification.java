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

package de.kaiserpfalzEdv.office.tenants.api.notifications;

import de.kaiserpfalzEdv.office.notifications.OfficeNotification;
import de.kaiserpfalzEdv.office.tenants.api.commands.TenantStoreCommand;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.UUID;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class TenantStoreNotification extends OfficeNotification {
    private static final long serialVersionUID = 1L;


    private UUID tenantId;

    public TenantStoreNotification(final TenantStoreCommand command) {
        super(command.getCommandId());
        setTenantId(command.getTenantId());
    }

    public TenantStoreNotification(final UUID commandId, final UUID tenantId) {
        super(commandId);
        setTenantId(tenantId);
    }


    @Override
    public String getTarget() {
        return TenantStoreCommand.TARGET_ENTITY;
    }


    public UUID getTenantId() {
        return tenantId;
    }

    protected void setTenantId(final UUID tenantId) {
        this.tenantId = tenantId;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("tenantId", tenantId)
                .toString();
    }
}
