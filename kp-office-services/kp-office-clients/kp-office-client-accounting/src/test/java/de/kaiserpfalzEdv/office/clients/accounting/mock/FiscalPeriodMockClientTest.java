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

package de.kaiserpfalzEdv.office.clients.accounting.mock;

import de.kaiserpfalzEdv.commons.test.CommonTestBase;
import de.kaiserpfalzEdv.office.accounting.tax.FiscalPeriod;
import de.kaiserpfalzEdv.office.accounting.tax.FiscalPeriodService;
import de.kaiserpfalzEdv.office.clients.core.tenant.impl.TenantMockClient;
import de.kaiserpfalzEdv.office.core.tenants.TenantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Set;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 17.08.15 00:14
 */
@Test
public class FiscalPeriodMockClientTest extends CommonTestBase {
    private static final Logger LOG = LoggerFactory.getLogger(FiscalPeriodMockClientTest.class);


    private TenantService       tenantService;
    private FiscalPeriodService service;

    public FiscalPeriodMockClientTest() {
        super(FiscalPeriodMockClientTest.class, LOG);
    }


    public void checkCreation() {
        logMethod("creation", "Checks the creation of the service");

        Set<FiscalPeriod> result = service.findAllPeriods();
        LOG.trace("Result: {}", result);
    }


    @BeforeMethod
    protected void setUpService() {
        service = new FiscalPeriodMockClient(tenantService);
    }

    @BeforeClass
    protected void setUpTenantService() {
        tenantService = new TenantMockClient();
    }
}
