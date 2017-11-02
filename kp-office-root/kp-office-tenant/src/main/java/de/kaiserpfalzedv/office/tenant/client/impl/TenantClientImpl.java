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

package de.kaiserpfalzedv.office.tenant.client.impl;

import de.kaiserpfalzedv.commons.api.cdi.Implementation;
import de.kaiserpfalzedv.commons.api.config.ConfigReader;
import de.kaiserpfalzedv.commons.api.messaging.*;
import de.kaiserpfalzedv.commons.client.messaging.MessageSenderImpl;
import de.kaiserpfalzedv.commons.impl.config.ConfigReaderBuilder;
import de.kaiserpfalzedv.office.tenant.api.Tenant;
import de.kaiserpfalzedv.office.tenant.api.TenantDoesNotExistException;
import de.kaiserpfalzedv.office.tenant.api.TenantExistsException;
import de.kaiserpfalzedv.office.tenant.api.commands.*;
import de.kaiserpfalzedv.office.tenant.api.replies.*;
import de.kaiserpfalzedv.office.tenant.client.TenantClient;
import de.kaiserpfalzedv.office.tenant.client.TenantClientCommunicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Properties;
import java.util.UUID;

import static de.kaiserpfalzedv.commons.api.commands.CrudCommands.CREATE;
import static de.kaiserpfalzedv.commons.api.commands.CrudCommands.RETRIEVE;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2016-09-20
 */
@Implementation
@ApplicationScoped
public class TenantClientImpl implements TenantClient, Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(TenantClientImpl.class);


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
                try {
                    response.close();
                } catch (IOException e) {
                    LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);
                }
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
                    RETRIEVE,
                    UUID.fromString(sender.getCorrelationId()),
                    "",
                    e
            );
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);
                }
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
                try {
                    response.close();
                } catch (IOException e) {
                    LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);
                }
            }
        }
    }

    @Override
    public Tenant retrieve(String businessKey) throws TenantDoesNotExistException {

        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.office.tenant.client.impl.TenantClientImpl.retrieve
        return null;  //To change body of implemented methods use File | Settings | File Templates.
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
