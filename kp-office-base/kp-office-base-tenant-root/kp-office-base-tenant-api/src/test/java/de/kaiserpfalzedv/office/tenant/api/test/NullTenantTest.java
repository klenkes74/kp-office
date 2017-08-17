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

package de.kaiserpfalzedv.office.tenant.api.test;

import java.util.UUID;

import de.kaiserpfalzedv.office.tenant.api.Tenant;
import de.kaiserpfalzedv.office.tenant.api.impl.NullTenant;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 27.12.15 11:48
 */
public class NullTenantTest {
    private static UUID NULL_ID = UUID.fromString("00000000-0000-0000-0000-000000000000");

    private Tenant tenant;


    @Before
    public void setUp() throws Exception {
        tenant = new NullTenant();
    }


    @Test
    public void testGetId() throws Exception {
        assertEquals("The id should be only 0s!", NULL_ID, tenant.getId());
    }

    @Test
    public void testGetDisplayname() throws Exception {
        assertEquals("The display name should be empty!", "", tenant.getDisplayName());
    }

    @Test
    public void testGetFullname() throws Exception {
        assertEquals("The full name should be empty!", "", tenant.getFullName());
    }

    @Test
    public void testGetTenantId() throws Exception {
        assertEquals("The tenant id should be only 0s!", NULL_ID, tenant.getTenant());
    }
}
