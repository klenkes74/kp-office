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

import java.util.HashSet;
import java.util.UUID;

import de.kaiserpfalzedv.office.common.BuilderException;
import de.kaiserpfalzedv.office.tenant.Tenant;
import de.kaiserpfalzedv.office.tenant.commands.TenantCommandBuilder;
import de.kaiserpfalzedv.office.tenant.commands.TenantCreateCommand;
import de.kaiserpfalzedv.office.tenant.commands.TenantDeleteCommand;
import de.kaiserpfalzedv.office.tenant.commands.TenantRetrieveAllCommand;
import de.kaiserpfalzedv.office.tenant.commands.TenantRetrieveCommand;
import de.kaiserpfalzedv.office.tenant.commands.TenantUpdateCommand;
import de.kaiserpfalzedv.office.tenant.replies.TenantCreateReply;
import de.kaiserpfalzedv.office.tenant.replies.TenantDeleteReply;
import de.kaiserpfalzedv.office.tenant.replies.TenantReplyBuilder;
import de.kaiserpfalzedv.office.tenant.replies.TenantRetrieveAllReply;
import de.kaiserpfalzedv.office.tenant.replies.TenantRetrieveReply;
import de.kaiserpfalzedv.office.tenant.replies.TenantUpdateReply;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-25
 */
public class TenantReplyBuilderTest {
    private static final Logger LOG = LoggerFactory.getLogger(TenantReplyBuilderTest.class);

    private static final UUID SOURCE_ID = UUID.randomUUID();

    private TenantCommandBuilder commandBuilder;
    private TenantReplyBuilder service;


    @Test
    public void checkCreateNoReply() {
        try {
            service.withSource(SOURCE_ID).build();

            fail("A BuilderException should be thrown!");
        } catch (BuilderException e) {
            // everything is fine.
        }
        // No assert since we check for the exception.
    }

    @Test
    public void checkCreateReply() {
        Tenant data = new TestTenant();
        TenantCreateCommand command = createCreateTenantCommand(data);

        TenantCreateReply reply = (TenantCreateReply) service
                .withSource(SOURCE_ID)
                .withTenant(data)
                .withCommand(command)
                .build();
        LOG.debug("Reply: {}", reply);

        assertEquals(reply.getCommandId(), command.getCommandId());
        assertEquals(reply.getTenant(), data);
    }

    private TenantCreateCommand createCreateTenantCommand(Tenant data) {
        return (TenantCreateCommand) commandBuilder
                .withSource(SOURCE_ID)
                .withTenant(data)
                .create()
                .build();
    }

    @Test
    public void checkCreateReplyWithReplyId() {
        UUID id = UUID.randomUUID();
        Tenant data = new TestTenant();
        TenantCreateCommand command = createCreateTenantCommand(data);

        TenantCreateReply reply = (TenantCreateReply) service
                .withSource(SOURCE_ID)
                .withReplyId(id)
                .withTenant(data)
                .withCommand(command)
                .build();
        LOG.debug("Reply: {}", reply);

        assertEquals(reply.getReplyId(), id);
    }

    @Test
    public void checkCreateReplyWithCommandId() {
        UUID id = UUID.randomUUID();
        Tenant data = new TestTenant();
        TenantCreateCommand command = createCreateTenantCommand(data);

        TenantCreateReply reply = (TenantCreateReply) service
                .withSource(SOURCE_ID)
                .withTenant(data)
                .withCommand(command)
                .withCommandId(id)
                .build();
        LOG.debug("Reply: {}", reply);

        assertEquals(reply.getCommandId(), id);
    }

    @Test
    public void checkCreateReplyWithoutTenant() {
        UUID id = UUID.randomUUID();
        Tenant data = new TestTenant();
        TenantCreateCommand command = createCreateTenantCommand(data);

        try {
            service
                    .withSource(SOURCE_ID)
                    .withReplyId(id)
                    .withCommand(command)
                    .build();
        } catch (BuilderException e) {
            // everything is fine.
        }
        // No assert since we check for the exception.
    }

    @Test
    public void checkCreateReplyWithoutSource() {
        Tenant data = new TestTenant();
        TenantCreateCommand command = createCreateTenantCommand(data);

        try {
            service
                    .withTenant(data)
                    .withCommand(command)
                    .build();
        } catch (BuilderException e) {
            // everything is fine.
        }
        // No assert since we check for the exception.
    }


    @Test
    public void checkRetrieveReply() {
        Tenant data = new TestTenant();
        TenantRetrieveCommand command = createRetrieveTenantCommand(data);

        TenantRetrieveReply reply = (TenantRetrieveReply) service
                .withSource(SOURCE_ID)
                .withTenant(data)
                .withCommand(command)
                .build();
        LOG.debug("Reply: {}", reply);

        assertEquals(reply.getCommandId(), command.getCommandId());
        assertEquals(reply.getTenant(), data);
    }

    private TenantRetrieveCommand createRetrieveTenantCommand(Tenant data) {
        return (TenantRetrieveCommand) commandBuilder
                .withSource(SOURCE_ID)
                .withTenantId(data.getId())
                .retrieve()
                .build();
    }

