/*
 * Copyright 2017 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzedv.office.finance.chartofaccounts.chartofaccounts.test;

import java.util.UUID;

import de.kaiserpfalzedv.office.finance.chartofaccounts.api.chartofaccounts.ChartOfAccountsAlreadyExistsException;
import de.kaiserpfalzedv.office.finance.chartofaccounts.api.chartofaccounts.ChartsOfAccountStore;
import de.kaiserpfalzedv.office.finance.chartofaccounts.api.chartofaccounts.ChartsOfAccounts;
import de.kaiserpfalzedv.office.finance.chartofaccounts.impl.chartofaccounts.ChartsOfAccountsImpl;
import de.kaiserpfalzedv.office.finance.chartofaccounts.mock.ChartsOfAccountStoreMock;
import de.kaiserpfalzedv.office.tenant.api.NullTenant;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 05.01.16 17:20
 */
public class ChartOfAccountsTest {
    private static final Logger LOG = LoggerFactory.getLogger(ChartOfAccountsTest.class);

    private static final UUID TENANT_ID = new NullTenant().getId();

    private ChartsOfAccountStore store = new ChartsOfAccountStoreMock();
    private ChartsOfAccounts service;


    @Before
    public void setUp() {
        service = new ChartsOfAccountsImpl(store);
    }

    @Test
    public void testMinimalChartOfAccounts() throws ChartOfAccountsAlreadyExistsException {
        service.createChartOfAccount("SKR04", TENANT_ID, UUID.randomUUID(), "Kontenrahmen 04", "Sonderkontenrahmen 04");
    }
}
