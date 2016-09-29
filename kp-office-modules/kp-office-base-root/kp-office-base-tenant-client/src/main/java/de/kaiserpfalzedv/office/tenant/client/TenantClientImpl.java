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

package de.kaiserpfalzedv.office.tenant.client;

import java.util.Collection;
import java.util.Properties;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.office.common.cdi.Implementation;
import de.kaiserpfalzedv.office.common.client.config.ConfigReader;
import de.kaiserpfalzedv.office.common.client.config.impl.ConfigReaderBuilder;
import de.kaiserpfalzedv.office.common.client.messaging.MessageInfo;
import de.kaiserpfalzedv.office.common.client.messaging.MessageSender;
import de.kaiserpfalzedv.office.common.client.messaging.MessagingCore;
import de.kaiserpfalzedv.office.common.client.messaging.NoBrokerException;
import de.kaiserpfalzedv.office.common.client.messaging.NoResponseException;
import de.kaiserpfalzedv.office.common.client.messaging.ResponseOfWrongTypeException;
import de.kaiserpfalzedv.office.common.client.messaging.impl.MessageSenderImpl;
import de.kaiserpfalzedv.office.tenant.Tenant;
import de.kaiserpfalzedv.office.tenant.TenantDoesNotExistException;
import de.kaiserpfalzedv.office.tenant.TenantExistsException;
import de.kaiserpfalzedv.office.tenant.commands.TenantCommandBuilder;
import de.kaiserpfalzedv.office.tenant.commands.TenantCreateCommand;
import de.kaiserpfalzedv.office.tenant.commands.TenantDeleteCommand;
import de.kaiserpfalzedv.office.tenant.commands.TenantRetrieveAllCommand;
import de.kaiserpfalzedv.office.tenant.commands.TenantRetrieveCommand;
import de.kaiserpfalzedv.office.tenant.commands.TenantUpdateCommand;
import de.kaiserpfalzedv.office.tenant.replies.TenantContainingBaseReply;
import de.kaiserpfalzedv.office.tenant.replies.TenantCreateReply;
import de.kaiserpfalzedv.office.tenant.replies.TenantDeleteReply;
import de.kaiserpfalzedv.office.tenant.replies.TenantRetrieveAllReply;
import de.kaiserpfalzedv.office.tenant.replies.TenantRetrieveReply;
import de.kaiserpfalzedv.office.tenant.replies.TenantUpdateReply;

import static de.kaiserpfalzedv.office.common.commands.CrudCommands.CREATE;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2016-09-20
 */
@Implementation
@ApplicationScoped
public class TenantClientImpl implements TenantClient {
    private MessagingCore messaging;
    private ConfigReader config;
    private UUID clientId = UUID.randomUUID();

    private String destination;


    @Inject
    public TenantClientImpl(
            @NotNull final ConfigReader config,
            @NotNull final MessagingCore messaging
    ) {
        this.config = config;
        this.messaging = messaging;
    }


    @Override
    public Tenant create(Tenant data) throws TenantExistsException {
        TenantCreateCommand command = new TenantCommandBuilder<TenantCreateCommand>()
                .withSource(clientId)
                .withTenant(data)
                .build();

        MessageSender<TenantCreateCommand, TenantCreateReply> sender = new MessageSenderImpl<>(messaging);
        sender.withPayload(command);

        return getTenantFromResponse(sender, data.getId());
    }

    private Tenant getTenantFromResponse(MessageSender sender, final UUID tenantId) {
        MessageInfo response = null;

        try {
            response = sender
                    .withDestination(destination)
                    .withResponse()
                    .sendMessage();

            return ((TenantContainingBaseReply) response.waitForResponse()).getTenant();
        } catch (NoBrokerException | ResponseOfWrongTypeException | NoResponseException | InterruptedException e) {
            throw new TenantClientCommunicationException(
                    CREATE,
                    UUID.fromString(sender.getCorrelationId()),
                    "",
                    tenantId,
                    e
            );
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    @Override
    public Tenant retrieve(UUID id) throws TenantDoesNotExistException {
        TenantRetrieveCommand command = new TenantCommandBuilder<TenantRetrieveCommand>()
                .withSource(clientId)
                .withTenantId(id)
                .build();

        MessageSender<TenantRetrieveCommand, TenantRetrieveReply> sender = new MessageSenderImpl<>(messaging);
        sender.withPayload(command);

        return getTenantFromResponse(sender, id);
    }

    @Override
    public Collection<Tenant> retrieve() {
        TenantRetrieveAllCommand command = new TenantCommandBuilder<TenantRetrieveAllCommand>()
                .withSource(clientId)
                .build();

        MessageSender<TenantRetrieveAllCommand, TenantRetrieveAllReply> sender = new MessageSenderImpl<>(messaging);
        sender.withPayload(command);

        MessageInfo<TenantRetrieveAllReply> response = null;

        try {
            response = sender
                    .withDestination(destination)
                    .withResponse()
                    .sendMessage();

            return response.waitForResponse().getTenants();
        } catch (NoBrokerException | ResponseOfWrongTypeException | NoResponseException | InterruptedException e) {
            throw new TenantClientCommunicationException(
                    CREATE,
                    UUID.fromString(sender.getCorrelationId()),
                    "",
                    e
            );
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    @Override
    public Tenant update(Tenant data) throws TenantDoesNotExistException {
        TenantUpdateCommand command = new TenantCommandBuilder<TenantUpdateCommand>()
                .withSource(clientId)
                .withTenant(data)
                .build();

        MessageSender<TenantUpdateCommand, TenantUpdateReply> sender = new MessageSenderImpl<>(messaging);
        sender.withPayload(command);

        return getTenantFromResponse(sender, data.getId());
    }

    @Override
    public void delete(UUID id) {
        TenantDeleteCommand command = new TenantCommandBuilder<TenantDeleteCommand>()
                .withSource(clientId)
                .withTenantId(id)
                .build();

        MessageSender<TenantDeleteCommand, TenantDeleteReply> sender = new MessageSenderImpl<>(messaging);
        sender.withPayload(command);

        MessageInfo<TenantDeleteReply> response = null;

        try {
            response = sender
                    .withDestination(destination)
                    .withResponse()
                    .sendMessage();

            response.waitForResponse();
        } catch (NoBrokerException | ResponseOfWrongTypeException | NoResponseException | InterruptedException e) {
            throw new TenantClientCommunicationException(
                    CREATE,
                    UUID.fromString(sender.getCorrelationId()),
                    "",
                    id,
                    e
            );
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    @Override
    public void close() {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.office.tenant.client.TenantClient.close
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void init() {
        this.destination = config.getEntry("tenant.queue", "tenant");
    }

    @Override
    public void init(Properties properties) {
        config = new ConfigReaderBuilder().withProperties(properties).build();

        init();
    }


}
