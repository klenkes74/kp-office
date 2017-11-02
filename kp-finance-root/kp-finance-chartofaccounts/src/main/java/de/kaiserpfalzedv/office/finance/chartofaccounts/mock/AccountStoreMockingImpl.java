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

package de.kaiserpfalzedv.office.finance.chartofaccounts.mock;

import de.kaiserpfalzedv.commons.api.cdi.Mock;
import de.kaiserpfalzedv.commons.api.data.ObjectDoesNotExistException;
import de.kaiserpfalzedv.commons.api.data.ObjectExistsException;
import de.kaiserpfalzedv.commons.api.data.Pageable;
import de.kaiserpfalzedv.commons.api.data.PagedListable;
import de.kaiserpfalzedv.commons.api.data.impl.PageableBuilder;
import de.kaiserpfalzedv.commons.api.data.impl.PagedListBuilder;
import de.kaiserpfalzedv.office.finance.chartofaccounts.api.account.Account;
import de.kaiserpfalzedv.office.finance.chartofaccounts.api.account.AccountInUseException;
import de.kaiserpfalzedv.office.finance.chartofaccounts.api.account.AccountStore;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * The mock up for the account store.
 *
 * @author klenkes
 * @version 2015Q1
 * @since 29.12.15 20:05
 */
@Mock
@Singleton
public class AccountStoreMockingImpl implements AccountStore, Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(AccountStoreMockingImpl.class);

    private final ConcurrentSkipListMap<UUID, Account> data = new ConcurrentSkipListMap<>();

    @PostConstruct
    public void init() {
        LOG.info("Initialized account store mocking implementation with id: {}", System.identityHashCode(this));
    }

    @PreDestroy
    public void close() {
        LOG.info("Destroying account store mocking implementation with id: {}", System.identityHashCode(this));
    }


    @Override
    public Account createAccount(final Account account) throws ObjectExistsException {
        LOG.trace("Trying to save account ({}): {}", System.identityHashCode(this), account);

        if (data.get(account.getId()) != null) {
            throw new ObjectExistsException(Account.class, account.getId(), data.get(account.getId()));
        }

        data.put(account.getId(), SerializationUtils.clone(account));

        LOG.info("Saved account ({}): {} -> {}", System.identityHashCode(this), System.identityHashCode(account), data.get(account.getId()));
        return account;
    }

    @Override
    public Account retrieveAccount(final UUID accountId) throws ObjectDoesNotExistException {
        LOG.trace("Trying to load account ({}): {}", System.identityHashCode(this), accountId);

        Account result = data.get(accountId);

        if (result == null) {
            throw new ObjectDoesNotExistException(Account.class, accountId);
        }

        LOG.debug("Loaded account ({}): {}", System.identityHashCode(this), result);
        return result;
    }

    @Override
    public PagedListable<Account> retrieveAccounts() {
        LOG.debug("Retrieving full list of accounts ({}).", System.identityHashCode(this));
        return new PagedListBuilder<Account>()
                .withData(data.values())
                .build();
    }

    @Override
    public PagedListable<Account> retrieveAccounts(Pageable pageable) {
        ArrayList<Account> accounts = new ArrayList<>();
        data.keySet().stream().forEachOrdered(e -> accounts.add(data.get(e)));

        long firstElementIndex = pageable.getPage() * pageable.getSize();
        firstElementIndex = (firstElementIndex <= (accounts.size() - 1)) ? firstElementIndex : accounts.size() - 1;
        long lastElementIndex = ((pageable.getPage() + 1) * pageable.getSize()) - 1;
        lastElementIndex = (lastElementIndex <= (accounts.size() - 1)) ? lastElementIndex : accounts.size() - 1;

        List<Account> resultData = accounts.subList((int) firstElementIndex, (int) lastElementIndex + 1);
        ArrayList<Account> result = new ArrayList<>(resultData.size());
        resultData.forEach(e -> result.add(SerializationUtils.clone(e)));

        Pageable page = new PageableBuilder()
                .withPaging(pageable)
                .withTotalCount(data.size())
                .withTotalPages(data.size() / pageable.getSize() + (data.size() % pageable.getSize() > 0 ? 1 : 0))
                .withSize(result.size())
                .build();

        LOG.debug("Retrieving paged list ({}, elements {}-{}) of accounts ({}): {}", page, firstElementIndex, lastElementIndex, System
                .identityHashCode(this), result);
        return new PagedListBuilder<Account>()
                .withData(result)
                .withPageable(page)
                .build();
    }


    @Override
    public Account updateAccount(final Account account) throws ObjectDoesNotExistException, AccountInUseException {
        LOG.trace("Trying to update account ({}): {}", System.identityHashCode(this), account);

        if (!data.containsKey(account.getId()))
            throw new ObjectDoesNotExistException(Account.class, account.getId());

        data.putIfAbsent(account.getId(), account);
        LOG.info("Changed account data ({}): {} -> {}", System.identityHashCode(this), System.identityHashCode(account), data
                .get(account.getId()));
        return account;
    }

    @Override
    public void deleteAccount(final UUID accountId) throws AccountInUseException {
        LOG.trace("Trying to remove account ({}): {}", System.identityHashCode(this), accountId);

        if (LOG.isTraceEnabled()) {
            LOG.trace("Account to be deleted ({}): {}", System.identityHashCode(this), data.get(accountId));
        }

        data.remove(accountId);

        LOG.info("Removed account with id ({}): {}", System.identityHashCode(this), accountId);
    }
}
