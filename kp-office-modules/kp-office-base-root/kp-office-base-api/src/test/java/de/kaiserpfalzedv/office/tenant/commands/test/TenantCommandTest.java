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

import java.io.IOException;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.kaiserpfalzedv.office.tenant.Tenant;
import de.kaiserpfalzedv.office.tenant.commands.TenantBaseCommand;
import de.kaiserpfalzedv.office.tenant.commands.TenantCommandBuilder;
import de.kaiserpfalzedv.office.tenant.commands.TenantContainingBaseCommand;
import de.kaiserpfalzedv.office.tenant.commands.TenantCreateCommand;
import de.kaiserpfalzedv.office.tenant.commands.TenantDeleteCommand;
import de.kaiserpfalzedv.office.tenant.commands.TenantIdContainingBaseCommand;
import de.kaiserpfalzedv.office.tenant.commands.TenantRetrieveAllCommand;
import de.kaiserpfalzedv.office.tenant.commands.TenantRetrieveCommand;
import de.kaiserpfalzedv.office.tenant.commands.TenantUpdateCommand;
import de.kaiserpfalzedv.office.tenant.impl.TenantBuilder;
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
public class TenantCommandTest {
    private static final Logger LOG = LoggerFactory.getLogger(TenantCommandTest.class);

    private static final UUID SOURCE_ID = UUID.randomUUID();

    private static final UUID TENANT_ID = UUID.randomUUID();
    private static final UUID TENANT_TENANT = UUID.randomUUID();
    private static final String KEY = "KEY-004";
    private static final String DISPLAY_NAME = "Display Name";
    private static final String FULL_NAME = "Full Name";
    private static final Tenant TENANT = new TenantBuilder()
            .withTenantId(TENANT_TENANT)
            .withId(TENANT_ID)
            .withKey(KEY)
            .withDisplayName(DISPLAY_NAME)
            .withFullName(FULL_NAME)
            .build();


    private static final ObjectMapper mapper = new ObjectMapper();

    @BeforeClass
    public static void checkSerializing() {
        checkState(mapper.canSerialize(TenantCreateCommand.class));
    }

    @Test
    public void checkTenantCreateCommand() throws IOException {
        TenantCreateCommand command = createTenantCreateCommand(TENANT);

        String json = marshalAndUnmarshalCommand(command);

        TenantCreateCommand result = mapper.readValue(json, TenantCreateCommand.class);

        checkCommand(command, result);
    }

    private TenantCreateCommand createTenantCreateCommand(final Tenant tenant) {
        TenantCreateCommand command = new TenantCommandBuilder<TenantCreateCommand>()
                .withSource(SOURCE_ID)
                .withTenant(tenant)
                .create()
                .build();
        LOG.trace("Command: {}", command);
        return command;
    }

    private String marshalAndUnmarshalCommand(TenantBaseCommand command) throws JsonProcessingException {
        String json = mapper.writeValueAsString(command);
        LOG.trace("JSon: {}", json);
        return json;
    }

    private void checkCommand(final TenantContainingBaseCommand command, final TenantContainingBaseCommand result) {
        //noinspection RedundantCast
        checkCommand((TenantBaseCommand) command, (TenantBaseCommand) result);

        checkTenant(result.getTenant(), command.getTenant());
    }

    private void checkCommand(final TenantBaseCommand command, final TenantBaseCommand result) {
        LOG.trace("Checking result: {}", result);

        assertTrue(command.equals(result));
        assertEquals(result.getSource(), command.getSource());
        assertEquals(result.getCommand(), command.getCommand());
    }

    private void checkTenant(final Tenant actual, final Tenant expected) {
        assertTrue(actual.equals(expected));
        assertEquals(actual.getTenant(), expected.getTenant());
        assertEquals(actual.getDisplayName(), expected.getDisplayName());
        assertEquals(actual.getFullName(), expected.getFullName());
    }

    @Test
    public void checkTenantRetrieveCommand() throws IOException {
        TenantRetrieveCommand command = createTenantRetrieveCommand(TENANT_ID);

        String json = marshalAndUnmarshalCommand(command);

        TenantRetrieveCommand result = mapper.readValue(json, TenantRetrieveCommand.class);

        checkCommand(command, result);
    }

    private TenantRetrieveCommand createTenantRetrieveCommand(final UUID tenant) {
        TenantRetrieveCommand command = new TenantCommandBuilder<TenantRetrieveCommand>()
                .withSource(SOURCE_ID)
                .withTenantId(tenant)
                .retrieve()
                .build();

        LOG.trace("Command: {}", command);
        return command;
    }

    private void checkCommand(final TenantIdContainingBaseCommand command, final TenantIdContainingBaseCommand result) {
        //noinspection RedundantCast
        checkCommand((TenantBaseCommand) command, (TenantBaseCommand) result);

        assertEquals(result.getTenant(), command.getTenant());
    }

    @Test
    public void checkTenantRetrieveAllCommand() throws IOException {
        TenantRetrieveAllCommand command = createTenantRetrieveAllCommand();

        String json = marshalAndUnmarshalCommand(command);

        TenantRetrieveCommand result = mapper.readValue(json, TenantRetrieveCommand.class);

        checkCommand(command, result);
    }

    private TenantRetrieveAllCommand createTenantRetrieveAllCommand() {
        TenantRetrieveAllCommand command = new TenantCommandBuilder<TenantRetrieveAllCommand>()
                .withSource(SOURCE_ID)
                .retrieveAll()
                .build();

        LOG.trace("Command: {}", command);
        return command;
    }

    @Test
    public void checkTenantUpdateCommand() throws IOException {
        TenantUpdateCommand command = createTenantUpdateCommand(TENANT);

        String json = marshalAndUnmarshalCommand(command);

        TenantCreateCommand result = mapper.readValue(json, TenantCreateCommand.class);

        checkCommand(command, result);
    }

    private TenantUpdateCommand createTenantUpdateCommand(final Tenant tenant) {
        TenantUpdateCommand command = new TenantCommandBuilder<TenantUpdateCommand>()
                .withSource(SOURCE_ID)
                .withTenant(tenant)
                .update()
                .build();
        LOG.trace("Command: {}", command);
        return command;
    }

    @Test
    public void checkTenantDeleteCommand() throws IOException {
        TenantDeleteCommand command = createTenantDeleteCommand(TENANT_ID);

        String json = marshalAndUnmarshalCommand(command);

        TenantDeleteCommand result = mapper.readValue(json, TenantDeleteCommand.class);

        checkCommand(command, result);
    }

    private TenantDeleteCommand createTenantDeleteCommand(final UUID tenant) {
        TenantDeleteCommand command = new TenantCommandBuilder<TenantDeleteCommand>()
                .withSource(SOURCE_ID)
                .withTenantId(tenant)
                .delete()
                .build();

        LOG.trace("Command: {}", command);
        return command;
    }
}
