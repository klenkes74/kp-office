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
import de.kaiserpfalzedv.commons.api.init.Closeable;
import de.kaiserpfalzedv.commons.api.init.Initializable;
import de.kaiserpfalzedv.commons.api.init.InitializationException;
import de.kaiserpfalzedv.office.finance.chartofaccounts.api.account.AccountNotMappedException;
import de.kaiserpfalzedv.office.finance.chartofaccounts.api.chartofaccounts.ChartOfAccounts;
import de.kaiserpfalzedv.office.finance.chartofaccounts.api.chartofaccounts.ChartOfAccountsDoesNotExistException;
import de.kaiserpfalzedv.office.finance.chartofaccounts.api.chartofaccounts.ChartedAccount;
import de.kaiserpfalzedv.office.finance.chartofaccounts.api.chartofaccounts.ChartsOfAccountStore;
import de.kaiserpfalzedv.office.finance.chartofaccounts.impl.chartofaccounts.ChartOfAccountsBuilder;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import javax.ejb.Singleton;
import java.util.*;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-10-26
 */
@Singleton
@Mock
public class ChartsOfAccountStoreMock implements ChartsOfAccountStore, Initializable, Closeable {
    private static final Logger LOG = LoggerFactory.getLogger(ChartsOfAccountStoreMock.class);

    private final HashMap<String, ChartOfAccounts> chartsOfAccounts = new HashMap<>(3);


    @Override
    @PostConstruct
    @PostActivate
    public void init() throws InitializationException {
        Properties properties = new Properties();

        properties.put("type", "file");
        properties.put("accounts.path", "classpath:/accounts.csv");
        properties.put("accounts.type", "csv");
        properties.put("chartsofaccounts.1", "default");
        properties.put("chartsofaccoutns.2", "alternate");

        init(properties);
    }

    @Override
    public void init(Properties properties) throws InitializationException {
        LOG.info("Loading charts of accounts");
    }

    @Override
    @PreDestroy
    @PrePassivate
    public void close() {
        chartsOfAccounts.clear();
    }


    @Override
    public synchronized Set<ChartOfAccounts> load() {
        HashSet<ChartOfAccounts> result = new HashSet<>();

        chartsOfAccounts.values().forEach(coa -> result.add(SerializationUtils.clone(coa)));

        LOG.info("Returning {} charts of accounts to calling service: {}",
                 chartsOfAccounts.size(), chartsOfAccounts.keySet()
        );
        return result;
    }

    @Override
    public synchronized void save(Collection<ChartOfAccounts> chartsOfAccounts) {
        chartsOfAccounts.forEach(coa -> this.chartsOfAccounts.put(coa.getDisplayName(), SerializationUtils.clone(coa)));

        LOG.info("Saved {} charts of accounts: {}", this.chartsOfAccounts.size(), this.chartsOfAccounts.keySet());
    }

    @Override
    public synchronized ChartOfAccounts save(ChartOfAccounts chartOfAccounts) {
        chartsOfAccounts.put(chartOfAccounts.getDisplayName(), SerializationUtils.clone(chartOfAccounts));

        LOG.info("Saved chart of accounts: {}", chartOfAccounts.getDisplayName());
        return chartsOfAccounts.get(chartOfAccounts.getDisplayName());
    }

    @Override
    public synchronized boolean delete(String plan) {
        chartsOfAccounts.remove(plan);

        LOG.info("Deleted chart of accounts: {}", plan);
        return !chartsOfAccounts.containsKey(plan);
    }

    @Override
    public synchronized boolean put(String plan, ChartedAccount account) throws ChartOfAccountsDoesNotExistException {
        LOG.info("Adding account to chart of account: chartOfAccount={}, account={}", plan, account.getAccountNumber());

        try {
            chartsOfAccounts.get(plan).put(account.getAccountNumber(), account);
        } catch (NullPointerException e) {
            ChartOfAccounts newPlan = new ChartOfAccountsBuilder()
                    .withTenantId(account.getTenant())
                    .withDisplayName(plan)
                    .withFullName(plan)
                    .build();

            save(newPlan);
        }

        try {
            return chartsOfAccounts.get(plan).get(account.getAccountNumber()) != null;
        } catch (AccountNotMappedException e) {
            return false;
        }
    }

    @Override
    public synchronized boolean remove(String plan, ChartedAccount account) throws ChartOfAccountsDoesNotExistException {
        LOG.info("Removing account from chart of account: chartOfAccount={}, accoutn={}", plan, account.getAccountNumber());

        try {
            return chartsOfAccounts.get(plan).remove(account.getAccountNumber());
        } catch (NullPointerException e) {
            ChartOfAccounts newPlan = new ChartOfAccountsBuilder()
                    .withTenantId(account.getTenant())
                    .withDisplayName(plan)
                    .withFullName(plan)
                    .build();

            save(newPlan);
        }

        return true;
    }

    @Override
    public synchronized boolean clear(String plan) {
        LOG.info("Clearing all accounts from chart of accounts: {}", plan);

        try {
            return chartsOfAccounts.get(plan).clear();
        } catch (NullPointerException e) {
            ChartOfAccounts newPlan = new ChartOfAccountsBuilder()
                    .withDisplayName(plan)
                    .withFullName(plan)
                    .build();

            save(newPlan);
        }

        return true;
    }
}
