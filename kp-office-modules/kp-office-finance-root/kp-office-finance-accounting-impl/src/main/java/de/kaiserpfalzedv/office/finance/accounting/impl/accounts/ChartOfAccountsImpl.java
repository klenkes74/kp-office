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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 03.01.16 17:38
 */
public class ChartOfAccountsImpl implements ChartOfAccounts {
    private static final long serialVersionUID = -1012983357060344008L;


    private final ConcurrentSkipListMap<String, HashSet<Account>> data        = new ConcurrentSkipListMap<>();
    private final ConcurrentSkipListMap<Account, String>          byAccount   = new ConcurrentSkipListMap<>();
    private final ConcurrentSkipListMap<UUID, String>             byAccountId = new ConcurrentSkipListMap<>();


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
    public Account put(String accountNumber, Account account) {
        if (!data.containsKey(accountNumber)) {
            data.put(accountNumber, new HashSet<>());
        }

        Account added = new AccountBuilder()
                .withAccount(account)
                .withAccountNumber(accountNumber)
                .build();


        data.get(accountNumber).add(added);

        byAccount.put(added, accountNumber);
        byAccountId.put(added.getId(), accountNumber);

        return added;
    }

    @Override
    public Set<Account> get(String accountNumber) {
        if (!data.containsKey(accountNumber))
            return Collections.unmodifiableSet(new HashSet<>());

        return Collections.unmodifiableSet(data.get(accountNumber));
    }

    @Override
    public String get(Account account) throws AccountNotMappedException {
        if (!byAccount.containsKey(account))
            throw new AccountNotMappedException(account);

        return byAccount.get(account);
    }

    @Override
    public String get(UUID accountId) throws AccountNotMappedException {
        if (!byAccountId.containsKey(accountId))
            throw new AccountNotMappedException(accountId);

        return byAccountId.get(accountId);
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
    public String getDisplayname() {
        return displayName;
    }

    @Override
    public String getFullname() {
        return fullName;
    }


    @Override
    public Set<Account> remove(final String key) {
        if (!data.containsKey(key)) {
            return new HashSet<>();
        }

        Set<Account> result = new HashSet<>(data.get(key).size());
        data.get(key).forEach(result::add);

        data.get(key).forEach(e -> {
            byAccount.remove(e);
            byAccountId.remove(e.getId());
        });
        data.remove(key);

        return result;
    }

    @Override
    public Account remove(final String accountNumber, final Account account) {
        byAccount.remove(account);
        byAccountId.remove(account.getId());

        try {
            data.get(accountNumber).remove(account);
        } catch (NullPointerException e) {
            // No mapping for accountNumber exists. So the removal is automatically successful :-)
        }

        return account;
    }

    @Override
    public void clear() {
        data.clear();
        byAccount.clear();
        byAccountId.clear();
    }


    @Override
    public void clear(String accountNumber) {
        try {
            data.get(accountNumber).forEach(e -> {
                byAccount.remove(e);
                byAccountId.remove(e.getId());
            });

            data.remove(accountNumber);
        } catch (NullPointerException e) {
            // No mapping for accountNumber exists. So the removal is automatically successful :-)
        }
    }
}
