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

import de.kaiserpfalzedv.office.common.data.BuilderException;
import de.kaiserpfalzedv.office.finance.accounting.AccountNotMappedException;
import de.kaiserpfalzedv.office.finance.accounting.accounts.Account;
import org.apache.commons.lang3.builder.Builder;

import java.util.ArrayList;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * The builder for building a default account. May be overwritten for other purposes.
 *
 * @author klenkes
 * @version 2015Q1
 * @since 29.12.15 19:14
 */
public class AccountBuilder implements Builder<Account> {
    private UUID   tenantId;
    private UUID   id;
    private String displayName;
    private String fullName;
    private String accountNumber;

    @Override
    public Account build() {
        setDefaultValues();
        validate();

        return generateObject(tenantId, id, accountNumber, displayName, fullName);
    }

    /**
     * Method to generate the account. This is the method that needs to be overwritten to generate another type of
     * account.
     *
     * @param tenantId    The tenant for which this account exists.
     * @param id          The id of the account.
     * @param displayName The display name of the account.
     * @param fullName    The full name of the account.
     *
     * @return An object of type AccountImpl.
     */
    private AccountImpl generateObject(final UUID tenantId, final UUID id, final String accountNumber, final String displayName, final String fullName) {
        return new AccountImpl(tenantId, id, accountNumber, displayName, fullName);
    }

    /**
     * Sets default values if not initialized by the user.
     */
    private void setDefaultValues() {
        if (id == null) {
            id = UUID.randomUUID();
        }

        if (isBlank(displayName)) {
            displayName = fullName;
        }

        if (isBlank(fullName)) {
            fullName = displayName;
        }
    }

    private void validate() {
        ArrayList<String> failures = new ArrayList<>(4);

        if (tenantId == null) failures.add("Account needs a tenant!");
        if (id == null) failures.add("Account needs an unique id!");
        if (isBlank(displayName)) failures.add("Account needs a display name!");
        if (isBlank(fullName)) failures.add("Account needs a full name!");

        if (failures.size() != 0) {
            throw new BuilderException(Account.class, failures.toArray(new String[1]));
        }
    }


    public AccountBuilder withAccount(Account account) {
        withTenantId(account.getTenantId());
        withId(account.getId());
        withDisplayName(account.getDisplayname());
        withFullName(account.getFullname());

        try {
            withAccountNumber(account.getCurrentAccountId());
        } catch (AccountNotMappedException e) {
            // Nothing to do. It will have no mapping in this situation.
        }

        return this;
    }


    public AccountBuilder withTenantId(UUID tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public AccountBuilder withId(UUID id) {
        this.id = id;
        return this;
    }

    public AccountBuilder withAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public AccountBuilder withDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public AccountBuilder withFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }
}
