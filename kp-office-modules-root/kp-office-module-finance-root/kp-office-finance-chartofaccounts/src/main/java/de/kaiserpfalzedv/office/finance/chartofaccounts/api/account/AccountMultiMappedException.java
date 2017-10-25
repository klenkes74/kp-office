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

package de.kaiserpfalzedv.office.finance.chartofaccounts.api.account;

import java.util.Set;

import javax.security.auth.login.AccountException;

import de.kaiserpfalzedv.office.finance.chartofaccounts.api.chartofaccounts.ChartedAccount;

/**
 * This exception is thrown if a mapping of an unmapped account is requested.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 0.3.0
 * @since 2016-03-23
 */
public class AccountMultiMappedException extends AccountException {
    private static final long serialVersionUID = 3842579341544828799L;

    private ChartedAccount account;

    public AccountMultiMappedException(final ChartedAccount account) {
        super("Account '" + account.getAccountNumber()
                      + "' (" + account.getDisplayName() + ") is mapped multiple times.");

        this.account = account;
    }

    /**
     * @return The charted account that can't be mapped to a single account.
     */
    public ChartedAccount getAccount() {
        return account;
    }

    /**
     * @return The complete set of accounts the charted account is mapped to.
     */
    public Set<? extends Account> getAccounts() {
        return account.getAccounts();
    }
}
