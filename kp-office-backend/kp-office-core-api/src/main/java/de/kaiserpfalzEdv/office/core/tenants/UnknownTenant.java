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

package de.kaiserpfalzEdv.office.core.tenants;

import java.util.UUID;

/**
 * An unknown tenant is a tenant where only the ID is known.
 *
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class UnknownTenant implements Tenant {
    public static final UUID unknownTenantId = UUID.fromString("00000000-0000-0000-0000-000000000001");


    @SuppressWarnings("deprecation")
    public static final Tenant INSTANCE = new UnknownTenant();


    @SuppressWarnings({"UnusedDeclaration", "deprecation"})
    @Deprecated
    public UnknownTenant() {}

    @Override
    public UUID getId() {
        return unknownTenantId;
    }

    @Override
    public String getDisplayNumber() {
        return "0";
    }

    @Override
    public String getDisplayName() {
        return "Unknown Tenant";
    }


    @Override
    public boolean isHidden() {
        return false;
    }
}
