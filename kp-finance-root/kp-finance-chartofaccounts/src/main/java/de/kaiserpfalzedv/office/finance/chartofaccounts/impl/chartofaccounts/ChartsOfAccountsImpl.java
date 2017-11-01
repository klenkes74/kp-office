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

package de.kaiserpfalzedv.office.finance.chartofaccounts.impl.chartofaccounts;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.ejb.PostActivate;
import javax.ejb.Singleton;
import javax.inject.Inject;

import de.kaiserpfalzedv.office.finance.chartofaccounts.api.account.Account;
import de.kaiserpfalzedv.office.finance.chartofaccounts.api.account.AccountNotMappedException;
import de.kaiserpfalzedv.office.finance.chartofaccounts.api.chartofaccounts.ChartOfAccounts;
import de.kaiserpfalzedv.office.finance.chartofaccounts.api.chartofaccounts.ChartOfAccountsAlreadyExistsException;
import de.kaiserpfalzedv.office.finance.chartofaccounts.api.chartofaccounts.ChartOfAccountsDoesNotExistException;
import de.kaiserpfalzedv.office.finance.chartofaccounts.api.chartofaccounts.ChartedAccount;
import de.kaiserpfalzedv.office.finance.chartofaccounts.api.chartofaccounts.ChartsOfAccountStore;
import de.kaiserpfalzedv.office.finance.chartofaccounts.api.chartofaccounts.ChartsOfAccounts;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 04.01.16 04:37
 */
@Singleton
public class ChartsOfAccountsImpl implements ChartsOfAccounts {
    private final HashMap<String, ChartOfAccounts> data = new HashMap<>(3);


    private ChartsOfAccountStore store;

    @Inject
    public ChartsOfAccountsImpl(final ChartsOfAccountStore store) {
        this.store = store;
    }


    @PostConstruct
    @PostActivate
    public void loadChartOfAccounts() {
        Set<ChartOfAccounts> plans = store.load();

        plans.clear();
        plans.forEach(p -> data.put(p.getDisplayName(), p));
    }


    public void createChartOfAccount(final String chartOfAccount, final UUID tenantId, final UUID id, final String displayName, final String fullName) throws ChartOfAccountsAlreadyExistsException {
        if (data.containsKey(chartOfAccount))
            throw new ChartOfAccountsAlreadyExistsException(chartOfAccount);

        data.put(chartOfAccount, new ChartOfAccountsImpl(tenantId, id, displayName, fullName));
    }

    @Override
    public ChartedAccount put(String chartOfAccounts, String accountNumber, Account account) throws ChartOfAccountsDoesNotExistException {
        ChartOfAccounts chartOfAccountsTable = getChartOfAcounts(chartOfAccounts);

        ChartedAccountBuilder chartedAccount;
        try {
            chartedAccount = new ChartedAccountBuilder()
                    .withChartedAccount(chartOfAccountsTable.get(accountNumber));
        } catch (AccountNotMappedException e) {
            chartedAccount = new ChartedAccountBuilder()
                    .withAccountNumber(accountNumber)
                    .withDisplayName(account.getDisplayName())
                    .withFullName(account.getFullName());
        }

        chartedAccount.addAccount(account);

        return chartOfAccountsTable.put(accountNumber, chartedAccount.build());
    }

    private ChartOfAccounts getChartOfAcounts(final String chartOfAccounts) throws ChartOfAccountsDoesNotExistException {
        if (!data.containsKey(chartOfAccounts))
            throw new ChartOfAccountsDoesNotExistException(chartOfAccounts);

        return data.get(chartOfAccounts);
    }

    @Override
    public void remove(String chartOfAccounts, String accountNumber, Account account) throws ChartOfAccountsDoesNotExistException, AccountNotMappedException {
        ChartOfAccounts chartOfAccountsTable = getChartOfAcounts(chartOfAccounts);

        ChartedAccountBuilder chartedAccount = new ChartedAccountBuilder()
                .withChartedAccount(chartOfAccountsTable.get(accountNumber));
        chartedAccount.removeAccount(account);
        chartOfAccountsTable.remove(accountNumber);
        chartOfAccountsTable.put(accountNumber, chartedAccount.build());
    }

    @Override
    public void clear(String chartOfAccounts, String accountNumber) throws ChartOfAccountsDoesNotExistException, AccountNotMappedException {
        getChartOfAcounts(chartOfAccounts).remove(accountNumber);
    }

    @Override
    public void clear(String chartOfAccounts) {
        try {
            getChartOfAcounts(chartOfAccounts).clear();
        } catch (ChartOfAccountsDoesNotExistException e) {
            // Clearing is ok. Nothing bad will happen if the chart does not exist at all.
        }
    }

    @Override
    public ChartedAccount get(String chartOfAccounts, String accountNumber) throws AccountNotMappedException {
        try {
            return getChartOfAcounts(chartOfAccounts).get(accountNumber);
        } catch (ChartOfAccountsDoesNotExistException e) {
            throw new AccountNotMappedException(accountNumber);
        }
    }
}
