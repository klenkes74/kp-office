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

package de.kaiserpfalzEdv.office.clients.core.mock.test;

import de.kaiserpfalzEdv.commons.jee.paging.Page;
import de.kaiserpfalzEdv.commons.test.CommonTestBase;
import de.kaiserpfalzEdv.office.clients.core.tenant.impl.TenantMockClient;
import de.kaiserpfalzEdv.office.commons.paging.PageableImpl;
import de.kaiserpfalzEdv.office.commons.paging.SortImpl;
import de.kaiserpfalzEdv.office.core.tenants.Tenant;
import de.kaiserpfalzEdv.office.core.tenants.TenantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 17.08.15 00:00
 */
@Test
public class TenantMockClientTest extends CommonTestBase {
    private static final Logger LOG = LoggerFactory.getLogger(TenantMockClientTest.class);


    private TenantService service;


    public TenantMockClientTest() {
        super(TenantMockClientTest.class, LOG);
    }


    public void checkCreation() {
        logMethod("creation", "Checks if the mock can be created ...");

        Page<Tenant> result = service.listTenants(new PageableImpl(0, 50, new SortImpl("id")));
        LOG.trace("Result: {}", result);

        Assert.assertEquals(result.getSize(), 10, "There should be 10 tenants!");
    }


    @BeforeMethod
    protected void setupService() {
        service = new TenantMockClient();
    }
}
