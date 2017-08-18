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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import de.kaiserpfalzedv.office.finance.accounting.AccountMultiMappedException;
import de.kaiserpfalzedv.office.finance.accounting.accounts.Account;
import de.kaiserpfalzedv.office.finance.accounting.accounts.ChartedAccount;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

/**
 * @author klenkes  {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 0.3.0
 * @since 2016-03-25
 */
public class ChartedAccountImpl implements ChartedAccount {
    private final UUID   tenantId;
    private final String accountNumber;

    private final String displayName;
    private final String fullName;

    private final HashSet<Account> accounts = new HashSet<>();

    ChartedAccountImpl(
            final UUID tenantId, final String accountNumber, final String displayName, final String fullName,
            final Set<? extends Account> accounts
    ) {
        this.tenantId = tenantId;
        this.accountNumber = accountNumber;

        this.displayName = displayName;
        this.fullName = fullName;

        this.accounts.addAll(accounts);
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
                .append(accountNumber)
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
        ChartedAccount rhs = (ChartedAccount) obj;
        return new EqualsBuilder()
                .append(this.getTenantId(), rhs.getTenantId())
                .append(this.getAccountNumber(), rhs.getAccountNumber())
                .isEquals();
    }

    @Override
    public UUID getTenantId() {
        return tenantId;
    }

    @Override
    public String getAccountNumber() {
        return accountNumber;
    }

    @Override
    public Set<? extends Account> getAccounts() {
        return Collections.unmodifiableSet(accounts);
    }

    @Override
    public Account getBookableAccount() throws AccountMultiMappedException {
        if (!isDirectlyBookable()) {
            throw new AccountMultiMappedException(this);
        }

        return accounts.iterator().next();
    }

    @Override
    public boolean isDirectlyBookable() {
        return (accounts.size() == 1);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, SHORT_PREFIX_STYLE)
                .append("tenantId", tenantId)
                .append("accountNumber", accountNumber)
                .append("displayName", displayName)
                .toString();
    }
}
