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

package de.kaiserpfalzedv.office.finance.accounting.impl.accounts;

import de.kaiserpfalzedv.office.finance.accounting.accounts.Account;
import de.kaiserpfalzedv.office.finance.accounting.accounts.AccountNotMappedException;
import de.kaiserpfalzedv.office.finance.accounting.accounts.ChartOfAccounts;
import de.kaiserpfalzedv.office.finance.accounting.accounts.ChartOfAccountsAlreadyExistsException;
import de.kaiserpfalzedv.office.finance.accounting.accounts.ChartOfAccountsDoesNotExistException;
import de.kaiserpfalzedv.office.finance.accounting.accounts.ChartsOfAccounts;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 04.01.16 04:37
 */
public class ChartsOfAccountsImpl implements ChartsOfAccounts {
    private final HashMap<String, ChartOfAccounts> data = new HashMap<>(3);

    public void createChartOfAccount(final String chartOfAccount, final UUID tenantId, final UUID id, final String displayName, final String fullName) throws ChartOfAccountsAlreadyExistsException {
        if (data.containsKey(chartOfAccount))
            throw new ChartOfAccountsAlreadyExistsException(chartOfAccount);

        data.put(chartOfAccount, new ChartOfAccountsImpl(tenantId, id, displayName, fullName));
    }

    @Override
    public Set<Account> put(String chartOfAccounts, String accountNumber, Account account) throws ChartOfAccountsDoesNotExistException {
        getChartOfAcounts(chartOfAccounts).put(accountNumber, account);

        return getChartOfAcounts(chartOfAccounts).get(accountNumber);
    }

    private ChartOfAccounts getChartOfAcounts(final String chartOfAccounts) throws ChartOfAccountsDoesNotExistException {
        if (!data.containsKey(chartOfAccounts))
            throw new ChartOfAccountsDoesNotExistException(chartOfAccounts);

        return data.get(chartOfAccounts);
    }

    @Override
    public Set<Account> remove(String chartOfAccounts, String accountNumber, Account account) throws ChartOfAccountsDoesNotExistException {
        Set<Account> result = getChartOfAcounts(chartOfAccounts).get(accountNumber);

        getChartOfAcounts(chartOfAccounts).remove(accountNumber, account);

        return result;
    }

    @Override
    public Set<Account> clear(String chartOfAccounts, String accountNumber) throws ChartOfAccountsDoesNotExistException {
        Set<Account> result = getChartOfAcounts(chartOfAccounts).get(accountNumber);

        getChartOfAcounts(chartOfAccounts).clear(accountNumber);

        return result;
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
    public Set<Account> get(String chartOfAccounts, String accountNumber) throws AccountNotMappedException {
        try {
            return getChartOfAcounts(chartOfAccounts).get(accountNumber);
        } catch (ChartOfAccountsDoesNotExistException e) {
            throw new AccountNotMappedException(accountNumber);
        }
    }

    @Override
    public String get(String chartOfAccounts, Account account) throws AccountNotMappedException {
        try {
            return getChartOfAcounts(chartOfAccounts).get(account);
        } catch (ChartOfAccountsDoesNotExistException e) {
            throw new AccountNotMappedException(account);
        }
    }

    @Override
    public String get(String chartOfAccounts, UUID accountId) throws AccountNotMappedException {
        try {
            return getChartOfAcounts(chartOfAccounts).get(accountId);
        } catch (ChartOfAccountsDoesNotExistException e) {
            throw new AccountNotMappedException(accountId);
        }
    }
}
