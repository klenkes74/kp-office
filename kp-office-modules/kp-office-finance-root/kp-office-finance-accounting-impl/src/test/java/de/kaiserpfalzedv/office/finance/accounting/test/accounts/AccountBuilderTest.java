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

package de.kaiserpfalzedv.office.finance.accounting.test.accounts;

import de.kaiserpfalzedv.office.common.data.BuilderException;
import de.kaiserpfalzedv.office.common.impl.NullTenant;
import de.kaiserpfalzedv.office.finance.accounting.accounts.Account;
import de.kaiserpfalzedv.office.finance.accounting.impl.accounts.AccountBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


/**
 * @author klenkes
 * @version 2015Q1
 * @since 29.12.15 19:36
 */
public class AccountBuilderTest {
    private static final UUID   DEFAULT_TENANT_ID    = new NullTenant().getId();
    private static final UUID   DEFAULT_ID           = UUID.randomUUID();
    private static final String DEFAULT_DISPLAY_NAME = "Account Display Name";
    private static final String DEFAULT_FULL_NAME    = "Account Full Name";

    private AccountBuilder service;

    @Before
    public void setUp() throws Exception {
        service = new AccountBuilder();
    }

    @Test(expected = BuilderException.class)
    public void testEmptyBuild() {
        service.build();
    }

    @Test
    public void testFullBuild() {
        service
                .withTenantId(DEFAULT_TENANT_ID)
                .withId(DEFAULT_ID)
                .withDisplayName(DEFAULT_DISPLAY_NAME)
                .withFullName(DEFAULT_FULL_NAME);

        Account result = service.build();

        assertEquals(DEFAULT_TENANT_ID, result.getTenantId());
        assertEquals(DEFAULT_ID, result.getId());
        assertEquals(DEFAULT_DISPLAY_NAME, result.getDisplayname());
        assertEquals(DEFAULT_FULL_NAME, result.getFullname());
    }


    @Test(expected = BuilderException.class)
    public void testNoTenantBuild() {
        service
                .withId(DEFAULT_ID)
                .withDisplayName(DEFAULT_DISPLAY_NAME)
                .withFullName(DEFAULT_FULL_NAME);

        Account result = service.build();

        assertEquals(DEFAULT_TENANT_ID, result.getTenantId());
        assertEquals(DEFAULT_ID, result.getId());
        assertEquals(DEFAULT_DISPLAY_NAME, result.getDisplayname());
        assertEquals(DEFAULT_FULL_NAME, result.getFullname());
    }


    @Test
    public void testNoIdBuild() {
        service
                .withTenantId(DEFAULT_TENANT_ID)
                .withDisplayName(DEFAULT_DISPLAY_NAME)
                .withFullName(DEFAULT_FULL_NAME);

        Account result = service.build();

        assertEquals(DEFAULT_TENANT_ID, result.getTenantId());
        assertNotEquals(DEFAULT_ID, result.getId());
        assertEquals(DEFAULT_DISPLAY_NAME, result.getDisplayname());
        assertEquals(DEFAULT_FULL_NAME, result.getFullname());
    }


    @Test
    public void testNoDisplayNameBuild() {
        service
                .withTenantId(DEFAULT_TENANT_ID)
                .withId(DEFAULT_ID)
                .withFullName(DEFAULT_FULL_NAME);

        Account result = service.build();

        assertEquals(DEFAULT_TENANT_ID, result.getTenantId());
        assertEquals(DEFAULT_ID, result.getId());
        assertEquals(DEFAULT_FULL_NAME, result.getDisplayname());
        assertEquals(DEFAULT_FULL_NAME, result.getFullname());
    }


    @Test
    public void testNoFullNameBuild() {
        service
                .withTenantId(DEFAULT_TENANT_ID)
                .withId(DEFAULT_ID)
                .withDisplayName(DEFAULT_DISPLAY_NAME);

        Account result = service.build();

        assertEquals(DEFAULT_TENANT_ID, result.getTenantId());
        assertEquals(DEFAULT_ID, result.getId());
        assertEquals(DEFAULT_DISPLAY_NAME, result.getDisplayname());
        assertEquals(DEFAULT_DISPLAY_NAME, result.getFullname());
    }


    @Test
    public void testComaratorEqual() {
        service
                .withTenantId(DEFAULT_TENANT_ID)
                .withId(DEFAULT_ID)
                .withDisplayName(DEFAULT_DISPLAY_NAME);

        Account o1 = service.build();
        o1.setCurrentAccountId("1000");

        Account o2 = service.build();
        o2.setCurrentAccountId("1000");

        assertEquals(0, o1.compareTo(o2));
        assertEquals(0, o2.compareTo(o1));
    }

    @Test
    public void testComaratorNotEqual() {
        service
                .withTenantId(DEFAULT_TENANT_ID)
                .withId(DEFAULT_ID)
                .withDisplayName(DEFAULT_DISPLAY_NAME);

        Account o1 = service.build();
        o1.setCurrentAccountId("1000");

        Account o2 = service.build();
        o2.setCurrentAccountId("2000");

        assertEquals(-1, o1.compareTo(o2));
        assertEquals(1, o2.compareTo(o1));
    }

    @Test
    public void testComaratorOneNotMapped() {
        service
                .withTenantId(DEFAULT_TENANT_ID)
                .withId(DEFAULT_ID)
                .withDisplayName(DEFAULT_DISPLAY_NAME);

        Account o1 = service.build();
        o1.setCurrentAccountId("1000");

        Account o2 = service.build();

        assertEquals(1, o1.compareTo(o2));
        assertEquals(-1, o2.compareTo(o1));
    }

    @Test
    public void testComaratorBothNotMapped() {
        service
                .withTenantId(DEFAULT_TENANT_ID)
                .withId(DEFAULT_ID)
                .withDisplayName(DEFAULT_DISPLAY_NAME);

        Account o1 = service.build();

        service.withId(UUID.randomUUID());
        Account o2 = service.build();

        assertEquals(o1.getId().compareTo(o2.getId()), o1.compareTo(o2));
        assertEquals(o2.getId().compareTo(o1.getId()), o2.compareTo(o1));
    }
}