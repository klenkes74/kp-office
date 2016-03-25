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

import de.kaiserpfalzedv.office.finance.accounting.AccountNotMappedException;
import de.kaiserpfalzedv.office.finance.accounting.accounts.Account;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.UUID;

/**
 * The immutable account object.
 *
 * @author klenkes
 * @version 2015Q1
 * @since 28.12.15 07:13
 */
public class AccountImpl implements Account {
    private static final long serialVersionUID = -3544817616044320036L;

    private UUID   tenantId;
    private UUID   id;
    private String displayName;
    private String fullName;

    private String accountMapping;


    AccountImpl(final UUID tenantId, final UUID id, final String accountNumber, final String displayName, final String fullName) {
        this.tenantId = tenantId;
        this.id = id;
        this.displayName = displayName;
        this.fullName = fullName;

        this.accountMapping = accountNumber;
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
    public UUID getTenantId() {
        return tenantId;
    }

    @Override
    public String getCurrentAccountId() throws AccountNotMappedException {
        if (accountMapping == null)
            throw new AccountNotMappedException(this);

        return accountMapping;
    }

    @Override
    public void setCurrentAccountId(String accountId) {
        this.accountMapping = accountId;
    }

    @Override
    public int compareTo(Account o) {
        if (accountMapping != null) {
            try {
                return accountMapping.compareTo(o.getCurrentAccountId());
            } catch (AccountNotMappedException e) {
                return 1;
            }
        } else {
            try {
                o.getCurrentAccountId();

                return -1;
            } catch (AccountNotMappedException e) {
                return id.compareTo(o.getId());
            }
        }
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append(System.identityHashCode(this))
                .append("mapping", accountMapping)
                .append("displayName", displayName)
                .append("id", id)
                .append("tenantId", tenantId)
                .toString();
    }
}
