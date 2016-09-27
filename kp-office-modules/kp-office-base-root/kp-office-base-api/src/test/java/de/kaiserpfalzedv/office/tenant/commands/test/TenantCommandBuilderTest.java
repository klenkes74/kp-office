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

package de.kaiserpfalzedv.office.tenant.commands.test;

import java.util.UUID;

import de.kaiserpfalzedv.office.common.BuilderException;
import de.kaiserpfalzedv.office.tenant.Tenant;
import de.kaiserpfalzedv.office.tenant.commands.TenantCommandBuilder;
import de.kaiserpfalzedv.office.tenant.commands.TenantCreateCommand;
import de.kaiserpfalzedv.office.tenant.commands.TenantDeleteCommand;
import de.kaiserpfalzedv.office.tenant.commands.TenantRetrieveAllCommand;
import de.kaiserpfalzedv.office.tenant.commands.TenantRetrieveCommand;
import de.kaiserpfalzedv.office.tenant.commands.TenantUpdateCommand;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-25
 */
public class TenantCommandBuilderTest {
    private static final Logger LOG = LoggerFactory.getLogger(TenantCommandBuilderTest.class);

    private static final UUID SOURCE_ID = UUID.randomUUID();

    private TenantCommandBuilder service;

    @Test
    public void checkCreateNoCommand() {
        try {
            service.withSource(SOURCE_ID).build();

            fail("A BuilderException should be thrown!");
        } catch (BuilderException e) {
            // everything is fine.
        }
        // No assert since we check for the exception.
    }

    @Test
    public void checkCreateCommand() {
        Tenant data = new TestTenant();
        TenantCreateCommand result = (TenantCreateCommand) service
                .withSource(SOURCE_ID)
                .withTenant(data)
                .create()
                .build();
        LOG.debug("Result: {}", result);

        assertEquals(data.getId(), result.getTenant().getId());
    }

    @Test
    public void checkCreateCommandWithId() {
        UUID id = UUID.randomUUID();
        Tenant data = new TestTenant();

        TenantCreateCommand result = (TenantCreateCommand) service
                .withSource(SOURCE_ID)
                .withId(id)
                .withTenant(data)
                .create()
                .build();
        LOG.debug("Result: {}", result);

        assertEquals(id, result.getCommand());
        assertEquals(SOURCE_ID, result.getSource());
    }

    @Test
    public void checkCreateCommandWithoutTenant() {
        try {
            service.withSource(SOURCE_ID).create().build();

            fail("A BuilderException should be thrown!");
        } catch (BuilderException e) {
            // everything is fine.
        }
        // No assert since we check for the exception.
    }

    @Test
    public void checkCreateCommandWithoutSource() {
        Tenant data = new TestTenant();

        try {
            service.withTenant(data).create().build();

            fail("A BuilderException should be thrown!");
        } catch (BuilderException e) {
            // everything is fine.
        }
        // No assert since we check for the exception.
    }

    @Test
    public void checkRetrieveCommand() {
        UUID tenantId = UUID.randomUUID();
        TenantRetrieveCommand result = (TenantRetrieveCommand) service
                .withSource(SOURCE_ID)
                .withTenantId(tenantId)
                .retrieve()
                .build();
        LOG.debug("Result: {}", result);

        assertEquals(tenantId, result.getTenant());
    }

    @Test
    public void checkRetrieveCommandWithoutTenantId() {
        try {
            service.withSource(SOURCE_ID).retrieve().build();

            fail("A BuilderException should be thrown!");
        } catch (BuilderException e) {
            // everything is fine.
        }
        // No assert since we check for the exception.
    }

    @Test
    public void checkRetrieveCommandWithoutSource() {
        UUID tenantId = UUID.randomUUID();

        try {
            service.withTenantId(tenantId).retrieve().build();

            fail("A BuilderException should be thrown!");
        } catch (BuilderException e) {
            // everything is fine.
        }
        // No assert since we check for the exception.
    }

    @Test
    public void checkRetrieveAllCommand() {
        UUID tenantId = UUID.randomUUID();
        TenantRetrieveAllCommand result = (TenantRetrieveAllCommand) service
                .withSource(SOURCE_ID)
                .retrieveAll()
                .build();
        LOG.debug("Result: {}", result);

        assertTrue(result != null);
    }

    @Test
    public void checkRetrieveAllCommandWithoutSource() {
        try {
            service.retrieveAll().build();

            fail("A BuilderException should be thrown!");
        } catch (BuilderException e) {
            // everything is fine.
        }
        // No assert since we check for the exception.
    }

    @Test
    public void checkUpdateCommand() {
        Tenant data = new TestTenant();
        TenantUpdateCommand result = (TenantUpdateCommand) service
                .withSource(SOURCE_ID)
                .withTenant(data)
                .update()
                .build();
        LOG.debug("Result: {}", result);

        assertEquals(data.getId(), result.getTenant().getId());
    }

    @Test
    public void checkUpdateCommandWithoutTenant() {
        try {
            service.withSource(SOURCE_ID).update().build();

            fail("A BuilderException should be thrown!");
        } catch (BuilderException e) {
            // everything is fine.
        }
        // No assert since we check for the exception.
    }

    @Test
    public void checkUpdateCommandWithoutSource() {
        Tenant data = new TestTenant();

        try {
            service.withTenant(data).update().build();

            fail("A BuilderException should be thrown!");
        } catch (BuilderException e) {
            // everything is fine.
        }
        // No assert since we check for the exception.
    }

    @Test
    public void checkDeleteCommand() {
        UUID tenantId = UUID.randomUUID();
        TenantDeleteCommand result = (TenantDeleteCommand) service
                .withSource(SOURCE_ID)
                .withTenantId(tenantId)
                .delete()
                .build();
        LOG.debug("Result: {}", result);

        assertEquals(tenantId, result.getTenant());
    }

    @Test
    public void checkDeleteCommandWithoutTenantId() {
        try {
            service.withSource(SOURCE_ID).delete().build();

            fail("A BuilderException should be thrown!");
        } catch (BuilderException e) {
            // everything is fine.
        }
        // No assert since we check for the exception.
    }

    @Test
    public void checkDeleteCommandWithoutSource() {
        UUID tenantId = UUID.randomUUID();

        try {
            service.withTenantId(tenantId).delete().build();

            fail("A BuilderException should be thrown!");
        } catch (BuilderException e) {
            // everything is fine.
        }
        // No assert since we check for the exception.
    }
}
