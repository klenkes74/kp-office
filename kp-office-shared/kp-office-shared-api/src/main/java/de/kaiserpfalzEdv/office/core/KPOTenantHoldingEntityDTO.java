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

package de.kaiserpfalzEdv.office.core;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class KPOTenantHoldingEntityDTO extends KPOEntityDTO implements KPOTenantHoldingEntity {
    private static final long serialVersionUID = 4102545731581586298L;

    private UUID tenantId;


    @Deprecated // Only for Jackson, JAX-B and JPA!
    public KPOTenantHoldingEntityDTO() {
    }

    @SuppressWarnings("deprecation")
    public KPOTenantHoldingEntityDTO(@NotNull final UUID id,
                                     @NotNull final String displayName,
                                     @NotNull final String displayNumber,
                                     @NotNull final UUID tenantId) {
        setId(id);
        setDisplayName(displayName);
        setDisplayNumber(displayNumber);
        setTenantId(tenantId);
    }


    public UUID getTenantId() {
        return tenantId;
    }

    protected void setTenantId(@NotNull final UUID tenantId) {
        this.tenantId = tenantId;
    }
}
