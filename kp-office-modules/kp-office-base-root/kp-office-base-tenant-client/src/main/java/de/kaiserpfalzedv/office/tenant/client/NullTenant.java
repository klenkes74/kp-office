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

package de.kaiserpfalzedv.office.tenant.client;

import java.util.UUID;

import de.kaiserpfalzedv.office.tenant.Tenant;

/**
 * The null tenant for all objects that don't have a tenant.
 *
 * @author klenkes
 * @version 2015Q1
 * @since 27.12.15 11:41
 */
public class NullTenant extends TenantImpl implements Tenant {
    private static final long serialVersionUID = 1751230514524921614L;

    private static final UUID NULL_ID          = UUID.fromString("00000000-0000-0000-0000-000000000000");

    public NullTenant() {
        super(NULL_ID, NULL_ID, "", "");
    }
}
