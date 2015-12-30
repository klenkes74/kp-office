/*
 * Copyright 2015 Kaiserpfalz EDV-Service, Roland T. Lichti
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

import de.kaiserpfalzedv.office.common.data.ObjectDoesNotExistException;
import de.kaiserpfalzedv.office.common.data.ObjectExistsException;
import de.kaiserpfalzedv.office.common.data.Pageable;
import de.kaiserpfalzedv.office.common.data.PagedListable;
import de.kaiserpfalzedv.office.common.impl.data.PagedListBuilder;
import de.kaiserpfalzedv.office.finance.accounting.accounts.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.UUID;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 29.12.15 20:05
 */
@Stateless
public class AccountService {
    private static final Logger LOG = LoggerFactory.getLogger(AccountService.class);


    public Account createAccount(final Account account) throws ObjectExistsException {
        return account;
    }

    public Account retrieveAccount(final UUID accountId) throws ObjectDoesNotExistException {
        throw new UnsupportedOperationException("Not yet implemented.");
    }

    public PagedListable<Account> retrieveAccounts() {
        return new PagedListBuilder<Account>()
                .withData(new ArrayList<>())
                .build();
    }

    public PagedListable<Account> retrieveAccounts(Pageable pageable) {
        return new PagedListBuilder<Account>()
                .withData(new ArrayList<>())
                .withPageable(pageable)
                .build();
    }


    public Account updateAccount(final Account account) throws ObjectDoesNotExistException {
        return account;
    }

    public void deleteAccount(final UUID accountId) {

    }
}
