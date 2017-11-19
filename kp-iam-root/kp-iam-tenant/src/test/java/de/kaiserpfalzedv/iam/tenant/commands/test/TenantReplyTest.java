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

package de.kaiserpfalzedv.iam.tenant.commands.test;

import java.io.IOException;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.kaiserpfalzedv.commons.api.commands.CommandBuilder;
import de.kaiserpfalzedv.iam.tenant.api.Tenant;
import de.kaiserpfalzedv.iam.tenant.api.TenantBuilder;
import de.kaiserpfalzedv.iam.tenant.api.commands.TenantCommandCreator;
import de.kaiserpfalzedv.iam.tenant.api.commands.TenantCreateCommand;
import de.kaiserpfalzedv.iam.tenant.api.commands.TenantDeleteCommand;
import de.kaiserpfalzedv.iam.tenant.api.commands.TenantUpdateCommand;
import de.kaiserpfalzedv.iam.tenant.api.replies.TenantBaseReply;
import de.kaiserpfalzedv.iam.tenant.api.replies.TenantContainingBaseReply;
import de.kaiserpfalzedv.iam.tenant.api.replies.TenantCreateReply;
import de.kaiserpfalzedv.iam.tenant.api.replies.TenantDeleteReply;
import de.kaiserpfalzedv.iam.tenant.api.replies.TenantReplyBuilder;
import de.kaiserpfalzedv.iam.tenant.api.replies.TenantRetrieveReply;
import de.kaiserpfalzedv.iam.tenant.api.replies.TenantUpdateReply;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkState;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-27
 */
public class TenantReplyTest {
    private static final Logger LOG = LoggerFactory.getLogger(TenantReplyTest.class);

    private static final UUID REQUESTOR_ID = UUID.randomUUID();
    private static final UUID SOURCE_ID = UUID.randomUUID();

    private static final UUID TENANT_ID = UUID.randomUUID();
    private static final UUID TENANT_TENANT = UUID.randomUUID();
    private static final String KEY = "KEY-003";
    private static final String DISPLAY_NAME = "Display Name";
    private static final String FULL_NAME = "Full Name";
    private static final Tenant TENANT = new TenantBuilder()
            .withTenantId(TENANT_TENANT)
            .withId(TENANT_ID)
            .withKey(KEY)
            .withDisplayName(DISPLAY_NAME)
            .withFullName(FULL_NAME)
            .build();

    private static final TenantCommandCreator creator = new TenantCommandCreator();
    private static final ObjectMapper mapper = new ObjectMapper();

    @BeforeClass
    public static void checkSerializing() {
        checkState(mapper.canSerialize(TenantCreateCommand.class));
    }


    @Test
    public void checkTenantCreateReply() throws IOException {
        TenantCreateCommand command = createTenantCreateCommand(TENANT);
        TenantCreateReply reply = new TenantReplyBuilder<TenantCreateReply>()
                .withSource(SOURCE_ID)
                .withCommand(command)
                .withTenant(TENANT)
                .build();

        String json = marshalAndUnmarshalCommand(reply);

        TenantCreateReply result = mapper.readValue(json, TenantCreateReply.class);

        checkReply(result, reply);
    }

    private TenantCreateCommand createTenantCreateCommand(final Tenant tenant) {
        CommandBuilder<TenantCreateCommand, Tenant> commandBuilder
                = new CommandBuilder<TenantCreateCommand, Tenant>(TenantCreateCommand.class, creator)
                .withSource(REQUESTOR_ID)
                .withData(tenant)
                .create();
        TenantCreateCommand command = commandBuilder.build();

        LOG.trace("Command: {}", command);
        return command;
    }

    private String marshalAndUnmarshalCommand(final TenantBaseReply reply) throws JsonProcessingException {
        String json = mapper.writeValueAsString(reply);
        LOG.trace("JSon: {}", json);
        return json;
    }

    private void checkReply(final TenantContainingBaseReply actual, final TenantContainingBaseReply expected) {
        //noinspection RedundantCast
        checkReply((TenantBaseReply) actual, (TenantBaseReply) expected);

        checkTenant(actual.getTenant(), expected.getTenant());
    }

    private void checkReply(final TenantBaseReply actual, final TenantBaseReply expected) {
        LOG.trace("Checking result: {}", actual);

        assertTrue(actual.equals(expected));

        assertEquals(actual.getSource(), expected.getSource());
        assertEquals(actual.getActionType(), expected.getActionType());
        assertEquals(actual.getCommand(), expected.getCommand());
        assertEquals(actual.getReply(), expected.getReply());
    }


    private void checkTenant(final Tenant actual, final Tenant expected) {
        assertTrue(actual.equals(expected));
        assertEquals(actual.getTenant(), expected.getTenant());
        assertEquals(actual.getDisplayName(), expected.getDisplayName());
        assertEquals(actual.getFullName(), expected.getFullName());
    }


    private void checkReply(final TenantRetrieveReply actual, final TenantRetrieveReply expected) {
        //noinspection RedundantCast
        checkReply((TenantBaseReply) actual, (TenantBaseReply) expected);

        assertEquals(actual.getTenants().getPage().getTotalCount(), expected.getTenants().getPage().getTotalCount());
        checkTenant(actual.getTenants().getEntries().iterator().next(), expected.getTenants()
                                                                                .getEntries()
                                                                                .iterator()
                                                                                .next());
    }


    @Test
    public void checkTenantUpdateReply() throws IOException {
        TenantUpdateCommand command = createTenantUpdateCommand(TENANT);
        TenantUpdateReply reply = new TenantReplyBuilder<TenantUpdateReply>()
                .withSource(SOURCE_ID)
                .withCommand(command)
                .withTenant(TENANT)
                .build();

        String json = marshalAndUnmarshalCommand(reply);

        TenantUpdateReply result = mapper.readValue(json, TenantUpdateReply.class);

        checkReply(result, reply);
    }

    private TenantUpdateCommand createTenantUpdateCommand(final Tenant tenant) {
        CommandBuilder<TenantUpdateCommand, Tenant> commandBuilder
                = new CommandBuilder<TenantUpdateCommand, Tenant>(TenantUpdateCommand.class, creator)
                .withSource(SOURCE_ID)
                .withData(tenant)
                .update();
        TenantUpdateCommand command = commandBuilder.build();

        LOG.trace("Command: {}", command);
        return command;
    }


    @Test
    public void checkTenantDeleteReply() throws IOException {
        TenantDeleteCommand command = createTenantDeleteCommand(TENANT_ID);
        TenantDeleteReply reply = new TenantReplyBuilder<TenantDeleteReply>()
                .withSource(SOURCE_ID)
                .withCommand(command)
                .build();

        String json = marshalAndUnmarshalCommand(reply);

        TenantDeleteReply result = mapper.readValue(json, TenantDeleteReply.class);

        checkReply(result, reply);
    }

    private TenantDeleteCommand createTenantDeleteCommand(final UUID tenantId) {
        CommandBuilder<TenantDeleteCommand, Tenant> commandBuilder
                = new CommandBuilder<TenantDeleteCommand, Tenant>(TenantDeleteCommand.class, creator)
                .withSource(SOURCE_ID)
                .withId(tenantId)
                .update();
        TenantDeleteCommand command = commandBuilder.build();

        LOG.trace("Command: {}", command);
        return command;
    }
}
