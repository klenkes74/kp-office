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

package de.kaiserpfalzEdv.office.tenants;

import de.kaiserpfalzEdv.office.core.KPOEntityDTO;

import java.util.UUID;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class TenantDTO extends KPOEntityDTO implements Tenant {
    private static final long serialVersionUID = -8050304486006253950L;


    /**
     * @deprecated Only for JPA!
     */
    @SuppressWarnings("deprecation")
    @Deprecated
    protected TenantDTO() {
    }


    /**
     * A copy-constructor.
     *
     * @param orig The original tenant to be copied.
     */
    public TenantDTO(final Tenant orig) {
        this(orig.getId(), orig.getDisplayName(), orig.getDisplayNumber());
    }


    public TenantDTO(final String number, final String name) {
        this(UUID.randomUUID(), name, number);
    }

    public TenantDTO(final UUID id, final String number, final String name) {
        super(id, name, number);
    }
}
