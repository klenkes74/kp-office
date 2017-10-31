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

package de.kaiserpfalzedv.office.finance.chartofaccounts.api.chartofaccounts;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

import de.kaiserpfalzedv.office.common.api.data.Identifiable;
import de.kaiserpfalzedv.office.common.api.data.Nameable;
import de.kaiserpfalzedv.office.common.api.data.Tenantable;
import de.kaiserpfalzedv.office.finance.chartofaccounts.api.account.Account;
import de.kaiserpfalzedv.office.finance.chartofaccounts.api.account.AccountNotMappedException;

/**
 * This is the chart of accounts as used by humans to order the accounts. Normally the accounts get numbers attached. In
 * KP Office the accounts may be mapped with multiple chart of accounts so the accounts itself are referenced by their
 * internal UUID. Multiple accounts may be mapped to a unique account number within a chart of accounts.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 0.3.0
 * @since 2016-01-03
 */
public interface ChartOfAccounts extends Identifiable, Nameable, Tenantable, Serializable {
    /**
     * Adds the given accounts to the chart of accounts referenced by the accountNumber. If the charted account already
     * exists, the given accounts are added to the already existing accounts.
     *
     * @param accountNumber The reference number/name of this account.
     * @param displayName   The display name of the charted account.
     * @param fullName      The full name of the charted account.
     * @param accounts      The set of accounts to use for this single charted account.
     *
     * @return The charted account created or updated to be used in human interaction.
     */
    ChartedAccount put(
            final UUID tenantId, final String accountNumber, final String displayName, final String fullName,
            final Set<? extends Account> accounts
    );


    ChartedAccount put(final String accountNumber, final ChartedAccount account);

    /**
     * Retrieves the charted account referenced by the accountNumber given.
     *
     * @param accountNumber The account number reference to be searched for.
     *
     * @return The referenced account
     *
     * @throws AccountNotMappedException If the accountNumber is not listed in this chart of accounts.
     */
    ChartedAccount get(final String accountNumber) throws AccountNotMappedException;

    /**
     * Removes the charted account from this chart of accounts.
     *
     * @param accountNumber The reference of the charted account to be removed from the chart of accounts.
     *
     * @return if the account has been removed.
     */
    boolean remove(final String accountNumber);

    /**
     * Remove all accounts from this chart.
     *
     * @return If all accounts have been wiped out.
     */
    boolean clear();
}
