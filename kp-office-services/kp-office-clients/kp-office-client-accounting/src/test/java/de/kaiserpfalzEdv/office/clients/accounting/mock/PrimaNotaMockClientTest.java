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
import de.kaiserpfalzEdv.office.accounting.primaNota.PrimaNotaService;
import de.kaiserpfalzEdv.office.accounting.tax.FiscalPeriodService;
import de.kaiserpfalzEdv.office.clients.core.tenant.impl.TenantMockClient;
import de.kaiserpfalzEdv.office.commons.paging.PageableImpl;
import de.kaiserpfalzEdv.office.core.tenants.TenantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 17.08.15 00:43
 */
@Test
public class PrimaNotaMockClientTest extends CommonTestBase {
    private static final Logger LOG = LoggerFactory.getLogger(PrimaNotaMockClientTest.class);

    private FiscalPeriodService periodService;
    private TenantService       tenantService;

    private PrimaNotaService service;

    public PrimaNotaMockClientTest() {
        super(PrimaNotaMockClient.class, LOG);
    }


    public void checkCreation() {
        logMethod("creation", "Checks if the mock is created correctly");

        assertEquals(service.listAllPrimaNota().size(), 16, "There should be 16 prima notae.");
        assertEquals(
                service.loadPrimaNota(service.listAllPrimaNota().iterator().next(), new PageableImpl(0, 20))
                       .getTotalElements(), 1000
        );
    }


    @BeforeMethod
    protected void setUpService() {
        service = new PrimaNotaMockClient(periodService, tenantService);
    }

    @BeforeClass
    protected void setUpHelpServices() {
        tenantService = new TenantMockClient();
        periodService = new FiscalPeriodMockClient(tenantService);
    }
}
