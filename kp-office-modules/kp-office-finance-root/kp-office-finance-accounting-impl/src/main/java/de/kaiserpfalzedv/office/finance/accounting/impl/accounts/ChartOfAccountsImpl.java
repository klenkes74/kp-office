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

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

import de.kaiserpfalzedv.office.finance.accounting.AccountNotMappedException;
import de.kaiserpfalzedv.office.finance.accounting.accounts.Account;
import de.kaiserpfalzedv.office.finance.accounting.accounts.ChartOfAccounts;
import de.kaiserpfalzedv.office.finance.accounting.accounts.ChartedAccount;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 03.01.16 17:38
 */
public class ChartOfAccountsImpl implements ChartOfAccounts {
    private static final long                            serialVersionUID = -1012983357060344008L;
    private final        HashMap<String, ChartedAccount> data             = new HashMap<>();
    private UUID   tenantId;
    private UUID   id;
    private String displayName;
    private String fullName;


    ChartOfAccountsImpl(UUID tenantId, UUID id, String displayName, String fullName) {
        this.tenantId = tenantId;
        this.id = id;
        this.displayName = displayName;
        this.fullName = fullName;
    }


    @Override
    public ChartedAccount put(
            final UUID tenantId, final String accountNumber,
            final String displayName, final String fullName,
            final Set<? extends Account> accounts
    ) {
        return put(accountNumber, new ChartedAccountBuilder()
                .withTenantId(tenantId)
                .withAccountNumber(accountNumber)
                .withDisplayName(displayName)
                .withFullName(fullName)
                .withAccounts(accounts)
                .build()
        );
    }

    @Override
    public ChartedAccount put(String accountNumber, ChartedAccount account) {
        ChartedAccount result = new ChartedAccountBuilder()
                .withChartedAccount(account)
                .withAccountNumber(accountNumber)
                .build();

        if (data.containsKey(accountNumber)) {
            data.remove(accountNumber);
        }

        data.put(accountNumber, result);

        return result;
    }


    @Override
    public ChartedAccount get(String accountNumber) throws AccountNotMappedException {
        if (!data.containsKey(accountNumber))
            throw new AccountNotMappedException(accountNumber);


        return data.get(accountNumber);
    }

    @Override
    public void remove(final String key) {
        data.remove(key);
    }

    @Override
    public void clear() {
        data.clear();
    }

    @Override
    public UUID getTenantId() {
        return tenantId;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String getFullName() {
        return fullName;
    }
}
