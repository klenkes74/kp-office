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

package de.kaiserpfalzedv.office.finance.chartofaccounts.account.test;

import de.kaiserpfalzedv.commons.api.BuilderException;
import de.kaiserpfalzedv.iam.tenant.api.NullTenant;
import de.kaiserpfalzedv.office.finance.chartofaccounts.api.account.CostCenter;
import de.kaiserpfalzedv.office.finance.chartofaccounts.impl.CostCenterBuilder;
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
public class CostCenterBuilderTest {
    private static final UUID DEFAULT_TENANT_ID = new NullTenant().getId();
    private static final UUID DEFAULT_ID = UUID.randomUUID();
    private static final String DEFAULT_DISPLAY_NAME = "Account Display Name";
    private static final String DEFAULT_FULL_NAME = "Account Full Name";

    private CostCenterBuilder service;

    @Before
    public void setUp() throws Exception {
        service = new CostCenterBuilder();
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

        CostCenter result = service.build();

        assertEquals(DEFAULT_TENANT_ID, result.getTenant());
        assertEquals(DEFAULT_ID, result.getId());
        assertEquals(DEFAULT_DISPLAY_NAME, result.getDisplayName());
        assertEquals(DEFAULT_FULL_NAME, result.getFullName());
    }


    @Test(expected = BuilderException.class)
    public void testNoTenantBuild() {
        service
                .withId(DEFAULT_ID)
                .withDisplayName(DEFAULT_DISPLAY_NAME)
                .withFullName(DEFAULT_FULL_NAME);

        CostCenter result = service.build();

        assertEquals(DEFAULT_TENANT_ID, result.getTenant());
        assertEquals(DEFAULT_ID, result.getId());
        assertEquals(DEFAULT_DISPLAY_NAME, result.getDisplayName());
        assertEquals(DEFAULT_FULL_NAME, result.getFullName());
    }


    @Test
    public void testNoIdBuild() {
        service
                .withTenantId(DEFAULT_TENANT_ID)
                .withDisplayName(DEFAULT_DISPLAY_NAME)
                .withFullName(DEFAULT_FULL_NAME);

        CostCenter result = service.build();

        assertEquals(DEFAULT_TENANT_ID, result.getTenant());
        assertNotEquals(DEFAULT_ID, result.getId());
        assertEquals(DEFAULT_DISPLAY_NAME, result.getDisplayName());
        assertEquals(DEFAULT_FULL_NAME, result.getFullName());
    }


    @Test
    public void testNoDisplayNameBuild() {
        service
                .withTenantId(DEFAULT_TENANT_ID)
                .withId(DEFAULT_ID)
                .withFullName(DEFAULT_FULL_NAME);

        CostCenter result = service.build();

        assertEquals(DEFAULT_TENANT_ID, result.getTenant());
        assertEquals(DEFAULT_ID, result.getId());
        assertEquals(DEFAULT_FULL_NAME, result.getDisplayName());
        assertEquals(DEFAULT_FULL_NAME, result.getFullName());
    }


    @Test
    public void testNoFullNameBuild() {
        service
                .withTenantId(DEFAULT_TENANT_ID)
                .withId(DEFAULT_ID)
                .withDisplayName(DEFAULT_DISPLAY_NAME);

        CostCenter result = service.build();

        assertEquals(DEFAULT_TENANT_ID, result.getTenant());
        assertEquals(DEFAULT_ID, result.getId());
        assertEquals(DEFAULT_DISPLAY_NAME, result.getDisplayName());
        assertEquals(DEFAULT_DISPLAY_NAME, result.getFullName());
    }
}