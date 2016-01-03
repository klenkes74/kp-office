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
import java.util.UUID;

/**
 * This exception is thrown if a mapping of an unmapped account is requested.
 *
 * @author klenkes
 * @version 2015Q1
 * @since 03.01.16 12:05
 */
public class AccountNotMappedException extends AccountException {
    private static final long serialVersionUID = -1725667515126633454L;

    public AccountNotMappedException(final Account account) {
        this(account.getId());
    }

    public AccountNotMappedException(final String accountNumber) {
        super("Account '" + accountNumber + "' has no current display number mapping.");
    }

    public AccountNotMappedException(final UUID accountId) {
        this(accountId.toString());
    }
}
