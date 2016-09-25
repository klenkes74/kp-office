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

package de.kaiserpfalzedv.office.tenant.client.test;

import java.util.UUID;

import de.kaiserpfalzedv.office.tenant.Tenant;
import de.kaiserpfalzedv.office.tenant.impl.NullTenant;
import org.junit.Before;
import org.junit.Test;

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
        Assert.assertEquals("The id should be only 0s!", NULL_ID, tenant.getId());
    }

    @Test
    public void testGetDisplayname() throws Exception {
        Assert.assertEquals("The display name should be empty!", "", tenant.getDisplayName());
    }

    @Test
    public void testGetFullname() throws Exception {
        Assert.assertEquals("The full name should be empty!", "", tenant.getFullName());
    }

    @Test
    public void testGetTenantId() throws Exception {
        Assert.assertEquals("The tenant id should be only 0s!", NULL_ID, tenant.getTenantId());
    }
}
