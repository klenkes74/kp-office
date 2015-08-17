/*
 * Copyright 2015 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzEdv.office.core.tenants.commands;

import de.kaiserpfalzEdv.office.commons.notifications.Notification;
import de.kaiserpfalzEdv.office.core.tenants.Tenant;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.UUID;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 01.03.15 21:34
 */
public class TenantUpdateCommand implements TenantCommand {
    private static final long serialVersionUID = 4700404114214630624L;


    private UUID   id;
    private Tenant tenant;


    public TenantUpdateCommand(final UUID id, final Tenant tenant) {
        this.id = id;
        this.tenant = tenant;
    }


    public UUID getId() {
        return id;
    }

    public Tenant getTenant() {
        return tenant;
    }


    @Override
    public Notification execute(TenantCommandExecutor executor) {
        return executor.execute(this);
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        TenantUpdateCommand rhs = (TenantUpdateCommand) obj;
        return new EqualsBuilder()
                .append(this.tenant, rhs.tenant)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(tenant)
                .toHashCode();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append(tenant)
                .toString();
    }
}
