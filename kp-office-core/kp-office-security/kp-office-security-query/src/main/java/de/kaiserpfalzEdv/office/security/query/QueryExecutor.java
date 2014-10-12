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

package de.kaiserpfalzEdv.office.security.query;

import de.kaiserpfalzEdv.office.tenants.Tenant;
import de.kaiserpfalzEdv.office.tenants.TenantCommandException;
import de.kaiserpfalzEdv.office.tenants.commands.TenantQueryById;
import de.kaiserpfalzEdv.office.tenants.commands.TenantQueryByName;
import de.kaiserpfalzEdv.office.tenants.commands.TenantQueryByNumber;

/**
 * @author klenkes
 * @since 2014Q
 */
public interface QueryExecutor {
    Tenant handle(TenantQueryById command) throws TenantCommandException;

    Tenant handle(TenantQueryByNumber command) throws TenantCommandException;

    Tenant handle(TenantQueryByName command) throws TenantCommandException;
}