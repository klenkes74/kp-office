/*
 * Copyright 2016 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzedv.office.tenant.commands.test;

import java.util.UUID;

import de.kaiserpfalzedv.office.tenant.Tenant;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-25
 */
public class TestTenant implements Tenant {
    private static final long serialVersionUID = -2804739088043736423L;

    private static final UUID NULL_ID = UUID.fromString("00000000-0000-0000-0000-000000000000");

    @Override
    public String getDisplayName() {
        return "";
    }

    @Override
    public String getFullName() {
        return "";
    }

    @Override
    public UUID getId() {
        return NULL_ID;
    }

    @Override
    public UUID getTenantId() {
        return NULL_ID;
    }
}
