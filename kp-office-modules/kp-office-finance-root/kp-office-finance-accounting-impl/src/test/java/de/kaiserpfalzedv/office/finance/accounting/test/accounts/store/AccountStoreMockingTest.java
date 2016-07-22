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

package de.kaiserpfalzedv.office.finance.accounting.test.accounts.store;

import java.util.UUID;

import de.kaiserpfalzedv.office.common.data.ObjectDoesNotExistException;
import de.kaiserpfalzedv.office.common.data.ObjectExistsException;
import de.kaiserpfalzedv.office.common.data.Pageable;
import de.kaiserpfalzedv.office.common.data.PagedListable;
import de.kaiserpfalzedv.office.common.impl.NullTenant;
import de.kaiserpfalzedv.office.common.impl.data.PageableBuilder;
import de.kaiserpfalzedv.office.finance.accounting.AccountInUseException;
import de.kaiserpfalzedv.office.finance.accounting.AccountStore;
import de.kaiserpfalzedv.office.finance.accounting.accounts.Account;
import de.kaiserpfalzedv.office.finance.accounting.impl.accounts.AccountBuilder;
import de.kaiserpfalzedv.office.finance.accounting.impl.accounts.store.AccountStoreMockingImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 03.01.16 10:41
 */
public class AccountStoreMockingTest {
    private AccountStore service;

    @Before
    public void setUp() {
        service = new AccountStoreMockingImpl();
    }


    @Test
    public void testCreationOfNewAccount() throws ObjectExistsException {
        Account data = new AccountBuilder()
                .withTenantId(new NullTenant().getId())
                .withDisplayName("Test Account 1")
                .withFullName("Test Account 1 Fullname")
                .build();

        Account result = service.createAccount(data);

        assertEquals(result.getId(), data.getId());
    }


    @Test
    public void testCreationOfDoubleIdAccount() throws ObjectExistsException {
        UUID id = UUID.randomUUID();
        Account originalAccount = new AccountBuilder()
                .withId(id)
                .withTenantId(new NullTenant().getId())
                .withDisplayName("Test Account 1")
                .withFullName("Test Account 1 Fullname")
                .build();

        service.createAccount(originalAccount);

        Account secondAccount = new AccountBuilder()
                .withTenantId(new NullTenant().getId())
                .withDisplayName("Test Account 2")
                .withFullName("Test Account 2 Fullname")
                .withId(id)
                .build();

        try {
            service.createAccount(secondAccount);
            fail("The second account should not be created!");
        } catch (ObjectExistsException e) {
            assertEquals(id, ((Account) e.getExistingObject()).getId());
        }
    }


    @Test
    public void testRetrieveSingleAccount() throws ObjectExistsException, ObjectDoesNotExistException {
        Account originalAccount = new AccountBuilder()
                .withTenantId(new NullTenant().getId())
                .withDisplayName("Test Account 1")
                .withFullName("Test Account 1 Fullname")
                .build();
        service.createAccount(originalAccount);


        Account result = service.retrieveAccount(originalAccount.getId());

        assertEquals(originalAccount.getId(), result.getId());
        assertNotEquals(System.identityHashCode(originalAccount), System.identityHashCode(result));
    }


    @Test(expected = ObjectDoesNotExistException.class)
    public void testRetrieveSingleNonExistingAccount() throws ObjectExistsException, ObjectDoesNotExistException {
        Account originalAccount = new AccountBuilder()
                .withTenantId(new NullTenant().getId())
                .withDisplayName("Test Account 1")
                .withFullName("Test Account 1 Fullname")
                .build();
        service.createAccount(originalAccount);

        service.retrieveAccount(UUID.randomUUID());
    }


    @Test
    public void testLoadingEmptyAccountList() {
        PagedListable<Account> result = service.retrieveAccounts();

        assertEquals(0, result.getPage().getTotalCount());
    }

    @Test
    public void testLoadingAccountList() throws ObjectExistsException {
        Account originalAccount = new AccountBuilder()
                .withTenantId(new NullTenant().getId())
                .withDisplayName("Test Account 1")
                .withFullName("Test Account 1 Fullname")
                .build();
        service.createAccount(originalAccount);

        PagedListable<Account> result = service.retrieveAccounts();

        assertEquals(1, result.getPage().getTotalCount());
        assertEquals(0, result.getPage().getPage());

        assertEquals(1, result.getEntries().size());
    }

    @Test
    public void testLoadingMultiplePageAccountList() throws ObjectExistsException {
        UUID tenantId = new NullTenant().getId();

        for (int i = 1; i <= 100; i++) {
            Account data = new AccountBuilder()
                    .withTenantId(tenantId)
                    .withDisplayName("Test Account " + i)
                    .withFullName("Test account " + i + " Fullname")
                    .build();

            service.createAccount(data);
        }

        Pageable page = new PageableBuilder()
                .withPage(2)
                .withSize(20)
                .build();

        PagedListable<Account> result = service.retrieveAccounts(page);

        assertEquals(5, result.getPage().getTotalPages());
        assertEquals(2, result.getPage().getPage());
        assertEquals(20, result.getEntries().size());
    }


    @Test
    public void testDeletionOfExistingAccount() throws ObjectExistsException, AccountInUseException {
        Account data = new AccountBuilder()
                .withTenantId(new NullTenant().getId())
                .withDisplayName("Test Account 1")
                .withFullName("Test Account 1 Fullname")
                .build();

        service.createAccount(data);

        service.deleteAccount(data.getId());
    }


    @Test
    public void testDeletionOfNonExistingAccount() throws ObjectExistsException, AccountInUseException {
        service.deleteAccount(UUID.randomUUID());
    }
}
