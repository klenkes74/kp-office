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

import de.kaiserpfalzedv.office.common.api.data.Nameable;
import de.kaiserpfalzedv.office.common.api.data.Tenantable;
import de.kaiserpfalzedv.office.finance.chartofaccounts.api.account.Account;
import de.kaiserpfalzedv.office.finance.chartofaccounts.api.account.AccountMultiMappedException;

/**
 * @author klenkes  {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 0.3.0
 * @since 2016-03-24
 */
public interface ChartedAccount extends Tenantable, Nameable, Serializable {
    /**
     * @return The account number displayed to the user for this charted account.
     */
    String getAccountNumber();

    /**
     * @return The account the posting record can be put to.
     *
     * @throws AccountMultiMappedException If there is no unique mapping.
     */
    default Account getBookableAccount() throws AccountMultiMappedException {
        if (!isDirectlyBookable()) {
            throw new AccountMultiMappedException(this);
        }

        return getAccounts().iterator().next();
    }

    /**
     * If there is only one account mapped to this charted account then it can be used directly in posting records.
     * Otherwise the user has to select to which "real" account the entry should be booked.
     *
     * @return TRUE if the account can be used directly for posted entries.
     */
    default boolean isDirectlyBookable() {
        return getAccounts().size() == 1;
    }

    /**
     * @return The set of accounts mapped to this charted account.
     */
    Set<? extends Account> getAccounts();
}
