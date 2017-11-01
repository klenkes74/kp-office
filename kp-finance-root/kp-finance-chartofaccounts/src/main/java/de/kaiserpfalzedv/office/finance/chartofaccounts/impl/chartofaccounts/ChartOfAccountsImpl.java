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

import de.kaiserpfalzedv.office.finance.chartofaccounts.api.account.Account;
import de.kaiserpfalzedv.office.finance.chartofaccounts.api.account.AccountNotMappedException;
import de.kaiserpfalzedv.office.finance.chartofaccounts.api.chartofaccounts.ChartOfAccounts;
import de.kaiserpfalzedv.office.finance.chartofaccounts.api.chartofaccounts.ChartedAccount;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 03.01.16 17:38
 */
public class ChartOfAccountsImpl implements ChartOfAccounts {
    private static final long serialVersionUID = -2800020009455576751L;

    private final HashMap<String, ChartedAccount> data = new HashMap<>();
    private UUID tenantId;
    private UUID id;
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
    public boolean remove(final String key) {
        data.remove(key);

        return !data.containsKey(key);
    }

    @Override
    public boolean clear() {
        data.clear();

        return data.isEmpty();
    }

    @Override
    public UUID getTenant() {
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

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(tenantId)
                .append(id)
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        ChartOfAccountsImpl rhs = (ChartOfAccountsImpl) obj;
        return new EqualsBuilder()
                .append(this.tenantId, rhs.tenantId)
                .append(this.id, rhs.id)
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("tenantId", tenantId)
                .append("displayName", displayName)
                .toString();
    }
}
