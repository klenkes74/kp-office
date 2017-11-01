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

package de.kaiserpfalzedv.office.finance.chartofaccounts.mock.test;

import de.kaiserpfalzedv.office.finance.chartofaccounts.api.chartofaccounts.ChartOfAccounts;
import de.kaiserpfalzedv.office.finance.chartofaccounts.api.chartofaccounts.ChartsOfAccountStore;
import de.kaiserpfalzedv.office.finance.chartofaccounts.impl.chartofaccounts.ChartOfAccountsBuilder;
import de.kaiserpfalzedv.office.finance.chartofaccounts.mock.ChartsOfAccountStoreMock;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-10-26
 */
public class ChartsOfAccountStoreMockTest {
    private static final Logger LOG = LoggerFactory.getLogger(ChartsOfAccountStoreMockTest.class);

    private static final ChartOfAccounts SKR04 = new ChartOfAccountsBuilder()
            .withDisplayName("SKR04")
            .withFullName("Sonderkontenrahmen 04")
            .build();

    private ChartsOfAccountStore sut;

    @Before
    public void setUp() {
        sut = new ChartsOfAccountStoreMock();
    }


    @Test
    public void testShouldReturnACopyOfSKR04WhenCreatingChartOfAccount() {
        ChartOfAccounts saved = sut.save(SKR04);

        assertEquals(SKR04, saved);
        assertNotEquals(System.identityHashCode(SKR04), System.identityHashCode(saved));
    }
}
