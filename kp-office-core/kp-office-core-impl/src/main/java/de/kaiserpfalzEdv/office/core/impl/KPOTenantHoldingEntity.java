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

package de.kaiserpfalzEdv.office.core.impl;

import de.kaiserpfalzEdv.office.commons.TenantHoldingEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
@MappedSuperclass
public class KPOTenantHoldingEntity extends KPOEntity implements TenantHoldingEntity {
    private static final long serialVersionUID = 3706937080258806740L;

    @Column(name = "TENANT_", unique = false)
    private UUID tenantId;


    @SuppressWarnings("deprecation")
    @Deprecated // Only for Jackson, JAX-B and JPA!
    public KPOTenantHoldingEntity() {
    }

    @SuppressWarnings("deprecation")
    public KPOTenantHoldingEntity(@NotNull final UUID id,
                                  @NotNull final String displayName,
                                  @NotNull final String displayNumber,
                                  @NotNull final UUID tenantId) {
        setId(id);
        setDisplayName(displayName);
        setDisplayNumber(displayNumber);
        setTenantId(tenantId);
    }


    @Override
    public UUID getTenantId() {
        return tenantId;
    }

    protected void setTenantId(@NotNull final UUID tenantId) {
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
