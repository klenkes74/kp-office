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

import de.kaiserpfalzedv.office.common.data.Identifyable;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * A sorted {@link Map} of the mapped accounts.
 *
 * @author klenkes
 * @version 2015Q1
 * @since 03.01.16 17:22
 */
public interface ChartOfAccounts extends Identifyable, Serializable {
    Account put(final String accountNumber, final Account account);

    Account remove(final String accountNumber, final Account account);

    Set<Account> remove(final String accountNumber);


    Set<Account> get(final String accountNumber);

    String get(final Account account) throws AccountNotMappedException;

    String get(final UUID accountId) throws AccountNotMappedException;

    void clear(final String accountNumber);

    void clear();
}
