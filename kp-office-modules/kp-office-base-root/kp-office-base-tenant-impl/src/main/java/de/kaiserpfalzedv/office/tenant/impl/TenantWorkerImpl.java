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

import java.util.Collection;
import java.util.UUID;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.jms.JMSException;

import de.kaiserpfalzedv.office.common.MessageInfo;
import de.kaiserpfalzedv.office.common.commands.BaseCommand;
import de.kaiserpfalzedv.office.common.commands.CommandExecutionException;
import de.kaiserpfalzedv.office.commons.shared.converter.Converter;
import de.kaiserpfalzedv.office.commons.shared.converter.ConverterRegistry;
import de.kaiserpfalzedv.office.commons.shared.converter.NoMatchingConverterFoundException;
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
import de.kaiserpfalzedv.office.tenant.replies.TenantDeleteReply;
import de.kaiserpfalzedv.office.tenant.replies.TenantReplyBuilder;
import de.kaiserpfalzedv.office.tenant.replies.TenantRetrieveAllReply;
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
    private ConverterRegistry converterRegistry;
    private ResponseSender responseSender;

    private MessageInfo info;


    @Inject
    public TenantWorkerImpl(
            final TenantService tenantService,
            final ConverterRegistry converterRegistry,
            final ResponseSender responseSender
    ) {
        this.service = tenantService;
        this.converterRegistry = converterRegistry;
        this.responseSender = responseSender;
    }


    @Override
    public void workOn(final MessageInfo info, final String message) throws NoMatchingConverterFoundException, JMSException {
        this.info = info;

        String actionType = info.getActionType();
        Converter<? extends TenantBaseCommand> converter = null;

        try {
            converter = converterRegistry.borrowConverter(actionType);

            converter.unmarshal(message).execute(this);
        } catch (TenantCommandExecutionException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);

            Converter<CommandExecutionException> errorConverter = converterRegistry.borrowConverter(e.getActionType());

            String error = errorConverter.marshal(e);

            converterRegistry.returnConverter(e.getActionType(), errorConverter);

            responseSender.sendReply(info, error);
        } finally {
            if (converter != null) {
                converterRegistry.returnConverter(actionType, converter);
            }
        }
    }


    public void execute(TenantCreateCommand command) throws TenantCommandExecutionException {
        try {
            Tenant data = service.createTenant(command.getTenant());

            String message = createReply(command, data);

            responseSender.sendReply(info, message);
        } catch (TenantExistsException | NoMatchingConverterFoundException | JMSException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);
            throw new TenantCommandExecutionException(command, e);
        }
    }

    private String createReply(TenantBaseCommand command, Tenant data) throws NoMatchingConverterFoundException {
        TenantBaseReply reply = new TenantReplyBuilder()
                .withCommand(command)
                .withSource(tenantWorkerId)
                .withTenant(data)
                .build();

        Converter converter = converterRegistry.borrowConverter(reply.getActionType());
        String message = converter.marshal(reply);
        converterRegistry.returnConverter(reply.getActionType(), converter);
        return message;
    }


    @Override
    public void execute(TenantRetrieveCommand command) throws TenantCommandExecutionException {
        try {
            Tenant data = service.retrieveTenant(command.getTenant());

            String message = createReply(command, data);

            responseSender.sendReply(info, message);
        } catch (TenantDoesNotExistException | NoMatchingConverterFoundException | JMSException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);
            throw new TenantCommandExecutionException(command, e);
        }
    }


    @Override
    public void execute(TenantRetrieveAllCommand command) throws TenantCommandExecutionException {
        Collection<Tenant> data = service.retrieveTenants();

        TenantRetrieveAllReply reply = (TenantRetrieveAllReply) new TenantReplyBuilder<TenantRetrieveAllReply>()
                .withCommand(command)
                .withSource(tenantWorkerId)
                .withTenants(data)
                .build();

        try {
            Converter<TenantRetrieveAllReply> converter = converterRegistry.borrowConverter(reply.getActionType());
            String message = converter.marshal(reply);
            converterRegistry.returnConverter(reply.getActionType(), converter);

            responseSender.sendReply(info, message);
        } catch (NoMatchingConverterFoundException | JMSException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);
            throw new TenantCommandExecutionException(command, e);
        }
    }

    @Override
    public void execute(TenantUpdateCommand command) throws TenantCommandExecutionException {
        try {
            Tenant data = service.updateTenant(command.getTenant());

            String message = createReply(command, data);

            responseSender.sendReply(info, message);
        } catch (TenantDoesNotExistException | TenantExistsException | NoMatchingConverterFoundException | JMSException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);
            throw new TenantCommandExecutionException(command, e);
        }
    }


    @Override
    public void execute(TenantDeleteCommand command) throws TenantCommandExecutionException {
        service.deleteTenant(command.getTenant());

        TenantDeleteReply reply = (TenantDeleteReply) new TenantReplyBuilder<TenantDeleteReply>()
                .withCommand(command)
                .withSource(tenantWorkerId)
                .build();

        try {
            Converter<TenantDeleteReply> converter = converterRegistry.borrowConverter(reply.getActionType());
            String message = converter.marshal(reply);
            converterRegistry.returnConverter(reply.getActionType(), converter);

            responseSender.sendReply(info, message);
        } catch (NoMatchingConverterFoundException | JMSException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);
            throw new TenantCommandExecutionException(command, e);
        }
    }


    @Override
    public void execute(BaseCommand command) throws CommandExecutionException {
        throw new CommandExecutionException(command, "Invalid command: " + command);
    }
}
