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

package de.kaiserpfalzEdv.office.accounting.chartsofaccounts.test;

import de.kaiserpfalzEdv.commons.test.SpringTestNGBase;
import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.Account;
import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.AccountMapping;
import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.ChartOfAccount;
import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.ChartsOfAccounts;
import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.server.AccountMappingLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

import static de.kaiserpfalzEdv.office.accounting.chartsofaccounts.ChartOfAccount.CoA.D03;
import static de.kaiserpfalzEdv.office.accounting.chartsofaccounts.ChartOfAccount.CoA.D04;
import static org.testng.Assert.assertEquals;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 18.02.15 18:19
 */
@Test
@ContextConfiguration("/beans-test.xml")
public class ChartsOfAccountsTest extends SpringTestNGBase {
    private static final Logger LOG = LoggerFactory.getLogger(ChartsOfAccountsTest.class);

    @Inject
    private ChartsOfAccounts accounts;

    @Inject
    private AccountMappingLocator locator;

    @Inject
    @ChartOfAccount(D03)
    private AccountMapping d03Mapping;

    @Inject
    @ChartOfAccount(D04)
    private AccountMapping d04Mapping;


    public ChartsOfAccountsTest() {
        super(ChartsOfAccountsTest.class, LOG);
    }


    public void testAccounts() {
        logMethod("check-accounts", "Check the chart of accounts.");

        assertEquals(accounts.getId(), UUID.fromString("cf5f01fb-83c7-4352-80bd-681163956786"), "Wrong id!");
        assertEquals(accounts.getDisplayName(), "Internal Chart Of Accounts", "Wrong name of chart of accounts!");
        assertEquals(accounts.allAccounts().size(), 3, "There are not 3 accounts in the chart of accounts!");
    }


    @Test(dataProvider = "account-mapping")
    public void testMapping(final Account account, final String d03Number, final String d04Number) {
        logMethod("check-mapping", "Check mapping from Account '" + account.getDisplayName() + "' to '" + d03Number + "' (D03) and '" + d04Number + "' (D04) ...");

        assertEquals(
                d03Mapping.renumber(account)
                          .getDisplayNumber(), d03Number, "Renumbering " + account.getId() + " for D03 failed!"
        );
        assertEquals(
                d04Mapping.renumber(account)
                          .getDisplayNumber(), d04Number, "Renumbering " + account.getId() + " for D04 failed!"
        );
    }

    @DataProvider(name = "account-mapping")
    public Iterator<Object[]> getAccountMapping() {

        ArrayList<Object[]> result = new ArrayList<>();

        result.add(new Object[]{accounts.getAccount(UUID.fromString("ddb684b8-501c-47fc-a46c-7990110bf092")), "1600", "1100"});
        result.add(new Object[]{accounts.getAccount(UUID.fromString("cb488173-b9b0-4f37-adb1-385d2051363e")), "1600", "1800"});
        result.add(new Object[]{accounts.getAccount(UUID.fromString("804327a3-50f7-4f2c-8ca0-1c3cd502b1ab")), "1100", "1800"});

        return result.iterator();
    }


    @Test(dataProvider = "account-bookable")
    public void testBookableAccounts(final AccountMapping mapping, final String number, final boolean bookable) {
        logMethod(
                "check-bookable", "Check, if account '" + number + "' is " + (bookable ? "" : "not ") + "bookable in mapper '" + mapping
                        .getDisplayName() + "' ..."
        );
        assertEquals(mapping.isBookable(number), bookable, "In mapping '" + mapping.getDisplayName() + "' the account '" + number + "' should be " + (bookable ? "" : "not ") + "bookable!");
    }

    @DataProvider(name = "account-bookable")
    public Iterator<Object[]> getAccountBookable() {
        ArrayList<Object[]> result = new ArrayList<>();

        result.add(new Object[]{d03Mapping, "1600", false});
        result.add(new Object[]{d03Mapping, "1100", true});

        result.add(new Object[]{d04Mapping, "1100", true});
        result.add(new Object[]{d04Mapping, "1800", false});

        result.add(new Object[]{d04Mapping, "2200", false});

        return result.iterator();
    }


    public void testLocator() {
        logMethod("check-locator", "Check if the locator contains two mappings ...");

        assertEquals(locator.getMappings().size(), 2, "There should be two mappings (D03 and D04).");
    }
}
