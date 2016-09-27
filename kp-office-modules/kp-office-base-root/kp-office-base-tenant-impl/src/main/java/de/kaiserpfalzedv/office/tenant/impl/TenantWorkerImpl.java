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

package de.kaiserpfalzedv.office.tenant.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.UUID;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.jms.JMSException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.kaiserpfalzedv.office.common.MessageInfo;
import de.kaiserpfalzedv.office.common.commands.BaseCommand;
import de.kaiserpfalzedv.office.common.commands.CommandExecutionException;
import de.kaiserpfalzedv.office.tenant.ResponseSender;
import de.kaiserpfalzedv.office.tenant.Tenant;
import de.kaiserpfalzedv.office.tenant.TenantDoesNotExistException;
import de.kaiserpfalzedv.office.tenant.TenantExistsException;
import de.kaiserpfalzedv.office.tenant.TenantService;
import de.kaiserpfalzedv.office.tenant.TenantWorker;
import de.kaiserpfalzedv.office.tenant.commands.TenantBaseCommand;
import de.kaiserpfalzedv.office.tenant.commands.TenantCommandExecutionException;
import de.kaiserpfalzedv.office.tenant.commands.TenantCreateCommand;
import de.kaiserpfalzedv.office.tenant.commands.TenantDeleteCommand;
import de.kaiserpfalzedv.office.tenant.commands.TenantRetrieveAllCommand;
import de.kaiserpfalzedv.office.tenant.commands.TenantRetrieveCommand;
import de.kaiserpfalzedv.office.tenant.commands.TenantUpdateCommand;
import de.kaiserpfalzedv.office.tenant.replies.TenantBaseReply;
import de.kaiserpfalzedv.office.tenant.replies.TenantCreateReply;
import de.kaiserpfalzedv.office.tenant.replies.TenantDeleteReply;
import de.kaiserpfalzedv.office.tenant.replies.TenantReplyBuilder;
import de.kaiserpfalzedv.office.tenant.replies.TenantRetrieveAllReply;
import de.kaiserpfalzedv.office.tenant.replies.TenantRetrieveReply;
import de.kaiserpfalzedv.office.tenant.replies.TenantUpdateReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-27
 */

@Dependent
public class TenantWorkerImpl implements TenantWorker {
    private static final Logger LOG = LoggerFactory.getLogger(TenantWorker.class);

    private UUID tenantWorkerId = UUID.randomUUID();

    private TenantService service;
    private ResponseSender responseSender;
    private ObjectMapper mapper = new ObjectMapper();

    private MessageInfo info;


    @Inject
    public TenantWorkerImpl(
            final TenantService tenantService,
            final ResponseSender responseSender
    ) {
        this.service = tenantService;
        this.responseSender = responseSender;
    }


    @Override
    public void workOn(final MessageInfo info, final String message) throws JMSException, JsonProcessingException {
        this.info = info;

        String actionType = info.getActionType();

        try {
            mapper.readValue(message, claszOfAction(actionType)).execute(this);
        } catch (TenantCommandExecutionException | IOException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);

            String error = mapper.writeValueAsString(e);

            responseSender.sendReply(info, error);
        }
    }

    private Class<? extends TenantBaseCommand> claszOfAction(final String actionType) {
        switch (actionType) {
            case "de.kaiserpfalzedv.office.tenant.commands.TenantCreateCommand":
                return TenantCreateCommand.class;
            case "de.kaiserpfalzedv.office.tenant.commands.TenantRetrieveCommand":
                return TenantRetrieveCommand.class;
            case "de.kaiserpfalzedv.office.tenant.commands.TenantRetrieveAllCommand":
                return TenantRetrieveAllCommand.class;
            case "de.kaiserpfalzedv.office.tenant.commands.TenantUpdateCommand":
                return TenantUpdateCommand.class;
            case "de.kaiserpfalzedv.office.tenant.commands.TenantDeleteCommand":
                return TenantDeleteCommand.class;
            default:
                throw new IllegalStateException("No valid message type: " + actionType);
        }
    }


    public void execute(TenantCreateCommand command) throws TenantCommandExecutionException {
        try {
            Tenant data = service.create(command.getTenant());

            String message = createReply(command, data, TenantCreateReply.class);

            responseSender.sendReply(info, message);
        } catch (TenantExistsException | JsonProcessingException | JMSException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);

            throw new TenantCommandExecutionException(command, e);
        }
    }

    private <T extends TenantBaseCommand, R extends TenantBaseReply> String createReply(T command, Tenant data, Class<R> clasz) throws JsonProcessingException {
        R reply = new TenantReplyBuilder<R>()
                .withCommand(command)
                .withSource(tenantWorkerId)
                .withTenant(data)
                .build();

        return mapper.writeValueAsString(reply);
    }


    @Override
    public void execute(TenantRetrieveCommand command) throws TenantCommandExecutionException {
        try {
            Tenant data = service.retrieve(command.getTenant());

            String message = createReply(command, data, TenantRetrieveReply.class);

            responseSender.sendReply(info, message);
        } catch (TenantDoesNotExistException | JsonProcessingException | JMSException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);
            throw new TenantCommandExecutionException(command, e);
        }
    }


    @Override
    public void execute(TenantRetrieveAllCommand command) throws TenantCommandExecutionException {
        Collection<Tenant> data = service.retrieve();

        TenantRetrieveAllReply reply = new TenantReplyBuilder<TenantRetrieveAllReply>()
                .withCommand(command)
                .withSource(tenantWorkerId)
                .withTenants(data)
                .build();

        try {
            String message = mapper.writeValueAsString(reply);
            responseSender.sendReply(info, message);
        } catch (JsonProcessingException | JMSException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);

            throw new TenantCommandExecutionException(command, e);
        }
    }

    @Override
    public void execute(TenantUpdateCommand command) throws TenantCommandExecutionException {
        try {
            Tenant data = service.update(command.getTenant());

            String message = createReply(command, data, TenantUpdateReply.class);

            responseSender.sendReply(info, message);
        } catch (TenantDoesNotExistException | TenantExistsException | JsonProcessingException | JMSException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);
            throw new TenantCommandExecutionException(command, e);
        }
    }


    @Override
    public void execute(TenantDeleteCommand command) throws TenantCommandExecutionException {
        service.delete(command.getTenant());

        TenantDeleteReply reply = new TenantReplyBuilder<TenantDeleteReply>()
                .withCommand(command)
                .withSource(tenantWorkerId)
                .build();

        try {
            String message = mapper.writeValueAsString(reply);
            responseSender.sendReply(info, message);
        } catch (JsonProcessingException | JMSException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);
            throw new TenantCommandExecutionException(command, e);
        }
    }


    @Override
    public void execute(BaseCommand command) throws CommandExecutionException {
        throw new CommandExecutionException(command, "Invalid command: " + command);
    }
}
