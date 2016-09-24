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
 *
 */

package de.kaiserpfalzedv.office.tenant.client.test;

import de.kaiserpfalzedv.office.tenant.TenantService;
import de.kaiserpfalzedv.office.tenant.client.TenantClient;
import de.kaiserpfalzedv.office.tenant.test.AbstractTenantServiceTestClass;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2016-09-20
 */
public class TenantClientTest extends AbstractTenantServiceTestClass {
    @Override
    public TenantService createService() {
        return new TenantClient();
    }
}
