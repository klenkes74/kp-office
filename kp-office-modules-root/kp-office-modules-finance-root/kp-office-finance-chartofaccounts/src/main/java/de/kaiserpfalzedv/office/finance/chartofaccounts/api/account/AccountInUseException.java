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

import de.kaiserpfalzedv.office.finance.chartofaccounts.api.chartofaccounts.ChartOfAccountsException;

/**
 * The changes demanded could not be made since the account is already (or still) in use.
 *
 * @author klenkes
 * @version 2015Q1
 * @since 03.01.16 09:57
 */
public class AccountInUseException extends ChartOfAccountsException {
    private static final long serialVersionUID = -8074982705357314516L;

    private Account account;

    public AccountInUseException(final Account account) {
        super("The account '" + account.getId() + "' (" + account.getDisplayName() + ") is in use.");

        this.account = account;
    }

    public Account getAccount() {
        return account;
    }
}
