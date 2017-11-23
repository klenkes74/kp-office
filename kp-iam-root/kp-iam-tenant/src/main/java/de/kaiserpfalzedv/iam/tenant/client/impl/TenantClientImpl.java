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

package de.kaiserpfalzedv.iam.tenant.client.impl;

import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.commons.api.action.commands.CrudCommandBuilder;
import de.kaiserpfalzedv.commons.api.action.commands.CrudCommandBuilderCreator;
import de.kaiserpfalzedv.commons.api.cdi.Implementation;
import de.kaiserpfalzedv.commons.api.config.ConfigReader;
import de.kaiserpfalzedv.commons.api.data.paging.Pageable;
import de.kaiserpfalzedv.commons.api.data.paging.PageableBuilder;
import de.kaiserpfalzedv.commons.api.data.paging.PagedListable;
import de.kaiserpfalzedv.commons.api.data.query.Predicate;
import de.kaiserpfalzedv.commons.api.messaging.MessageInfo;
import de.kaiserpfalzedv.commons.api.messaging.MessageSender;
import de.kaiserpfalzedv.commons.api.messaging.MessagingCore;
import de.kaiserpfalzedv.commons.api.messaging.NoBrokerException;
import de.kaiserpfalzedv.commons.api.messaging.NoResponseException;
import de.kaiserpfalzedv.commons.api.messaging.ResponseOfWrongTypeException;
import de.kaiserpfalzedv.commons.client.messaging.MessageSenderImpl;
import de.kaiserpfalzedv.commons.impl.config.ConfigReaderBuilder;
import de.kaiserpfalzedv.iam.tenant.api.Tenant;
import de.kaiserpfalzedv.iam.tenant.api.TenantDoesNotExistException;
import de.kaiserpfalzedv.iam.tenant.api.TenantExistsException;
import de.kaiserpfalzedv.iam.tenant.api.TenantPredicate;
import de.kaiserpfalzedv.iam.tenant.api.commands.TenantCreateCommand;
import de.kaiserpfalzedv.iam.tenant.api.commands.TenantDeleteCommand;
import de.kaiserpfalzedv.iam.tenant.api.commands.TenantRetrieveCommand;
import de.kaiserpfalzedv.iam.tenant.api.commands.TenantUpdateCommand;
import de.kaiserpfalzedv.iam.tenant.api.replies.TenantContainingBaseReply;
import de.kaiserpfalzedv.iam.tenant.api.replies.TenantCreateReply;
import de.kaiserpfalzedv.iam.tenant.api.replies.TenantDeleteReply;
import de.kaiserpfalzedv.iam.tenant.api.replies.TenantRetrieveReply;
import de.kaiserpfalzedv.iam.tenant.api.replies.TenantUpdateReply;
import de.kaiserpfalzedv.iam.tenant.client.TenantClient;
import de.kaiserpfalzedv.iam.tenant.client.TenantClientCommunicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static de.kaiserpfalzedv.commons.api.action.CrudCommandType.CREATE;
import static de.kaiserpfalzedv.commons.api.action.CrudCommandType.RETRIEVE;

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

    private CrudCommandBuilderCreator<Tenant> commandCreator;
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
        CrudCommandBuilder<TenantCreateCommand, Tenant> commandBuilder
                = new CrudCommandBuilder<TenantCreateCommand, Tenant>(TenantCreateCommand.class, commandCreator)
                .withSource(clientId)
                .withData(data)
                .create();

        TenantCreateCommand command = commandBuilder.build();

        MessageSender<TenantCreateCommand, TenantCreateReply> sender = new MessageSenderImpl<>(messaging);
        sender.withPayload(command);

        return getTenantFromResponse(sender, data.getId());
    }

    @Override
    public Tenant retrieve(UUID id) throws TenantDoesNotExistException {
        return retrieve(
                TenantPredicate.id().isEqualTo(id),
                new PageableBuilder()
                        .withPage(new PageableBuilder().withPage(1).withSize(5).build())
                        .build()
        ).getEntries().get(0);
    }

    @Override
    public PagedListable<Tenant> retrieve(
            @NotNull final Predicate<Tenant> predicate,
            @NotNull final Pageable page
    ) {
        CrudCommandBuilder<TenantRetrieveCommand, Tenant> commandBuilder
                = new CrudCommandBuilder<TenantRetrieveCommand, Tenant>(TenantRetrieveCommand.class, commandCreator)
                .withSource(clientId)
                .withPredicate(predicate)
                .withPage(page)
                .retrieve();

        TenantRetrieveCommand command = commandBuilder.build();

        MessageInfo response = null;
        UUID correlationId = UUID.randomUUID();
        try (MessageSender<TenantRetrieveCommand, TenantRetrieveReply> sender = new MessageSenderImpl<>(messaging)) {
            sender.withPayload(command);

            response = sender
                    .withDestination(destination)
                    .withCorrelationId(correlationId.toString())
                    .withResponse()
                    .sendMessage();

            return ((TenantRetrieveReply) response.waitForResponse()).getTenants();
        } catch (NoBrokerException | ClassCastException | InterruptedException | NoResponseException
                | ResponseOfWrongTypeException e) {
            throw new TenantClientCommunicationException(
                    RETRIEVE,
                    correlationId,
                    e.getMessage(),
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
        CrudCommandBuilder<TenantUpdateCommand, Tenant> commandBuilder
                = new CrudCommandBuilder<TenantUpdateCommand, Tenant>(TenantUpdateCommand.class, commandCreator)
                .withSource(clientId)
                .withData(data)
                .update();

        TenantUpdateCommand command = commandBuilder.build();

        MessageSender<TenantUpdateCommand, TenantUpdateReply> sender = new MessageSenderImpl<>(messaging);
        sender.withPayload(command);

        return getTenantFromResponse(sender, data.getId());
    }

    @Override
    public void delete(UUID id) {
        CrudCommandBuilder<TenantDeleteCommand, Tenant> commandBuilder
                = new CrudCommandBuilder<TenantDeleteCommand, Tenant>(TenantDeleteCommand.class, commandCreator)
                .withSource(clientId)
                .withId(id)
                .delete();

        TenantDeleteCommand command = commandBuilder.build();

        MessageInfo<TenantDeleteReply> response = null;
        UUID correlationId = UUID.randomUUID();
        try (MessageSender<TenantDeleteCommand, TenantDeleteReply> sender = new MessageSenderImpl<>(messaging)) {
            sender.withPayload(command);

            response = sender
                    .withDestination(destination)
                    .withCorrelationId(correlationId.toString())
                    .withResponse()
                    .sendMessage();

            response.waitForResponse();
        } catch (NoBrokerException | ResponseOfWrongTypeException | NoResponseException | InterruptedException e) {
            throw new TenantClientCommunicationException(
                    CREATE,
                    correlationId,
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
    public void close() {
        messaging = null;
        config = null;
        clientId = null;
        destination = null;
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
