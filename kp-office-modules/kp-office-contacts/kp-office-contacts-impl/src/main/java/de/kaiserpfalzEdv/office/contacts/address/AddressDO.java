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

package de.kaiserpfalzEdv.office.contacts.address;

import de.kaiserpfalzEdv.office.core.KPOTenantHoldingEntity;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public abstract class AddressDO extends KPOTenantHoldingEntity implements Address {
    private static final long serialVersionUID = 6069535185329712041L;

    private AddressType type;
    private AddressUsage usage;


    @SuppressWarnings("deprecation")
    @Deprecated // Only for Jackson, JAX-B and JPA!
    public AddressDO() {
    }

    public AddressDO(@NotNull final UUID id,
                     @NotNull final String displayName,
                     @NotNull final String displayNumber,
                     @NotNull final AddressType type,
                     @NotNull final AddressUsage usage,
                     @NotNull final UUID tenantId) {
        super(id, displayName, displayNumber, tenantId);

        setType(type);
        setUsage(usage);
    }


    @Override
    public AddressType getType() {
        return type;
    }

    public void setType(@NotNull final AddressType addressType) {
        this.type = addressType;
    }


    @Override
    public AddressUsage getUsage() {
        return usage;
    }

    public void setUsage(@NotNull final AddressUsage addressUsage) {
        this.usage = addressUsage;
    }
}
