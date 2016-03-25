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

package de.kaiserpfalzedv.office.finance.accounting.accounts;

import de.kaiserpfalzedv.office.finance.accounting.AccountNotMappedException;
import de.kaiserpfalzedv.office.finance.accounting.ChartOfAccountsAlreadyExistsException;
import de.kaiserpfalzedv.office.finance.accounting.ChartOfAccountsDoesNotExistException;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 03.01.16 17:21
 */
public interface ChartsOfAccounts extends Serializable {
    void createChartOfAccount(final String chartOfAccount, final UUID tenantId, final UUID id, final String displayName, final String fullName) throws ChartOfAccountsAlreadyExistsException;

    Set<Account> put(final String chartOfAccounts, final String accountNumber, final Account account) throws ChartOfAccountsDoesNotExistException;

    Set<Account> remove(final String chartOfAccounts, final String accountNumber, final Account account) throws ChartOfAccountsDoesNotExistException;

    Set<Account> clear(final String chartOfAccounts, final String accountNumber) throws ChartOfAccountsDoesNotExistException;

    void clear(final String chartOfAccounts);

    Set<Account> get(final String chartOfAccounts, final String accountNumber) throws AccountNotMappedException;

    String get(final String chartOfAccounts, final Account account) throws AccountNotMappedException;

    String get(final String chartOfAccounts, final UUID accountId) throws AccountNotMappedException;
}
