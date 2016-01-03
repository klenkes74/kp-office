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

package de.kaiserpfalzedv.office.finance.accounting;

import de.kaiserpfalzedv.office.common.data.ObjectDoesNotExistException;
import de.kaiserpfalzedv.office.common.data.ObjectExistsException;
import de.kaiserpfalzedv.office.common.data.Pageable;
import de.kaiserpfalzedv.office.common.data.PagedListable;
import de.kaiserpfalzedv.office.finance.accounting.accounts.Account;
import de.kaiserpfalzedv.office.finance.accounting.accounts.AccountInUseException;

import java.util.UUID;

/**
 * The interface for accessing persisted account information. May be implemented multiple times.
 *
 * @author klenkes
 * @version 2015Q1
 * @since 03.01.16 09:53
 */
public interface AccountStore {
    /**
     * Creates the given account and returns the valid stored account object.
     *
     * @param account The data to be stored.
     *
     * @return The account object stored into the database.
     *
     * @throws ObjectExistsException If the object already exists.
     */
    Account createAccount(Account account) throws ObjectExistsException;

    /**
     * Loads the account with the given id.
     *
     * @param accountId The ID of the account to be retrieved.
     *
     * @return The account data.
     *
     * @throws ObjectDoesNotExistException If there is no account with the given ID.
     */
    Account retrieveAccount(UUID accountId) throws ObjectDoesNotExistException;

    /**
     * @return All accounts in a single page.
     */
    PagedListable<Account> retrieveAccounts();

    /**
     * @param pageable The page definition of the accounts to be loaded.
     *
     * @return The defined page of data to be loaded from the data store.
     */
    PagedListable<Account> retrieveAccounts(Pageable pageable);

    /**
     * Updates the account data in the store.
     *
     * @param account The new data to be stored.
     *
     * @return The stored data object.
     *
     * @throws ObjectDoesNotExistException If there is no data set with the account ID in the account object.
     * @throws AccountInUseException       If the account is in use and the given change is not allowed for accounts already
     *                                     used.
     */
    Account updateAccount(Account account) throws ObjectDoesNotExistException, AccountInUseException;

    /**
     * Deletes the given account.
     *
     * @param accountId The ID of the account to be deleted.
     *
     * @throws AccountInUseException If the account is in use and can't be deleted for that reason.
     */
    void deleteAccount(UUID accountId) throws AccountInUseException;
}