    @Test
    public void checkRetrieveReplyWithoutTenant() {
        UUID id = UUID.randomUUID();
        Tenant data = new TestTenant();
        TenantRetrieveCommand command = createRetrieveTenantCommand(data);

        try {
            service
                    .withSource(SOURCE_ID)
                    .withReplyId(id)
                    .withCommand(command)
                    .build();
        } catch (BuilderException e) {
            // everything is fine.
        }
        // No assert since we check for the exception.
    }

    @Test
    public void checkRetrieveReplyWithoutSource() {
        Tenant data = new TestTenant();
        TenantRetrieveCommand command = createRetrieveTenantCommand(data);

        try {
            service
                    .withTenant(data)
                    .withCommand(command)
                    .build();
        } catch (BuilderException e) {
            // everything is fine.
        }
        // No assert since we check for the exception.
    }


    @Test
    public void checkRetrieveAllReply() {
        TenantRetrieveAllCommand command = createRetrieveAllCommand();
        HashSet<Tenant> data = new HashSet<>();

        TenantRetrieveAllReply reply = (TenantRetrieveAllReply) service
                .withSource(SOURCE_ID)
                .withTenants(data)
                .withCommand(command)
                .build();
        LOG.debug("Reply: {}", reply);

        assertEquals(data, reply.getTenants());
    }

    private TenantRetrieveAllCommand createRetrieveAllCommand() {
        return (TenantRetrieveAllCommand) commandBuilder
                .withSource(SOURCE_ID)
                .retrieveAll()
                .build();
    }

    @Test
    public void checkRetrieveAllReplyWithoutTenants() {
        TenantRetrieveAllCommand command = createRetrieveAllCommand();

        try {
            service
                    .withSource(SOURCE_ID)
                    .withCommand(command)
                    .build();
        } catch (BuilderException e) {
            // everything is fine.
        }
        // No assert since we check for the exception.
    }

    @Test
    public void checkRetrieveAllReplyWithoutSource() {
        TenantRetrieveAllCommand command = createRetrieveAllCommand();

        try {
            service
                    .withTenants(new HashSet<>())
                    .withCommand(command)
                    .build();
        } catch (BuilderException e) {
            // everything is fine.
        }
        // No assert since we check for the exception.
    }


    @Test
    public void checkUpdateReply() {
        Tenant data = new TestTenant();
        TenantUpdateCommand command = createUpdateTenantCommand(data);

        TenantUpdateReply reply = (TenantUpdateReply) service
                .withSource(SOURCE_ID)
                .withTenant(data)
                .withCommand(command)
                .build();
        LOG.debug("Reply: {}", reply);

        assertEquals(reply.getCommandId(), command.getCommandId());
        assertEquals(reply.getTenant(), data);
    }

    private TenantUpdateCommand createUpdateTenantCommand(Tenant data) {
        return (TenantUpdateCommand) commandBuilder
                .withSource(SOURCE_ID)
                .withTenant(data)
                .update()
                .build();
    }

    @Test
    public void checkUpdateReplyWithoutTenant() {
        Tenant data = new TestTenant();
        TenantUpdateCommand command = createUpdateTenantCommand(data);

        try {
            service
                    .withSource(SOURCE_ID)
                    .withCommand(command)
                    .build();
        } catch (BuilderException e) {
            // everything is fine.
        }
        // No assert since we check for the exception.
    }


    @Test
    public void checkDeleteReply() {
        UUID id = UUID.randomUUID();

        TenantDeleteCommand command = (TenantDeleteCommand) commandBuilder
                .withSource(SOURCE_ID)
                .withTenantId(id)
                .delete()
                .build();

        TenantDeleteReply reply = (TenantDeleteReply) service
                .withSource(SOURCE_ID)
                .withCommand(command)
                .build();
        LOG.debug("Reply: {}", reply);

        assertEquals(reply.getCommandId(), command.getCommandId());
    }


    /**
     * Only way to get the test coverage for the default constructors. Objects are not usable that way ...
     */
    @Test
    public void checkDefaultConstructors() {
        new TestTenantCreateReply();
        new TestTenantRetrieveReply();
        new TestTenantRetrieveAllReply();
        new TestTenantUpdateReply();
        new TestTenantDeleteReply();
    }

    @Before
    public void setupService() {
        commandBuilder = new TenantCommandBuilder();
        service = new TenantReplyBuilder();
    }

    @SuppressWarnings("deprecation")
    private class TestTenantCreateReply extends TenantCreateReply {}

    @SuppressWarnings("deprecation")
    private class TestTenantRetrieveReply extends TenantRetrieveReply {}

    @SuppressWarnings("deprecation")
    private class TestTenantRetrieveAllReply extends TenantRetrieveAllReply {}

    @SuppressWarnings("deprecation")
    private class TestTenantUpdateReply extends TenantUpdateReply {}

    @SuppressWarnings("deprecation")
    private class TestTenantDeleteReply extends TenantDeleteReply {}
}
