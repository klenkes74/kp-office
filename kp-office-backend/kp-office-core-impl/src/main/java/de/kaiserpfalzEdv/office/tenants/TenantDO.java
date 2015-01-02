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

package de.kaiserpfalzEdv.office.tenants;

import de.kaiserpfalzEdv.office.core.KPOEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

/**
 * A tenant for enabling multi-tenancy within the KP Office. Only the ID of the tenant should be stored
 * within the other data sets. The frontend services should cache the tenants and select them by ID to
 * display the name or number.
 *
 * Never store the Tenant as link since the tenant database may be on another server and shared between
 * different other services.
 *
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
@Entity
@Table(
        schema = "TENANTS",
        name = "TENANTS"
)
public class TenantDO extends KPOEntity {
    private static final long serialVersionUID = -8176009820957052173L;


    @SuppressWarnings("deprecation")
    @Deprecated
    protected TenantDO() {}


    /**
     * A copy-constructor.
     *
     * @param orig The original tenant to be copied.
     */
    public TenantDO(final Tenant orig) {
        this(orig.getId(), orig.getDisplayName(), orig.getDisplayNumber());
    }


    public TenantDO(final String number, final String name) {
        this(UUID.randomUUID(), name, number);
    }

    public TenantDO(final UUID id, final String number, final String name) {
        super(id, name, number);
    }
}
