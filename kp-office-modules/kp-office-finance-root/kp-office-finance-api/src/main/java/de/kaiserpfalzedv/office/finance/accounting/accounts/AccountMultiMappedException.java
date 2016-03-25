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

package de.kaiserpfalzedv.office.finance.accounting.accounts;

import javax.security.auth.login.AccountException;
import java.util.HashSet;
import java.util.Set;

/**
 * This exception is thrown if a mapping of an unmapped account is requested.
 *
 * @author klenkes
 * @version 2015Q1
 * @since 03.01.16 12:05
 */
public class AccountMultiMappedException extends AccountException {
    private static final long                       serialVersionUID = 5149856876016421022L;
    private final        HashSet<? extends Account> accounts         = new HashSet<>();
    private ChartedAccount account;


    public AccountMultiMappedException(final ChartedAccount account, Set<? extends Account> mappedAccounts) {
        super("Account '" + account.getAccountNumber()
                      + "' (" + account.getDisplayname() + ") is mapped multiple times.");

        this.account = account;
        mappedAccounts.forEach(accounts::add);
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
    public HashSet<? extends Account> getAccounts() {
        return accounts;
    }
}
