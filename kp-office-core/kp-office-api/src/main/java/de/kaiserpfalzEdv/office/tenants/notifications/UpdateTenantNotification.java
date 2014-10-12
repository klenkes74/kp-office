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

package de.kaiserpfalzEdv.office.tenants.notifications;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.kaiserpfalzEdv.office.tenants.Tenant;
import de.kaiserpfalzEdv.office.tenants.TenantDTO;
import de.kaiserpfalzEdv.office.tenants.commands.TenantStoreCommand;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class UpdateTenantNotification extends TenantStoreNotification {
    private static final long serialVersionUID = 1L;

    private TenantDTO tenant;

    @Deprecated // Only for JAX-B, Jackson, JPA!
    public UpdateTenantNotification() {
    }

    public UpdateTenantNotification(final TenantStoreCommand command, final Tenant tenant) {
        super(command);

        setTenant(tenant);
    }


    @JsonIgnore
    public String getDisplayNumber() {
        return tenant.getDisplayNumber();
    }

    @JsonIgnore
    public String getDisplayName() {
        return tenant.getDisplayName();
    }


    public Tenant getTenant() {
        return tenant;
    }

    private void setTenant(final Tenant tenant) {
        checkArgument(tenant != null, "A valid tenant is needed for a tenant notification!");
        this.tenant = new TenantDTO(tenant);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .append("tenant", tenant)
                .toString();
    }
}
