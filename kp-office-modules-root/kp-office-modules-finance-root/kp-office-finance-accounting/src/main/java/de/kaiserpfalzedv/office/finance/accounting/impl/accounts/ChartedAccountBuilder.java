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

package de.kaiserpfalzedv.office.finance.accounting.impl.accounts;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import de.kaiserpfalzedv.office.common.api.BuilderException;
import de.kaiserpfalzedv.office.finance.accounting.api.accounts.Account;
import de.kaiserpfalzedv.office.finance.accounting.api.accounts.ChartedAccount;
import de.kaiserpfalzedv.office.tenant.api.NullTenant;
import org.apache.commons.lang3.builder.Builder;

/**
 * This builder returns a ChartedAccount that is build from the values given.
 *
 * @author klenkes  {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 0.3.0
 * @since 2016-03-25
 */
public class ChartedAccountBuilder implements Builder<ChartedAccount> {
    private final HashSet<Account> accounts = new HashSet<>();
    private UUID   tenantId;
    private String accountNumber;
    private String displayName;
    private String fullName;

    @Override
    public ChartedAccount build() {
        setDefaultValues();

        validate();

        return new ChartedAccountImpl(tenantId, accountNumber, displayName, fullName, accounts);
    }

    private void setDefaultValues() {
        if (tenantId == null) {
            this.tenantId = new NullTenant().getId();
        }

        if (accounts.size() >= 1) {
            Account mainAccount = accounts.iterator().next();

            if (displayName == null) {
                this.displayName = mainAccount.getDisplayName();
            }

            if (fullName == null) {
                this.fullName = mainAccount.getFullName();
            }
        }
    }

    public void validate() throws BuilderException {
        ArrayList<String> failures = new ArrayList<>();

        if (accounts.size() == 0) {
            failures.add("No mapped account!");
        }

        if (displayName == null) {
            failures.add("An account needs a displayable name!");
        }

        if (fullName == null) {
            failures.add("An account needs a full name!");
        }

        if (failures.size() > 0) {
            throw new BuilderException(ChartedAccount.class, failures.toArray(new String[1]));
        }
    }


    public ChartedAccountBuilder withChartedAccount(final ChartedAccount account) {
        withTenantId(account.getTenant());
        withAccountNumber(account.getAccountNumber());
        withDisplayName(account.getDisplayName());
        withFullName(account.getFullName());
        withAccounts(account.getAccounts());

        return this;
    }


    public ChartedAccountBuilder withTenantId(final UUID tenantId) {
        this.tenantId = tenantId;

        return this;
    }

    public ChartedAccountBuilder withAccountNumber(final String accountNumber) {
        this.accountNumber = accountNumber;

        return this;
    }

    public ChartedAccountBuilder withDisplayName(final String displayName) {
        this.displayName = displayName;

        return this;
    }

    public ChartedAccountBuilder withFullName(final String fullName) {
        this.fullName = fullName;

        return this;
    }


    public ChartedAccountBuilder withAccounts(final Set<? extends Account> accounts) {
        this.accounts.clear();

        if (accounts != null)
            this.accounts.addAll(accounts);

        return this;
    }

    public ChartedAccountBuilder addAccount(final Account account) {
        this.accounts.add(account);

        return this;
    }

    public ChartedAccountBuilder removeAccount(final Account account) {
        this.accounts.remove(account);

        return this;
    }
}
